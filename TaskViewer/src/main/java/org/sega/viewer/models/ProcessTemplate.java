package org.sega.viewer.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Raysmond on 9/4/15.
 */
@Entity
public class ProcessTemplate extends BaseModel {

    private String processJSON;

    private String entityJSON;

    private String databaseSQL;

    private String EDmappingJSON;

    private String processImageUrl;

    private String description;

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
        this.EDmappingJSON = eDmappingJSON;
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
