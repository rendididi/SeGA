package org.sega.ProcessDesigner.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;


@Entity
public class Process extends BaseModel {

    private String processJSON;

    private String processXML;

    @Basic(fetch = FetchType.LAZY)
    private String entityJSON;

    // TODO The lazy loading doesn't work
    //@Lob
    @Basic(fetch = FetchType.LAZY)
    private String databaseJSON;

    @Basic(fetch = FetchType.LAZY)
    private String DDmappingJSON;

    @Basic(fetch = FetchType.LAZY)
    private String EDmappingJSON;

    private String processImageUrl;

    private String description;

    private String name;

    @ManyToOne
    @JoinColumn(name = "template")
    private ProcessTemplate template;

    @ManyToOne
    @JoinColumn(name = "dbconfig")
    private DatabaseConfiguration dbconfig;

    @OneToMany(mappedBy = "process", cascade = {CascadeType.ALL})
    private Collection<ProcessEdit> processEdits = new ArrayList<>();

    public String getProcessJSON() {
        return processJSON;
    }

    public void setProcessJSON(String processJSON) {
        this.processJSON = processJSON;
    }

    public String getProcessXML() {
        return processXML;
    }

    public void setProcessXML(String processXML) {
        this.processXML = processXML;
    }

    public String getEntityJSON() {
        return entityJSON;
    }

    public void setEntityJSON(String entityJSON) {
        this.entityJSON = entityJSON;
    }

    public String getDatabaseJSON() {
        return databaseJSON;
    }

    public void setDatabaseJSON(String databaseJSON) {
        this.databaseJSON = databaseJSON;
    }

    public String getEDmappingJSON() {
        return EDmappingJSON;
    }

    public void setEDmappingJSON(String eDmappingJSON) {
        EDmappingJSON = eDmappingJSON;
    }

    public String getProcessImageUrl() {
        return processImageUrl;
    }

    public void setProcessImageUrl(String processImageUrl) {
        this.processImageUrl = processImageUrl;
    }

    public ProcessTemplate getTemplate() {
        return template;
    }

    public void setTemplate(ProcessTemplate template) {
        this.template = template;
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

    public String getDDmappingJSON() {
        return DDmappingJSON;
    }

    public void setDDmappingJSON(String dDmappingJSON) {
        DDmappingJSON = dDmappingJSON;
    }

    public DatabaseConfiguration getDbconfig() {
        return dbconfig;
    }

    public void setDbconfig(DatabaseConfiguration dbconfig) {
        this.dbconfig = dbconfig;
    }
}
