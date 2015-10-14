package org.sega.viewer.services.support;

import org.json.JSONArray;
import org.json.JSONObject;
import org.sega.viewer.utils.Base64Util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Raysmond<i@raysmond.com>
 */
public class EDMappingService {
    private JSONArray mapping;

    // entity id <-> mapping item
    private Map<String, MappingItem> mappingInfo = null;

    public EDMappingService(String mappingJson) throws UnsupportedEncodingException {
        this.mapping = new JSONArray(Base64Util.decode(mappingJson));
    }

    public Map<String, MappingItem> getMappingInfo() {
        if (mappingInfo == null) {
            this.resolveMappingInfo();
        }

        return mappingInfo;
    }

    private void resolveMappingInfo() {
        mappingInfo = new HashMap<>();

        for (int i = 0; i < mapping.length(); ++i) {
            JSONObject m = mapping.getJSONObject(i);
            JSONObject db = m.getJSONObject("db");
            JSONObject entity = m.getJSONObject("entity");

            List<String> entityPath = new ArrayList<>();
            if (entity.has("path")) {
                JSONArray path = entity.getJSONArray("path");
                for (int j = 0; j < path.length(); j++) {
                    entityPath.add(path.getString(j));
                }
            }

            MappingItem mappingItem = new MappingItem(
                    db.getString("table"),
                    db.getString("column"),
                    entity.getString("id"),
                    entity.getString("text"),
                    entityPath
            );

            mappingInfo.put(mappingItem.getEntityId(), mappingItem);
        }
    }

    public static class MappingItem {
        private String table;
        private String column;

        private String entityId;
        private String entityName;
        private List<String> entityPath = new ArrayList<>();

        public MappingItem() {

        }

        public MappingItem(String table, String column, String entityId, String entityName, List<String> entityPath) {
            this.table = table;
            this.column = column;
            this.entityId = entityId;
            this.entityName = entityName;
            this.entityPath = entityPath;
        }

        public String getTable() {
            return table;
        }

        public void setTable(String table) {
            this.table = table;
        }

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        public String getEntityId() {
            return entityId;
        }

        public void setEntityId(String entityId) {
            this.entityId = entityId;
        }

        public String getEntityName() {
            return entityName;
        }

        public void setEntityName(String entityName) {
            this.entityName = entityName;
        }

        public List<String> getEntityPath() {
            return entityPath;
        }

        public void setEntityPath(List<String> entityPath) {
            this.entityPath = entityPath;
        }
    }
}
