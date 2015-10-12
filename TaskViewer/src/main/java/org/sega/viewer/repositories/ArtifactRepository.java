package org.sega.viewer.repositories;

import org.sega.viewer.models.Artifact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Raysmond<i@raysmond.com>.
 */
@Repository
@Transactional
public interface ArtifactRepository extends JpaRepository<Artifact, Long>{
    Artifact findByEntityId(Long entityId);
}
