package com.fiapster.backlog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fiapster.backlog.models.EmailStatus;

@Repository
public interface EmailStatusRepository extends JpaRepository<EmailStatus, Integer> {
	
	@Query(value = "UPDATE emails SET email_config= :status", nativeQuery = true)
	void enviaEmailConfig(@Param("status") int status);

}
