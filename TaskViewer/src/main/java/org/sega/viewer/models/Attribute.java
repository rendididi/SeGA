package org.sega.viewer.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Raysmond<jiankunlei@gmail.com>.
 */
@Entity
@Table(name = "attributes")
public class Attribute extends BaseModel{
    @ManyToOne
    private Artifact artifact;

    @Column(name = "attr_key", nullable = false)
    private String key;

    @Column(nullable = false)
    private String name;

    @Lob
    private Serializable value;

    public Attribute(){

    }

    public Attribute(Artifact artifact, String key, String name, Serializable value) {
        this.artifact = artifact;
        this.key = key;
        this.name = name;
        this.value = value;
    }

    public Artifact getArtifact() {
        return artifact;
    }

    public void setArtifact(Artifact artifact) {
        this.artifact = artifact;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Serializable getValue() {
        return value;
    }

    public void setValue(Serializable value) {
        this.value = value;
    }
}
