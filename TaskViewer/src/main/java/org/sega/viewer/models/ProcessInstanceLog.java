package org.sega.viewer.models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by glp on 2015/10/11.
 */

@Entity
@Table(name = "process_instance_log")
public class ProcessInstanceLog extends BaseModel{

    @ManyToOne
    private ProcessInstance processInstance;

    @Lob
    private String entity;

    @Column(name = "task", nullable = false)
    private String task;

//    raysmond says: order by commitDate
//    @ManyToOne(optional = true)
//    @Column(name = "last_step_log", nullable = true)
//    private ProcessInstanceLog lastStepLog;

    @Column(name = "commit_at")
    private Date commitAt;

    @PrePersist
    public void onPrePersist(){
        this.commitAt = new Date();
    }

    public ProcessInstance getProcessInstance() {
        return processInstance;
    }

    public void setProcessInstance(ProcessInstance processInstance) {
        this.processInstance = processInstance;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Date getCommitAt() {
        return commitAt;
    }

    public void setCommitAt(Date commitAt) {
        this.commitAt = commitAt;
    }
}
