package org.sega.viewer.services;

import org.json.JSONArray;
import org.sega.viewer.models.Artifact;
import org.sega.viewer.models.Attribute;
import org.sega.viewer.models.ProcessInstance;
import org.sega.viewer.repositories.ArtifactRepository;
import org.sega.viewer.repositories.AttributeRepository;
import org.sega.viewer.repositories.ProcessInstanceRepository;
import org.sega.viewer.services.support.AttributeType;
import org.sega.viewer.services.support.EntityJsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Raysmond<jiankunlei@gmail.com>.
 */
@Service
public class ArtifactService {
    @Autowired
    private ArtifactRepository artifactRepository;

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private ProcessInstanceRepository processInstanceRepository;

    // TODO
    @Transactional
    public Artifact createMainArtifact(ProcessInstance processInstance){
        Artifact artifact = new Artifact();
        artifact.setProcessInstance(processInstance);

        EntityJsonService entityJsonService = new EntityJsonService(processInstance.getProcess().getEntityJSON());
        artifact.setName(entityJsonService.getEntityName());
        artifact.setEntityId(generateEntityId());

        artifact = artifactRepository.save(artifact);

        processInstance.setMainArtifact(artifact);
        processInstanceRepository.save(processInstance);

        // createAttributes(artifact, entityJsonService);

        return artifact;
    }

    /**
     * TODO
     *
     * Generate random entity id
     * Actually, it should invoke the EDB service to get a key value from corresponding EDB table
     * @return
     */
    public Long generateEntityId(){
        Long id = (long) (Math.random() * 1000000);
        while (artifactRepository.findByEntityId(id) != null){
            id = (long) (Math.random() * 1000000);
        }

        return id;
    }


    private List<Attribute> createAttributes(Artifact artifact, EntityJsonService entityJsonService){
        JSONArray children = entityJsonService.getEntityJson().getJSONArray("children");
        List<AttributeType> attributeTypes = entityJsonService.parseAttributes(children);

        List<Attribute> attributes = new ArrayList<>();

        attributeTypes.forEach(attributeType -> {
            Attribute attribute = new Attribute(
                    artifact,
                    attributeType.getId(),
                    attributeType.getText(),
                    attributeType.getValueType(),
                    null
            );

            attribute.initializeValue();

            attributeRepository.save(attribute);

            attributes.add(attribute);
        });

        return attributes;
    }

    public Artifact updateArtifact(Artifact artifact){
        return artifactRepository.save(artifact);
    }
}
