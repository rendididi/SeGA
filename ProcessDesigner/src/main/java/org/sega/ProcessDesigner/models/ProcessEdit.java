package org.sega.ProcessDesigner.models;

import org.sega.ProcessDesigner.data.StepConstant;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author glp
 */

@Entity
@Table(name = "processedit")
public class ProcessEdit extends BaseModel {

    @Column(name="USERTYPE")
    private String userType;

    @ManyToOne
    @JoinColumn(name="PROCESS")
    private Process process;

    @Column(name="DATETIME")
    private Date datetime;

    @Column(name="STEP")
    private String step;

    @Column(name="SESSIONID")
    private String sessionid;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }


    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getStepName() {
        return StepConstant.getStepName(this.step);
    }
}
