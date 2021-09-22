package com.fiapster.backlog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fiapster.backlog.models.EmailStatus;

@Repository
public interface EmailStatusRepository extends JpaRepository<EmailStatus, Integer> {

}
