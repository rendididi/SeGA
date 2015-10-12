package org.sega.viewer.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Raysmond<i@raysmond.com>.
 */
@Entity
@Table(name = "artifact_list")
public class ArtifactList extends BaseModel{
    @ManyToOne
    private Artifact parent;

    @Column(name = "entity_id", nullable = false)
    private String entityId;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "artifactList", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Artifact> artifacts = new ArrayList<>();

    public Artifact getParent() {
        return parent;
    }

    public void setParent(Artifact parent) {
        this.parent = parent;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Artifact> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(List<Artifact> artifacts) {
        this.artifacts = artifacts;
    }
}
