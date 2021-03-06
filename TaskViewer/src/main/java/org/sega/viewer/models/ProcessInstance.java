package org.sega.viewer.models;


import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Raysmond<i@raysmond.com>.
 */
@Entity
@Table(name = "process_instances")
@Data
public class ProcessInstance extends BaseModel{
    public static final String STATE_COMPLETED = "已办结";
    public static final String EMPTY_KEY_VALUE = "NEW"; 

    private Integer businessId;
    private String operatorandtime;
    private String edb_name;
	public String getOperatorandtime() {
		return operatorandtime;
	}

	public void setOperatorandtime(String operatorandtime) {
		this.operatorandtime = operatorandtime;
	}

	public Integer getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	public String getEdb_name() {
		return edb_name;
	}

	public void setEdb_name(String edb_name) {
		this.edb_name = edb_name;
	}


	@ManyToOne
    private Process process;

    public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

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

    @Column(name = "sync_edb_at")
    private Date syncEdbAt;

    public ProcessInstance(){

    }

    public ProcessInstance(Process process) {
        this.process = process;
    }

    @PrePersist
    public void onPrePersist(){
        this.createdAt = new Date();
    }

}
