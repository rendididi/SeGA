package org.sega.viewer.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Raysmond<i@raysmond.com>.
 */
@Entity
@Table(name = "artifacts")
public class Artifact extends BaseModel {
    @OneToOne
    @JoinColumn(name = "process_instance_id")
    private ProcessInstance processInstance;

    @ManyToOne
    private Artifact parent;

    @ManyToOne
    private ArtifactList artifactList;

    @Column(name = "entity_key", nullable = false)
    private String entityKey;

    // machine id like 'j1_1'
    @Column(name = "entity_id", nullable = false, unique = true)
    private Long entityId;

    // friendly artifact name
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "artifact", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Attribute> attributes = new ArrayList<>();

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Artifact> artifacts = new ArrayList<>();

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ArtifactList> artifactLists = new ArrayList<>();

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @PrePersist
    public void onPrePersist(){
        if (createdAt == null)
            createdAt = new Date();

        updatedAt = new Date();
    }

    public ProcessInstance getProcessInstance() {
        return processInstance;
    }

    public void setProcessInstance(ProcessInstance processInstance) {
        this.processInstance = processInstance;
    }

    public Artifact getParent() {
        return parent;
    }

    public void setParent(Artifact parent) {
        this.parent = parent;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getEntityKey() {
        return entityKey;
    }

    public void setEntityKey(String entityKey) {
        this.entityKey = entityKey;
    }

    public List<Artifact> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(List<Artifact> artifacts) {
        this.artifacts = artifacts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<ArtifactList> getArtifactLists() {
        return artifactLists;
    }

    public void setArtifactLists(List<ArtifactList> artifactLists) {
        this.artifactLists = artifactLists;
    }
}
