package org.sega.ProcessDesigner.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name="databaseconfiguration")
public class DatabaseConfiguration extends BaseModel {

    @Column(name="TYPE")
    private String type;

    @Column(name="HOST")
    private String host;

    @Column(name="PORT")
    private long port;

    @Column(name="DATABASE_NAME")
    private String database_name;

    @Column(name="USERNAME")
    private String username;

    @Column(name="PASSWORD")
    private String password;

    @Column(name="TABLES_JSON")
    private String tables_json;

    @Column(name="COLUMNS_JSON")
    private String columns_json;

    @Column(name="KEYS_JSON")
    private String keys_json;

    @OneToMany(mappedBy = "dbconfig", cascade = { CascadeType.ALL } )
    private Collection<Process> processes = new ArrayList<>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public long getPort() {
        return port;
    }

    public void setPort(long port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTables_json() {
        return tables_json;
    }

    public void setTables_json(String tables_json) {
        this.tables_json = tables_json;
    }

    public String getColumns_json() {
        return columns_json;
    }

    public void setColumns_json(String columns_json) {
        this.columns_json = columns_json;
    }

    public String getKeys_json() {
        return keys_json;
    }

    public void setKeys_json(String keys_json) {
        this.keys_json = keys_json;
    }

    public String getDatabase_name() {
        return database_name;
    }

    public void setDatabase_name(String database_name) {
        this.database_name = database_name;
    }

}
