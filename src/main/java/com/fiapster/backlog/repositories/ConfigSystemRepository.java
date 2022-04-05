package com.fiapster.backlog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fiapster.backlog.models.ConfigSystem;

@Repository
public interface ConfigSystemRepository extends JpaRepository<ConfigSystem, Integer>{
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE public.sysusers s SET nivel = CASE WHEN FLOOR( s.pontos / :nivel ) <=1 THEN 1 ELSE FLOOR( s.pontos/ :nivel ) END", nativeQuery = true)
	void updateReprocessarNivel(@Param("nivel") int nivel);
	
}
