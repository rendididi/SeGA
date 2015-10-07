package org.sega.viewer.repositories;

import org.sega.viewer.models.ProcessInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Raysmond<jiankunlei@gmail.com>.
 */
@Repository
@Transactional
public interface ProcessInstanceRepository extends JpaRepository<ProcessInstance, Long>{

}