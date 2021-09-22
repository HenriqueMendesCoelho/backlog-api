package com.fiapster.backlog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fiapster.backlog.models.ConfigSystem;

@Repository
public interface ConfigSystemRepository extends JpaRepository<ConfigSystem, Integer>{

}
