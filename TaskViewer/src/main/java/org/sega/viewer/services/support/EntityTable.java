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