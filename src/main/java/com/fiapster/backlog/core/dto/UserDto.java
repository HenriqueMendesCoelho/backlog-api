package com.fiapster.backlog.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDto {
	
	@JsonProperty("sub")
	private String login;
	@JsonProperty("exp")
	private Long expiracao;
	@JsonProperty("aud")
	private String audiencia;
	
	public UserDto() {}
	
	public UserDto(String login, Long expiracao, String audiencia) {
		super();
		this.login = login;
		this.expiracao = expiracao;
		this.audiencia = audiencia;
	}

	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public Long getExpiracao() {
		return expiracao;
	}
	public void setExpiracao(Long expiracao) {
		this.expiracao = expiracao;
	}
	public String getAudiencia() {
		return audiencia;
	}
	public void setAudiencia(String audiencia) {
		this.audiencia = audiencia;
	}

	@Override
	public String toString() {
		return "UserDto [login=" + login + ", expiracao=" + expiracao + ", audiencia=" + audiencia + "]";
	}
}
