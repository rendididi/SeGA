package org.sega.viewer.models;

import org.sega.viewer.common.Constant;
import javax.persistence.*;
import java.util.Date;

/**
 * Created by Raysmond on 9/4/15.
 */
@Entity
public class ProcessEdit extends BaseModel {

    private String userType;

    @ManyToOne
    @JoinColumn(name = "process")
    private Process process;

    private Date datetime;

    private String step;

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
        return Constant.STEPS.get(this.step);
    }
}