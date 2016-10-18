package org.sega.viewer.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.sega.viewer.models.Process;
import org.sega.viewer.models.ProcessEdit;

/**
 * @author Raysmond<i@raysmond.com>.
 */
@Repository
@Transactional
public interface ProcessRepository extends JpaRepository<Process, Long>{
	@Query("select p from Process p where p.type = :type and p.city = :city")
    List<ProcessEdit> findByTypeAndCity(@Param("type") String type, @Param("city") String city);

    @Query("select p from Process p where p.city = :city")
	List<ProcessEdit> findByCity(@Param("city") String city);
}
