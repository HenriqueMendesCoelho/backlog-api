package com.fiapster.backlog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fiapster.backlog.models.EmailStatus;

@Repository
public interface EmailStatusRepository extends JpaRepository<EmailStatus, Integer> {
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE emails SET email_config= :status", nativeQuery = true)
	void enviaEmailConfig(@Param("status") boolean status);

}
