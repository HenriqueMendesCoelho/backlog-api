package com.fiapster.backlog.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fiapster.backlog.enums.Item;
import com.fiapster.backlog.enums.Perfil;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

/**
 * 
 * @author Henrique
 *
 */
@Entity
@Table(name = "sysusers")
public class SysUser implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private Integer id;
	
	@Column(length = 50, nullable = false)
	private String nome;
	
	@Column
	private int pontos;
	
	@Column
	private int creditos;
	
	@Column(length = 20, nullable = false)
	private String cargo;
	
	@Column
	private int nivel;
	
	@JsonIgnore
	@Column(nullable = true, length = 40)
	private long data_exec;
	
	@Column(length = 25)
	private String data_criacao;
	
	@Column
	private boolean Stemp;
	
	@Column
	private int credTmpUses;
	
	@Column
	private int Qtd_FLogin;
	
	@Column
	private boolean aparecerNoRank;
	
	@JsonIgnore
	@OneToOne(mappedBy = "user", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private EmailStatus emailS;
	
	@Column(unique=true, length = 50, nullable = false)
	private String email;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="PERFIS")
	private Set<Integer> perfis = new HashSet<>();
	
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="ITENS")
	private Set<Integer> itens = new HashSet<>();
	
	@Column(length = 70, nullable = false)
	private String senha;
	
	public SysUser() {
		addPerfil(Perfil.USUARIO);
	}
	
	public SysUser(int id, String nome, int pontos, int creditos, String cargo, int nivel
			, String email, String senha, long data_exec, String data_criacao, String nova_senha, 
			boolean Stemp, int credTmpUses, int Qtd_FLogin, boolean aparecerNoRank, EmailStatus emailS) {
		super();
		this.id = id;
		this.nome = nome;
		this.pontos = pontos;
		this.creditos = creditos;
		this.cargo = cargo;
		this.nivel = nivel;
		this.email = email;
		this.senha = senha;
		this.data_criacao = data_criacao;
		this.data_exec = data_exec;
		this.Stemp = Stemp;
		this.credTmpUses = credTmpUses;
		this.Qtd_FLogin = Qtd_FLogin;
		this.emailS = emailS;
		this.aparecerNoRank = aparecerNoRank;
		addPerfil(Perfil.USUARIO);
	}
	
	public long getData_exec() {
		return data_exec;
	}

	public void setData_exec(long data_exec) {
		this.data_exec = data_exec;
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
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getPontos() {
		return pontos;
	}
	public void setPontos(int pontos) {
		this.pontos = pontos;
	}
	public int getCreditos() {
		return creditos;
	}

	public void setCreditos(int creditos) {
		this.creditos = creditos;
	}

	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Set<Perfil> getPerfis(){
		return perfis.stream().map(i -> Perfil.toEnum(i)).collect(Collectors.toSet());
	}
	
	public void addPerfil(Perfil perfil) {
		perfis.add(perfil.getCod());
	}
	
	public void removePerfil(Perfil perfil) {
		perfis.remove(perfil.getCod());
	}
	
	public Set<Item> getItens(){
		return itens.stream().map(i -> Item.toEnum(i)).collect(Collectors.toSet());
	}
	
	public void addItem(Item item) {
		itens.add(item.getCod());
	}
	
	public void removeItem(Item item) {
		itens.remove(item.getCod());
	}
	
	public boolean isStemp() {
		return Stemp;
	}

	public void setStemp(boolean stemp) {
		Stemp = stemp;
	}

	public int getCredTmpUses() {
		return credTmpUses;
	}

	public void setCredTmpUses(int credTmpUses) {
		this.credTmpUses = credTmpUses;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysUser other = (SysUser) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public int getQtd_FLogin() {
		return Qtd_FLogin;
	}

	public void setQtd_FLogin(int qtd_FLogin) {
		Qtd_FLogin = qtd_FLogin;
	}

	public EmailStatus getEmailS() {
		return emailS;
	}

	public void setEmailS(EmailStatus emailS) {
		this.emailS = emailS;
	}

	public void setPerfis(Set<Integer> perfis) {
		this.perfis = perfis;
	}

	public boolean isAparecerNoRank() {
		return aparecerNoRank;
	}

	public void setAparecerNoRank(boolean aparecerNoRank) {
		this.aparecerNoRank = aparecerNoRank;
	}
}
