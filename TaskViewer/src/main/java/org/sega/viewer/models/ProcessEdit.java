package org.sega.viewer.models;

import org.sega.viewer.Constants;

import javax.persistence.*;

import java.util.Date;

/**
 * @author Raysmond<i@raysmond.com>
 */
@Entity
@Table(name = "processedit")
public class ProcessEdit extends BaseModel {

    private String userType;

    @ManyToOne
    @JoinColumn(name = "process")
    private Process process;

    private Date datetime;

    private String step;

    private String sessionid;
    
    private String type;
    
    private String city;

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
        return Constants.STEPS_MAP.get(this.step);
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
    
}