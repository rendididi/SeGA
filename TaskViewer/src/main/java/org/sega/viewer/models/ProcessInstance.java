package org.sega.viewer.models;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Raysmond<jiankunlei@gmail.com>.
 */
@Entity
@Table(name = "process_instances")
public class ProcessInstance extends BaseModel{
    @ManyToOne
    private Process process;

}
