package com.fiapster.backlog.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fiapster.backlog.enums.Perfil;

public class SysUserSS implements UserDetails{
	private static final long serialVersionUID = 1L;
	
	private Integer userid;
	private String email;
	private String senha;
	private boolean Stemp;
	private int credTmpUses;
	private int Qtd_FLogin;
	private Collection<? extends GrantedAuthority> authorities;
	
	public SysUserSS() {
		
	}
	
	public SysUserSS(Integer userid, String email, String senha, boolean Stemp, int credTmpUses, int Qtd_FLogin, Set<Perfil> perfis) {
		super();
		this.userid = userid;
		this.email = email;
		this.senha = senha;
		this.Stemp = Stemp;
		this.credTmpUses = credTmpUses;
		this.Qtd_FLogin = Qtd_FLogin;
		this.authorities = perfis.stream().map(i -> new SimpleGrantedAuthority(i.getDescricao())).collect(Collectors.toList());
	}

	public Integer getId() {
		return userid;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
		
	}

	@Override
	public boolean isAccountNonLocked() {
		if(Qtd_FLogin >= 10) {
			return false;
		}else {
			return true;
		}
		
	}

	@Override
	public boolean isCredentialsNonExpired() {

		if(Stemp) {
			if(credTmpUses >= 1) {
				return false;
			}else {
				return true;
			}
		}else {
			return true;
		}
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public boolean isStemp() {
		return Stemp;
	}

	public int getQtd_FLogin() {
		return Qtd_FLogin;
	}

	public void setQtd_FLogin(int qtd_FLogin) {
		Qtd_FLogin = qtd_FLogin;
	}

}
