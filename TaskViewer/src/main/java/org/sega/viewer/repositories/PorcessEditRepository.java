package org.sega.viewer.repositories;

import org.sega.viewer.models.*;
import org.sega.viewer.models.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by glp on 2015/10/4.
 */

@Repository
@Transactional
public interface PorcessEditRepository extends JpaRepository<Process, Long> {
}
