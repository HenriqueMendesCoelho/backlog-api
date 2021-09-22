package com.fiapster.backlog.dto;

import java.io.Serializable;

public class ConfigSystemNonAdminBadgeDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int nivelBadge1;
	private int nivelBadge2;
	private int nivelBadge3;
	private int nivelBadge4;
	
	public ConfigSystemNonAdminBadgeDTO() {
		
	}

	public int getNivelBadge1() {
		return nivelBadge1;
	}

	public void setNivelBadge1(int nivelBadge1) {
		this.nivelBadge1 = nivelBadge1;
	}

	public int getNivelBadge2() {
		return nivelBadge2;
	}

	public void setNivelBadge2(int nivelBadge2) {
		this.nivelBadge2 = nivelBadge2;
	}

	public int getNivelBadge3() {
		return nivelBadge3;
	}

	public void setNivelBadge3(int nivelBadge3) {
		this.nivelBadge3 = nivelBadge3;
	}

	public int getNivelBadge4() {
		return nivelBadge4;
	}

	public void setNivelBadge4(int nivelBadge4) {
		this.nivelBadge4 = nivelBadge4;
	}
}
