package com.fiapster.backlog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fiapster.backlog.models.ConfigSystem;

@Repository
public interface ConfigSystemRepository extends JpaRepository<ConfigSystem, Integer>{
	
	@Query(value = "UPDATE sysusers SET nivel = CASE WHEN FLOOR(pontos/ :nivel )<=1 THEN 1 ELSE FLOOR(pontos/ :nivel ) END", nativeQuery = true)
	void reprocessarNivel(@Param("nivel") int nivel);
	
}
