package org.sega.viewer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.sega.viewer.models.Process;

/**
 * @author Raysmond<jiankunlei@gmail.com>.
 */
@Repository
@Transactional
public interface ProcessRepository extends JpaRepository<Process, Long>{

}
