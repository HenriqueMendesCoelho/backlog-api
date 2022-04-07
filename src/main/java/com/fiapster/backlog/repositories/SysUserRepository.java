package com.fiapster.backlog.repositories;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fiapster.backlog.models.SysUser;

@Repository
public interface SysUserRepository extends JpaRepository<SysUser, Integer> {
	
	@Transactional(readOnly = true)
	SysUser findByEmail(String email);
	
	@Transactional(readOnly = true)
	List<SysUser> findAllByOrderByPontosDesc();
	
	@Transactional(readOnly = true)
	List<SysUser> findAllByOrderByIdAsc();

}
