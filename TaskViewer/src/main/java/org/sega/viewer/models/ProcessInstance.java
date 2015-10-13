package org.sega.viewer.models;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Raysmond<i@raysmond.com>.
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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "processInstance")
    private List<ProcessInstanceLog> logs = new ArrayList<>();

    @OneToOne(mappedBy = "processInstance")
    private ProcessInstanceJTangInfo jTangInfo;

    public ProcessInstance(){

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

    public List<ProcessInstanceLog> getLogs() {
        return logs;
    }

    public void setLogs(List<ProcessInstanceLog> logs) {
        this.logs = logs;
    }

    public ProcessInstanceJTangInfo getjTangInfo() {
        return jTangInfo;
    }

    public void setjTangInfo(ProcessInstanceJTangInfo jTangInfo) {
        this.jTangInfo = jTangInfo;
    }
}
