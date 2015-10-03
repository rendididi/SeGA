package org.sega.viewer.services;

import org.sega.viewer.models.Artifact;
import org.sega.viewer.models.ProcessInstance;
import org.sega.viewer.models.Process;
import org.sega.viewer.repositories.ProcessInstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Raysmond<jiankunlei@gmail.com>.
 */
@Service
public class ProcessInstanceService {
    @Autowired
    private ProcessInstanceRepository instanceRepository;

    @Autowired
    private ArtifactService artifactService;

    public ProcessInstance createProcessInstance(Process process){
        ProcessInstance instance = new ProcessInstance(process);

        Artifact mainArtifact = artifactService.createMainArtifact(instance);
        instance.setMainArtifact(mainArtifact);

        return instanceRepository.save(instance);
    }
}
