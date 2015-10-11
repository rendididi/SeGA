package org.sega.viewer.models;

import org.SeGA.model.*;

import javax.persistence.*;

/**
 * Created by glp on 2015/10/11.
 */
@Entity
@Table(name = "process_instance_jtang_info")
public class ProcessInstanceJTangInfo extends BaseModel {

    @OneToOne
    private ProcessInstance processInstance;

    @Lob
    private org.SeGA.model.Process jtangProcess;

    @Lob
    private org.SeGA.model.JTangProcIns jtangInstance;

    public ProcessInstanceJTangInfo() {
    }

    public ProcessInstanceJTangInfo(ProcessInstance processInstance, org.SeGA.model.Process jtangProcess, JTangProcIns jtangInstance) {
        this.processInstance = processInstance;
        this.jtangProcess = jtangProcess;
        this.jtangInstance = jtangInstance;
    }

    public ProcessInstance getProcessInstance() {
        return processInstance;
    }

    public void setProcessInstance(ProcessInstance processInstance) {
        this.processInstance = processInstance;
    }

    public org.SeGA.model.Process getJtangProcess() {
        return jtangProcess;
    }

    public void setJtangProcess(org.SeGA.model.Process jtangProcess) {
        this.jtangProcess = jtangProcess;
    }

    public org.SeGA.model.JTangProcIns getJtangInstance() {
        return jtangInstance;
    }

    public void setJtangInstance(org.SeGA.model.JTangProcIns jtangInstance) {
        this.jtangInstance = jtangInstance;
    }
}
