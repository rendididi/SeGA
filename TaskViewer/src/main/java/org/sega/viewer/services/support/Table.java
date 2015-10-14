package org.sega.viewer.services.support;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Raysmond<i@raysmond.com>
 */
public class Table {
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