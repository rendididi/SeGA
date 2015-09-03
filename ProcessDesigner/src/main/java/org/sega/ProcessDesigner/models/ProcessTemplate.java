package org.sega.ProcessDesigner.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;


@Entity
@Table(name="processtemplate")
public class ProcessTemplate extends BaseModel {

    @Column(name="PROCESSJSON")
    private String processJSON;

    @Column(name="ENTITYJSON")
    private String entityJSON;

    @Column(name="DATABASESQL")
    private String databaseSQL;

    @Column(name="EDMAPPINGJSON")
    private String EDmappingJSON;

    @Column(name="PROCESSIMAGEURL")
    private String processImageUrl;

    @Column(name="DESCRIPTION")
    private String description;

    @Column(name="NAME")
    private String name;

    @OneToMany(mappedBy = "template", cascade = { CascadeType.ALL } )
    private Collection<Process> processes = new ArrayList<>();

    public String getProcessJSON() {
        return processJSON;
    }

    public void setProcessJSON(String processJSON) {
        this.processJSON = processJSON;
    }

    public String getEntityJSON() {
        return entityJSON;
    }

    public void setEntityJSON(String entityJSON) {
        this.entityJSON = entityJSON;
    }

    public String getDatabaseSQL() {
        return databaseSQL;
    }

    public void setDatabaseSQL(String databaseSQL) {
        this.databaseSQL = databaseSQL;
    }

    public String getEDmappingJSON() {
        return EDmappingJSON;
    }

    public void setEDmappingJSON(String eDmappingJSON) {
        EDmappingJSON = eDmappingJSON;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProcessImageUrl() {
        return processImageUrl;
    }

    public void setProcessImageUrl(String processImageUrl) {
        this.processImageUrl = processImageUrl;
    }


}
