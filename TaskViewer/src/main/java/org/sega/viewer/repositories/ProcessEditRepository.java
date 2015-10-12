package org.sega.viewer.repositories;

import org.sega.viewer.models.ProcessEdit;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by glp on 2015/10/4.
 */
@Repository
@Transactional
public interface ProcessEditRepository extends JpaRepository<ProcessEdit, Long> {
    List<ProcessEdit> findByStep(String step, Sort sort);
}
