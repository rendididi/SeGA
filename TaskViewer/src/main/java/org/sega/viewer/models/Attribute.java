package org.sega.viewer.models;

import org.sega.viewer.models.support.ValueType;

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

    @Column(name = "value_type", nullable = false)
    private ValueType valueType;

    @Lob
    private Serializable value;

    public Attribute(){

    }

    public Attribute(Artifact artifact, String key, String name, ValueType valueType, Serializable value) {
        this.artifact = artifact;
        this.key = key;
        this.name = name;
        this.valueType = valueType;
        this.value = value;
    }

    /**
     * Initialize the value according to valueType
     */
    public void initializeValue(){
        this.value = null;

        switch (valueType){
            case STRING:
                break;
            case TEXT:
                break;
            case LONG:
                this.value = Long.valueOf(0L);
                break;
            case INTEGER:
                this.value = Integer.valueOf(0);
                break;
            case FLOAT:
                this.value = Float.valueOf(0.0F);
                break;
            case DOUBLE:
                this.value = Double.valueOf(0.0D);
            case FILE:
                break;
            case AUTO:
                break;

            default:
        }
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
