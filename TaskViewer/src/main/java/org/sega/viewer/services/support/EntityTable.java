package org.sega.viewer.services.support;

import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Raysmond<i@raysmond.com>
 */
public class EntityTable {
    public JSONObject entity;
    public Table table;
    public String key;
    public EDMappingService.MappingItem foreignKey;

    // If it's one-to-many relationship,then isGroupChild is true,
    // otherwise isGroupChild is false
    public boolean isGroupChild = false;

    public EntityTable parent = null;

    // has one
    public List<EntityTable> hasOneList = new ArrayList<>();

    // has many
    public List<EntityTable> hasManyList = new ArrayList<>();


    public EntityTable(JSONObject entity) {
        this.entity = entity;
    }

    public void setKeyAndValue(EDMappingService.MappingItem mappingItem, Long value){
        this.key = mappingItem.getEntityId();

        this.table.setKey(mappingItem.getColumn());
        this.table.setKeyValue(value);
    }

    public void addHasOneChild(EntityTable child, EDMappingService.MappingItem fk) {
        this.hasOneList.add(child);
        child.parent = this;
        child.foreignKey = fk;
    }

    public void addHasManyChild(EntityTable child, EDMappingService.MappingItem fk) {
        this.hasManyList.add(child);
        child.parent = this;
        child.isGroupChild = true;
        child.foreignKey = fk;
    }

    public void save() throws SQLException {
        // has-many
        if (isGroupChild && foreignKey != null) {
            this.table.setForeignKeyValue(foreignKey.getColumn(), parent.table.getKeyValue());
        }

        // has-one
        if (!isGroupChild && !hasOneList.isEmpty()) {
            hasOneList.forEach(child -> {
                this.table.setForeignKeyValue(child.foreignKey.getColumn(), child.table.getKeyValue());
            });

        }

        // save entity table
        this.table.save();

        // write back generated key
        this.entity.put(key, this.table.getKeyValue());
    }
}