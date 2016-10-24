package org.sega.viewer.repositories;

import org.sega.viewer.models.Approve;
import org.sega.viewer.models.ArtifactList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ApproveRespository extends JpaRepository<Approve, Long>{

}
