package com.fiapster.backlog.models;

public class Cliente {
	
	private int id;
	private String firstname;
	private String lastname;
	private String cpf;
	private String rg;
	private String email;
	private String data_criacao;
	
	
	public Cliente(int id, String firstname, String lastname, String cpf, String rg, String email,
			String data_criacao) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.cpf = cpf;
		this.rg = rg;
		this.email = email;
		this.data_criacao = data_criacao;
	}
	
	public Cliente() {
		
	}
	
	public String getData_criacao() {
		return data_criacao;
	}
	public void setData_criacao(String data_criacao) {
		this.data_criacao = data_criacao;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstname;
	}
	public void setFirstName(String firstName) {
		this.firstname = firstName;
	}
	public String getLastName() {
		return lastname;
	}
	public void setLastName(String lastName) {
		lastname = lastName;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	
	
}
