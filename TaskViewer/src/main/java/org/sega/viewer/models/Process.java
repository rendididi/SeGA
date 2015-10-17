package org.sega.viewer.models;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Raysmond<i@raysmond.com>
 */
@Entity
@Data
@Table(name = "process")
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

    private String bindingJson;

    private String processImageUrl;

    private String description;

    private String name;

    @ManyToOne
    @JoinColumn(name = "template")
    private ProcessTemplate template;

    @ManyToOne
    @JoinColumn(name = "dbconfig")
    private DatabaseConfiguration dbconfig;

    @OneToMany(mappedBy = "process", cascade = {CascadeType.ALL},  fetch = FetchType.LAZY)
    private Collection<ProcessEdit> processEdits = new ArrayList<>();

    @OneToMany(mappedBy = "process", cascade = {CascadeType.ALL},  fetch = FetchType.LAZY)
    private Collection<ProcessEdit> processInstances = new ArrayList<>();

}
