package com.fiapster.backlog.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;




@Entity
@Table(name = "emails")
public class EmailStatus implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "user_id")
	private Integer id;
	
	@OneToOne
	@MapsId
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private SysUser user;
	
	@Column
	private boolean emailBemVindo;
	
	@Column
	private boolean emailConfig;
	
	public EmailStatus() {
		
	}

	public EmailStatus(boolean emailBemVindo,boolean emailConfig, SysUser user) {
		super();
		this.user = user;
		this.emailBemVindo = emailBemVindo;
		this.emailConfig = emailConfig;
	}


	public boolean isEmailBemVindo() {
		return emailBemVindo;
	}

	public void setEmailBemVindo(boolean emailBemVindo) {
		this.emailBemVindo = emailBemVindo;
	}

	public boolean isEmailConfig() {
		return emailConfig;
	}

	public void setEmailConfig(boolean emailConfig) {
		this.emailConfig = emailConfig;
	}
	
}
