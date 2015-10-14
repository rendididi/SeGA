package org.sega.viewer.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.sega.viewer.models.DatabaseConfiguration;
import org.sega.viewer.models.ProcessInstance;
import org.sega.viewer.repositories.ProcessInstanceRepository;
import org.sega.viewer.services.support.AttributeType;
import org.sega.viewer.services.support.EDMappingService;
import org.sega.viewer.services.support.UnSupportEdbException;
import org.sega.viewer.utils.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.sql.*;

import java.util.*;


/**
 * @author Raysmond<i@raysmond.com>
 */
@Service
public class EdbService {

    @Autowired
    private ProcessInstanceRepository processInstanceRepository;

    private Map<String, List<AttributeType>> mapping;
    private Map<String, String> tableMap;
    private Map<String, EDMappingService.MappingItem> edMappingInfo;
    private EntityTable entityTableRoot;
    private Connection connection;

    private static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    private static final String MYSQL_CONNECTION_URL = "jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=UTF-8";


    /**
     * Synchronize SeGA process instance to edb
     */
    public void sync(ProcessInstance instance) throws
            SQLException, ClassNotFoundException, UnSupportEdbException, UnsupportedEncodingException {

        clearSyncInfo();

        JSONObject entity = new JSONObject(instance.getEntity());
        JSONObject entitySchema = new JSONArray(Base64Util.decode(instance.getProcess().getEntityJSON())).getJSONObject(0);

        getEntityInfo(entitySchema);
        parseEdMappingInfo(instance.getProcess().getEDmappingJSON());

        connection = createEdbConnection(instance.getProcess().getDbconfig());

        // travel and sync entity
        String rootId = getMainArtifactId(entitySchema);
        this.entityTableRoot = syncEntity(entity.getJSONObject(rootId), rootId);

        executeSync(this.entityTableRoot);

        // save updated entity with generated keys
        instance.setEntity(entity.toString());
        processInstanceRepository.save(instance);

        closeEdbConnection();
        clearSyncInfo();
    }


    /**
     * Execute synchronization query recursively (DFS)
     */
    private void executeSync(EntityTable entityTable) throws SQLException {
        // has-one relationship
        // save the child first and then add child key to current entity
        if (entityTable.child != null) {
            executeSync(entityTable.child);
            entityTable.save();
        }
        // has-many relationship
        // save the current entity first, and then set children's foreign key
        else if (!entityTable.children.isEmpty()) {
            entityTable.save();
            for (EntityTable et : entityTable.children) {
                executeSync(et);
            }
        } else {
            // simple entity
            entityTable.save();
        }
    }

    private void clearSyncInfo() {
        mapping = new HashMap<>();
        tableMap = new HashMap<>();
        edMappingInfo = new HashMap<>();
        entityTableRoot = null;
    }

    private void parseEdMappingInfo(String edMappingJson) throws UnsupportedEncodingException {
        EDMappingService edMappingService = new EDMappingService(edMappingJson);
        this.edMappingInfo = edMappingService.getMappingInfo();
    }

    private EDMappingService.MappingItem getEdMappingItem(String entityId) {
        return edMappingInfo.get(entityId);
    }

    /**
     * Synchronize an artifact entity recursively to edb
     */
    private EntityTable syncEntity(JSONObject artifact, String artifactId) throws SQLException {
        Map<String, Object> columns = new HashMap<>();
        String key = null;
        Long keyValue = null;

        EntityTable entityTable = new EntityTable(artifact);
        entityTable.table = new Table(connection, tableMap.get(artifactId));

        for (AttributeType attribute : mapping.get(artifactId)) {
            String id = attribute.getId();

            if (attribute.getType().equals("key")) {
                key = id;
                String val = artifact.optString(id);
                if (val != null && !val.equalsIgnoreCase("NEW")) {
                    try {
                        keyValue = Long.valueOf(val);
                    } catch (NumberFormatException e) {
                        keyValue = null;
                    }
                }
            }

            if (!artifact.has(id)) {
                continue;
            }

            // one-to-one
            if (attribute.getType().equals("artifact") && artifact.optJSONObject(id) != null) {
                EntityTable et = syncEntity(artifact.getJSONObject(id), id);
                entityTable.addHasOneChild(et);

                // add foreign key to current entity table
                EDMappingService.MappingItem fkMappingItem = getEdMappingItem(id);
                if (fkMappingItem != null) {
                    entityTable.foreignKey = fkMappingItem.getEntityId();
                    entityTable.table.setForeignKey(fkMappingItem.getColumn());
                }
            }

            // one-to-many
            if (attribute.getType().equals("artifact_n") && artifact.optJSONArray(id) != null) {
                JSONArray entities = artifact.getJSONArray(id);
                for (int i = 0; i < entities.length(); ++i) {
                    EntityTable et = syncEntity(entities.getJSONObject(i), id);
                    et.isGroupChild = true;

                    // add foreign key to children's entity table
                    EDMappingService.MappingItem fkMappingItem = getEdMappingItem(id);
                    et.foreignKey = fkMappingItem.getEntityId();
                    et.table.setForeignKey(fkMappingItem.getColumn());

                    entityTable.addHasManyChild(et);
                }
            }

            // normal attribute
            // TODO
            if (attribute.getType().equals("attribute")) {
                String dbColumn = getEdMappingItem(id).getColumn();

                switch (attribute.getValueType()) {
                    case LONG:
                        columns.put(dbColumn, artifact.getLong(id));
                        break;
                    case INTEGER:
                        columns.put(dbColumn, artifact.getInt(id));
                        break;
                    case TEXT:
                    case STRING:
                        columns.put(dbColumn, artifact.getString(id));
                        break;
                    default:
                        columns.put(dbColumn, artifact.getString(id));
                }
            }

            // group
            if (attribute.getType().equals("group")) {
                // TODO
            }
        }

        entityTable.table.setKey(getEdMappingItem(key).getColumn());
        entityTable.table.setKeyValue(keyValue);
        entityTable.table.setColumns(columns);
        entityTable.key = key;

        return entityTable;
    }

    private void getEntityInfo(JSONObject entitySchema) {
        String artifactId = entitySchema.getString("id");

        if (!entitySchema.getJSONObject("data").has("mapTo")) {
            return;
        }

        tableMap.put(artifactId, entitySchema.getJSONObject("data").getString("mapTo"));
        mapping.put(artifactId, new ArrayList<>());

        JSONArray children = entitySchema.getJSONArray("children");

        for (int i = 0; i < children.length(); ++i) {
            JSONObject child = children.getJSONObject(i);
            mapping.get(artifactId).add(new AttributeType(child));

            switch (child.getString("type")) {
                case "artifact":
                    getEntityInfo(child);
                    break;
                case "key":
                    break;
                case "attribute":
                    break;
                case "group":
                    JSONArray group = child.getJSONArray("children");
                    for (int j = 0; j < group.length(); ++j) {
                        mapping.get(artifactId).add(new AttributeType(group.getJSONObject(j)));
                    }
                    break;
                case "artifact_n":
                    getEntityInfo(child);
                    break;
                default:
                    System.out.println("Unrecognized entityJson child: " + child.toString());
            }
        }
    }

    private String getMainArtifactId(JSONObject entitySchema) {
        return entitySchema.getString("id");
    }


    private Connection createEdbConnection(DatabaseConfiguration edb) throws ClassNotFoundException, SQLException, UnSupportEdbException {
        // only support mysql temporarily
        if (edb == null || !edb.getType().equals("mysql")) {
            throw new UnSupportEdbException();
        }

        String dbUrl = String.format(MYSQL_CONNECTION_URL, edb.getHost(), edb.getPort(), edb.getDatabase_name());
        String user = edb.getUsername();
        String password = edb.getPassword();

        Class.forName(MYSQL_DRIVER);
        return DriverManager.getConnection(dbUrl, user, password);
    }

    private void closeEdbConnection() throws SQLException {
        connection.close();
        connection = null;
    }
}

class EntityTable {
    public JSONObject entity;
    public Table table;
    public String key;
    public String foreignKey;

    // If it's one-to-many relationship,then isGroupChild is true,
    // otherwise isGroupChild is false
    public boolean isGroupChild = false;

    public EntityTable parent = null;

    // has one
    public EntityTable child = null;

    // has many
    public List<EntityTable> children = new ArrayList<>();


    public EntityTable(JSONObject entity) {
        this.entity = entity;
    }

    public void addHasOneChild(EntityTable child) {
        this.child = child;
        this.child.parent = this;
    }

    public void addHasManyChild(EntityTable child) {
        this.children.add(child);
        child.parent = this;
    }

    public void save() throws SQLException {
        // has-many
        if (isGroupChild && foreignKey != null) {
            this.table.setForeignKeyValue(parent.table.getKeyValue());
        }

        // has-one
        if (!isGroupChild && child != null && foreignKey != null) {
            this.table.setForeignKeyValue(child.table.getKeyValue());
        }

        // save entity table
        this.table.save();

        // write back generated key
        this.entity.put(key, this.table.getKeyValue());
    }
}

class Table {
    private Connection connection;
    private String name;
    private String key;
    private Long keyValue;
    private String foreignKey;
    private Map<String, Object> columns = new HashMap<>();

    public Table(Connection connection, String name) {
        this.connection = connection;
        this.name = name;
    }

    /**
     * Insert or update a table record
     */
    public void save() throws SQLException {
        PreparedStatement statement;
        String sql;

        if (!this.exists()) {
            sql = "INSERT INTO " + name + " (" + getColumnsSql() + ") VALUES (" + getValuesPlaceholder() + ")";
        } else {
            if (columns.isEmpty())
                return;

            sql = "UPDATE " + name + " SET " + getUpdateColumnsSql() + " WHERE " + key + "=?";
        }

        if (!this.exists())
            statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        else
            statement = connection.prepareStatement(sql);

        // set statement parameters
        int count = 1;
        for (String column : columns.keySet()) {
            Object value = columns.get(column);

            if (value instanceof String) {
                statement.setString(count++, value.toString());
            } else if (value instanceof Long) {
                statement.setLong(count++, (Long) value);
            } else if (value instanceof Integer) {
                statement.setLong(count++, (Integer) value);
            } else {
                // TODO others
                statement.setString(count++, value.toString());
            }
        }

        // UPDATE SQL
        if (this.exists()) {
            statement.setLong(count, keyValue);
        }

        System.out.println("SQL : " + statement.toString());

        // execute sync SQL
        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Save entity record to table " + name + " failed.");
        }

        if (!this.exists()) {
            // get generated key value
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                this.keyValue = generatedKeys.getLong(1);
            }
        }
    }

    private boolean exists() {
        return keyValue != null;
    }

    private String getColumnsSql() {
        StringBuilder sql = new StringBuilder();
        for (String column : columns.keySet()) {
            sql.append(column).append(",");
        }
        if (sql.length() > 0)
            sql.deleteCharAt(sql.length() - 1);

        return sql.toString();
    }

    private String getValuesPlaceholder() {
        StringBuilder placeholder = new StringBuilder();

        for (int i = 0; i < columns.size(); ++i) {
            placeholder.append("?,");
        }

        if (placeholder.length() > 0)
            placeholder.deleteCharAt(placeholder.length() - 1);

        return placeholder.toString();
    }

    private String getUpdateColumnsSql() {
        StringBuilder sql = new StringBuilder();

        for (String column : columns.keySet()) {
            sql.append(column).append("=").append("?").append(",");
        }

        if (sql.length() > 0)
            sql.deleteCharAt(sql.length() - 1);

        return sql.toString();
    }

    public void setForeignKeyValue(Long value) {
        if (foreignKey != null && !foreignKey.isEmpty()) {
            this.columns.put(foreignKey, value);
        }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setKeyValue(Long keyValue) {
        this.keyValue = keyValue;
    }

    public Long getKeyValue() {
        return keyValue;
    }

    public void setForeignKey(String foreignKey) {
        this.foreignKey = foreignKey;
    }

    public void setColumns(Map<String, Object> columns) {
        this.columns = columns;
    }
}