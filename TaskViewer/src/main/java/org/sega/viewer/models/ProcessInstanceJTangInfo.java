package org.sega.viewer.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by glp on 2015/10/11.
 */
@Entity
@Table(name = "process_instance_jtang_info")
public class ProcessInstanceJTangInfo extends BaseModel {

    @OneToOne
    private ProcessInstance processInstance;


    public ProcessInstance getProcessInstance() {
        return processInstance;
    }

    public void setProcessInstance(ProcessInstance processInstance) {
        this.processInstance = processInstance;
    }
}
