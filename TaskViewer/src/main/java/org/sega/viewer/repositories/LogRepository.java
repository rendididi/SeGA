package org.sega.viewer.repositories;

import java.util.List;

import org.sega.viewer.models.Log;
import org.sega.viewer.models.ProcessEdit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by wxf  805915717@qq.com
 */
@Repository
@Transactional
public interface LogRepository extends JpaRepository<Log, Long> {
	/*@Query("select l from log l where l.type = :type")
	List<Log> findByType(@Param("type") String type);*/
	Page<Log> findByType(String type, Pageable pageable);
}