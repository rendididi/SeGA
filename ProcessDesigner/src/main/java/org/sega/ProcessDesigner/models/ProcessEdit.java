/**
 *
 */
package org.sega.ProcessDesigner.models;

import org.sega.ProcessDesigner.data.StepConstant;

import java.io.Serializable;
import java.util.Date;

/**
 * @author glp
 */
public class ProcessEdit implements Serializable {

    private long id;
    private String userType;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
