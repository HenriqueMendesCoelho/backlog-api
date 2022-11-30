package com.fiapster.backlog.models;

import java.io.Serializable;



import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * 
 * @author Henrique
 *
 */

@Entity
@Table(name = "config")
public class ConfigSystem implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(length = 1)
	@JsonIgnore
	private Integer id;
	
	//Valores dos Itens
	@Column(nullable = false)
	private int preco_pontual;
	@Column(nullable = false)
	private int preco_coroa;
	@Column(nullable = false)
	private int preco_superLimpo;
	@Column(nullable = false)
	private int preco_persistente;
	@Column(nullable = false)
	private int preco_cruz;
	@Column(nullable = false)
	private int preco_alcool;
	
	//Intervalo da higienização das mãos
	@Column(nullable = false)
	private int intervaloReset;
	@Column(nullable = false)
	private int intervaloMax;
	@Column(nullable = false)
	private int intervaloIntermediario;
	@Column(nullable = false)
	private int intervaloMin;
	
	//Pontos recebidos
	@Column(nullable = false)
	private int pontosPrimeiraExec;
	@Column(nullable = false)
	private int pontosReset; // Entre turnos
	@Column(nullable = false)
	private int pontosIntervalMax;
	@Column(nullable = false)
	private int pontosIntervalInter;
	@Column(nullable = false)
	private int pontosIntervalMin;
	
	//Creditos recebidos
	@Column(nullable = false)
	private int creditosPrimeiraExec;
	@Column(nullable = false)
	private int creditosReset; // Entre turnos
	@Column(nullable = false)
	private int creditosIntervalMax;
	@Column(nullable = false)
	private int creditosIntervalInter;
	@Column(nullable = false)
	private int creditosIntervalMin;
	
	//Níveis para as Badges
	@Column(nullable = false)
	private int nivelBadge1;
	@Column(nullable = false)
	private int nivelBadge2;
	@Column(nullable = false)
	private int nivelBadge3;
	@Column(nullable = false)
	private int nivelBadge4;
	
	//Quantidade de pontos para passar de nível
	@Column(nullable = false)
	private int pontosNivel;
	
	//Controle de alteração
	@Column(length = 102, nullable = false)
	private String ultimoEditor;
	@Column(length = 50, nullable = false)
	private String ultimaAlt;
	
	public ConfigSystem() {
		
	}

	public ConfigSystem(int preco_pontual, int preco_coroa, int preco_superLimpo, int preco_persistente,
			int preco_cruz, int preco_alcool, int intervaloReset, int intervaloMax, int intervaloIntermediario,
			int intervaloMin, int pontosPrimeiraExec, int pontosReset, int pontosIntervalMax, int pontosIntervalInter,
			int pontosIntervalMin, int creditosPrimeiraExec, int creditosReset, int creditosIntervalMax,
			int creditosIntervalInter, int creditosIntervalMin, int nivelBadge1, int nivelBadge2, int nivelBadge3,
			int nivelBadge4, int pontosNivel, String ultimoEditor, String ultimaAlt) {
		super();
		this.preco_pontual = preco_pontual;
		this.preco_coroa = preco_coroa;
		this.preco_superLimpo = preco_superLimpo;
		this.preco_persistente = preco_persistente;
		this.preco_cruz = preco_cruz;
		this.preco_alcool = preco_alcool;
		this.intervaloReset = intervaloReset;
		this.intervaloMax = intervaloMax;
		this.intervaloIntermediario = intervaloIntermediario;
		this.intervaloMin = intervaloMin;
		this.pontosPrimeiraExec = pontosPrimeiraExec;
		this.pontosReset = pontosReset;
		this.pontosIntervalMax = pontosIntervalMax;
		this.pontosIntervalInter = pontosIntervalInter;
		this.pontosIntervalMin = pontosIntervalMin;
		this.creditosPrimeiraExec = creditosPrimeiraExec;
		this.creditosReset = creditosReset;
		this.creditosIntervalMax = creditosIntervalMax;
		this.creditosIntervalInter = creditosIntervalInter;
		this.creditosIntervalMin = creditosIntervalMin;
		this.nivelBadge1 = nivelBadge1;
		this.nivelBadge2 = nivelBadge2;
		this.nivelBadge3 = nivelBadge3;
		this.nivelBadge4 = nivelBadge4;
		this.pontosNivel = pontosNivel;
		this.ultimoEditor = ultimoEditor;
		this.ultimaAlt = ultimaAlt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getPreco_pontual() {
		return preco_pontual;
	}

	public void setPreco_pontual(int preco_pontual) {
		this.preco_pontual = preco_pontual;
	}

	public int getPreco_coroa() {
		return preco_coroa;
	}

	public void setPreco_coroa(int preco_coroa) {
		this.preco_coroa = preco_coroa;
	}

	public int getPreco_superLimpo() {
		return preco_superLimpo;
	}

	public void setPreco_superLimpo(int preco_superLimpo) {
		this.preco_superLimpo = preco_superLimpo;
	}

	public int getPreco_persistente() {
		return preco_persistente;
	}

	public void setPreco_persistente(int preco_persistente) {
		this.preco_persistente = preco_persistente;
	}

	public int getPreco_cruz() {
		return preco_cruz;
	}

	public void setPreco_cruz(int preco_cruz) {
		this.preco_cruz = preco_cruz;
	}

	public int getPreco_alcool() {
		return preco_alcool;
	}

	public void setPreco_alcool(int preco_alcool) {
		this.preco_alcool = preco_alcool;
	}

	public int getIntervaloReset() {
		return intervaloReset;
	}

	public void setIntervaloReset(int intervaloReset) {
		this.intervaloReset = intervaloReset;
	}

	public int getIntervaloMax() {
		return intervaloMax;
	}

	public void setIntervaloMax(int intervaloMax) {
		this.intervaloMax = intervaloMax;
	}

	public int getIntervaloIntermediario() {
		return intervaloIntermediario;
	}

	public void setIntervaloIntermediario(int intervaloIntermediario) {
		this.intervaloIntermediario = intervaloIntermediario;
	}

	public int getIntervaloMin() {
		return intervaloMin;
	}

	public void setIntervaloMin(int intervaloMin) {
		this.intervaloMin = intervaloMin;
	}

	public int getPontosPrimeiraExec() {
		return pontosPrimeiraExec;
	}

	public void setPontosPrimeiraExec(int pontosPrimeiraExec) {
		this.pontosPrimeiraExec = pontosPrimeiraExec;
	}

	public int getPontosReset() {
		return pontosReset;
	}

	public void setPontosReset(int pontosReset) {
		this.pontosReset = pontosReset;
	}

	public int getPontosIntervalMax() {
		return pontosIntervalMax;
	}

	public void setPontosIntervalMax(int pontosIntervalMax) {
		this.pontosIntervalMax = pontosIntervalMax;
	}

	public int getPontosIntervalInter() {
		return pontosIntervalInter;
	}

	public void setPontosIntervalInter(int pontosIntervalInter) {
		this.pontosIntervalInter = pontosIntervalInter;
	}

	public int getPontosIntervalMin() {
		return pontosIntervalMin;
	}

	public void setPontosIntervalMin(int pontosIntervalMin) {
		this.pontosIntervalMin = pontosIntervalMin;
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

	public int getPontosNivel() {
		return pontosNivel;
	}

	public void setPontosNivel(int pontosNivel) {
		this.pontosNivel = pontosNivel;
	}

	public String getUltimoEditor() {
		return ultimoEditor;
	}

	public void setUltimoEditor(String ultimoEditor) {
		this.ultimoEditor = ultimoEditor;
	}

	public String getUltimaAlt() {
		return ultimaAlt;
	}

	public void setUltimaAlt(String ultimaAlt) {
		this.ultimaAlt = ultimaAlt;
	}

	public int getCreditosPrimeiraExec() {
		return creditosPrimeiraExec;
	}

	public void setCreditosPrimeiraExec(int creditosPrimeiraExec) {
		this.creditosPrimeiraExec = creditosPrimeiraExec;
	}

	public int getCreditosReset() {
		return creditosReset;
	}

	public void setCreditosReset(int creditosReset) {
		this.creditosReset = creditosReset;
	}

	public int getCreditosIntervalMax() {
		return creditosIntervalMax;
	}

	public void setCreditosIntervalMax(int creditosIntervalMax) {
		this.creditosIntervalMax = creditosIntervalMax;
	}

	public int getCreditosIntervalInter() {
		return creditosIntervalInter;
	}

	public void setCreditosIntervalInter(int creditosIntervalInter) {
		this.creditosIntervalInter = creditosIntervalInter;
	}

	public int getCreditosIntervalMin() {
		return creditosIntervalMin;
	}

	public void setCreditosIntervalMin(int creditosIntervalMin) {
		this.creditosIntervalMin = creditosIntervalMin;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
