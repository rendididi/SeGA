package org.sega.viewer.models;


import javax.persistence.*;

/**
 * @author Raysmond<jiankunlei@gmail.com>.
 */
@Entity
@Table(name = "process_instances")
public class ProcessInstance extends BaseModel{
    @ManyToOne
    private Process process;

    @OneToOne(mappedBy = "processInstance")
    private Artifact mainArtifact;

    public ProcessInstance() {

    }

    public ProcessInstance(Process process) {
        this.process = process;
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
}
