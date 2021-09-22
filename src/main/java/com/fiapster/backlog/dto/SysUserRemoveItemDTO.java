package com.fiapster.backlog.dto;

import java.io.Serializable;

public class SysUserRemoveItemDTO implements Serializable{
	private static final long serialVersionUID = 1L;
		
	private String email;
	private String item;
	
	public SysUserRemoveItemDTO() {	
	}

	public String getEmail() {
		return email;
	}

	public String getItem() {
		return item;
	}
	
}
