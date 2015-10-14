package org.sega.viewer.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.sega.viewer.models.ProcessInstance;
import org.sega.viewer.repositories.ProcessInstanceRepository;
import org.sega.viewer.services.support.*;
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

            if (attribute.getType().equals("key")) {
                key = id;
                String val = artifact.optString(id);
                if (val != null && !val.equalsIgnoreCase(ProcessInstance.EMPTY_KEY_VALUE)) {
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
                EntityTable et = preSyncEntity(artifact.getJSONObject(id), id);
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
                    EntityTable et = preSyncEntity(entities.getJSONObject(i), id);
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
                    System.out.println("Unrecognized entityJson child: " + child.toString());
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

    private EDMappingService.MappingItem getEdMappingItem(String entityId) {
        return edMappingInfo.get(entityId);
    }

}

