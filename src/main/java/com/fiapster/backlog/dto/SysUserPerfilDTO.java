package com.fiapster.backlog.dto;

import java.io.Serializable;

public class SysUserPerfilDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String email;
	
	public SysUserPerfilDTO() {
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

}
