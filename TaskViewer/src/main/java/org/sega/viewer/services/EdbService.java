package org.sega.viewer.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.sega.viewer.models.ProcessInstance;
import org.sega.viewer.models.support.ValueType;
import org.sega.viewer.repositories.ProcessInstanceRepository;
import org.sega.viewer.services.support.*;
import org.sega.viewer.utils.Base64Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.*;

import static org.sega.viewer.services.support.EDMappingService.MappingItem;


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

    private static final Logger logger = LoggerFactory.getLogger(EdbService.class);

    /**
     * Synchronize SeGA process instance to edb
     */
    public void sync(ProcessInstance instance) throws
            SQLException, ClassNotFoundException, UnSupportEdbException, UnsupportedEncodingException {

        clearSyncInfo();

        JSONObject entity = new JSONObject(instance.getEntity());
        JSONObject entitySchema = new JSONArray(Base64Util.decode(instance.getProcess().getEntityJSON())).getJSONObject(0);

        // entity schema tree
        parseEntityInfo(entitySchema);

        // entity-edb mapping
        parseEdMappingInfo(instance.getProcess().getEDmappingJSON());

        // create edb connection
        MysqlConnection mysql = new MysqlConnection(instance.getProcess().getDbconfig());
        connection = mysql.open();

        // travel and prepare execution tree
        String rootId = getMainArtifactId(entitySchema);
        this.entityTableRoot = preSyncEntity(entity.getJSONObject(rootId), rootId);

        // execute sync
        executeSync(this.entityTableRoot);

        // save updated entity with generated keys
        instance.setEntity(entity.toString());
        processInstanceRepository.save(instance);

        mysql.close();
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

    /**
     * Synchronize an artifact entity recursively to edb
     */
    private EntityTable preSyncEntity(JSONObject artifact, String artifactId) throws SQLException {
        Map<String, Object> columns = new HashMap<>();
        String key = null;
        Long keyValue = null;

        EntityTable entityTable = new EntityTable(artifact);
        entityTable.table = new Table(connection, tableMap.get(artifactId));

        for (AttributeType attribute : mapping.get(artifactId)) {
            String id = attribute.getId();
            String type = attribute.getType();

            if (type.equals("key")) {
                key = id;
                keyValue = getKeyValue(key, artifact);
            }

            if (!artifact.has(id)) {
                continue;
            }

            // one-to-one
            if (type.equals("artifact") && artifact.has(id)) {
                EntityTable et = preSyncEntity(artifact.getJSONObject(id), id);
                entityTable.addHasOneChild(et);

                // add foreign key to current entity table
                MappingItem fk = getEdMappingItem(id);
                if (fk != null) {
                    entityTable.foreignKey = fk.getEntityId();
                    entityTable.table.setForeignKey(fk.getColumn());
                }
            }

            // one-to-many
            if (type.equals("artifact_n") && artifact.has(id)) {
                JSONArray entities = artifact.getJSONArray(id);
                for (int i = 0; i < entities.length(); ++i) {
                    EntityTable et = preSyncEntity(entities.getJSONObject(i), id);
                    et.isGroupChild = true;

                    // add foreign key to children's entity table
                    MappingItem fk = getEdMappingItem(id);
                    et.foreignKey = fk.getEntityId();
                    et.table.setForeignKey(fk.getColumn());

                    entityTable.addHasManyChild(et);
                }
            }

            // normal attribute
            if (type.equals("attribute")) {
                columns.put(getEdMappingItem(id).getColumn(), getColumnValue(artifact, attribute.getValueType(), id));
            }

            // group
            if (type.equals("group")) {
                // do nothing
            }
        }

        entityTable.table.setKey(getEdMappingItem(key).getColumn());
        entityTable.table.setKeyValue(keyValue);
        entityTable.table.setColumns(columns);
        entityTable.key = key;

        return entityTable;
    }

    private Object getColumnValue(JSONObject entity, ValueType valueType, String id) {
        switch (valueType) {
            case LONG:
                return entity.getLong(id);
            case INTEGER:
                return entity.getInt(id);
            case TEXT:
            case STRING:
                return entity.getString(id);
            default:
                return entity.getString(id);
        }
    }

    private Long getKeyValue(String key, JSONObject entity) {
        String val = entity.optString(key);
        Long keyValue = null;

        if (val != null && !val.equalsIgnoreCase(ProcessInstance.EMPTY_KEY_VALUE)) {
            try {
                keyValue = Long.valueOf(val);
            } catch (NumberFormatException e) {
                keyValue = null;
            }
        }

        return keyValue;
    }

    private void parseEntityInfo(JSONObject entitySchema) {
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
                    parseEntityInfo(child);
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
                    parseEntityInfo(child);
                    break;
                default:
                    logger.error("Unrecognized entityJson child: " + child.toString());
            }
        }
    }

    private String getMainArtifactId(JSONObject entitySchema) {
        return entitySchema.getString("id");
    }

    private void parseEdMappingInfo(String edMappingJson) throws UnsupportedEncodingException {
        EDMappingService edMappingService = new EDMappingService(edMappingJson);
        this.edMappingInfo = edMappingService.getMappingInfo();
    }

    private MappingItem getEdMappingItem(String entityId) {
        return edMappingInfo.get(entityId);
    }

}

