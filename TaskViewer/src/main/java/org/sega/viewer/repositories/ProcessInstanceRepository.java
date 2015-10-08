package org.sega.viewer.repositories;

import org.sega.viewer.models.ProcessInstance;
import org.sega.viewer.models.Process;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Raysmond<jiankunlei@gmail.com>.
 */
@Repository
@Transactional
public interface ProcessInstanceRepository extends JpaRepository<ProcessInstance, Long>{
    List<ProcessInstance> findAllByProcess(Process process, Sort sort);
    Page<ProcessInstance> findByNextTaskNot(String nextTask, Pageable pageable);
}
