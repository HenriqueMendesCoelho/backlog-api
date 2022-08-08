package com.fiapster.backlog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fiapster.backlog.models.SysUser;
import com.fiapster.backlog.repositories.SysUserRepository;
import com.fiapster.backlog.security.SysUserSS;

@Service
@CrossOrigin(origins = "*")
public class SysUserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private SysUserRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		SysUser user = repo.findByEmail(email);
		
		if(user == null) {
			throw new UsernameNotFoundException(email);
		}
		
		return new SysUserSS(user.getId(), user.getEmail(), user.getSenha(), user.isStemp(), user.getCredTmpUses(), user.getQtd_FLogin(), user.getPerfis());
	}
}
