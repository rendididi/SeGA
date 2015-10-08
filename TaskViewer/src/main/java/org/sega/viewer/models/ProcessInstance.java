package org.sega.viewer.models;


import javax.persistence.*;
import java.util.Date;

/**
 * @author Raysmond<jiankunlei@gmail.com>.
 */
@Entity
@Table(name = "process_instances")
public class ProcessInstance extends BaseModel{
    public static final String STATE_COMPLETED = "completed";

    @ManyToOne
    private Process process;

    @Lob
    private String entity;

    @Column(name = "next_task")
    private String nextTask;

    @Column(name = "created_at")
    private Date createdAt;

    @OneToOne(mappedBy = "processInstance")
    private Artifact mainArtifact;

    public ProcessInstance() {

    }

    public ProcessInstance(Process process) {
        this.process = process;
    }

    @PrePersist
    public void onPrePersist(){
        this.createdAt = new Date();
    }

    public String getNextTask() {
        return nextTask;
    }

    public void setNextTask(String nextTask) {
        this.nextTask = nextTask;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public Artifact getMainArtifact() {
        return mainArtifact;
    }

    public void setMainArtifact(Artifact mainArtifact) {
        this.mainArtifact = mainArtifact;
    }
}
