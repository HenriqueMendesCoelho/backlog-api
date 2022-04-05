package com.fiapster.backlog.services;


import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fiapster.backlog.dto.ConfigSystemNonAdminBadgeDTO;
import com.fiapster.backlog.dto.ConfigSystemNonAdminStoreDTO;
import com.fiapster.backlog.methods.ObterDataEHora;
import com.fiapster.backlog.models.ConfigSystem;
import com.fiapster.backlog.models.SysUser;
import com.fiapster.backlog.repositories.ConfigSystemRepository;
import com.fiapster.backlog.repositories.EmailStatusRepository;
import com.fiapster.backlog.repositories.SysUserRepository;
import com.fiapster.backlog.security.JWTUtil;

@Service
@CrossOrigin(origins = "*")
public class ConfigSystemService {
	
	@Autowired
	ConfigSystemRepository repo;
	
	@Autowired
	EmailStatusRepository repoEmail;
	
	@Autowired
	SysUserRepository repoUser;
	
	private ObterDataEHora d = new ObterDataEHora();
	
	@Autowired
	private JWTUtil jwtutil;
	
	@PostConstruct
	public void createDefaultSettingsConfig() {
		System.out.println("Verificando se existem configuracoes padroes");
		if(repo.count() < 1) {
			System.out.println("Criando configuracao padrao");
			ConfigSystem config = new ConfigSystem(500, 2500, 500, 500, 1500, 500, 720, 60, 35, 2, 10, 10, 0, 5, 10, 10, 10, 0, 5, 10, 25, 50, 80, 100, 100, "System", d.obterDataEHora());		
			repo.save(config);
			return;
		}
		System.out.println("Configuracao padrao validada com sucesso");
	}
	
	
	public void createOrUpdateConfig(ConfigSystem configN, HttpServletRequest request) throws Exception{
		String header = request.getHeader("Authorization");
		SysUser user = repoUser.findByEmail(jwtutil.getUsername(header.substring(7)));
		
		if(user.getQtd_FLogin() < 10) {
			boolean config_exist = false;
			
			if(repo.count() == 0) {
				config_exist = false;
			}else {
				config_exist = true;
			}
			
			//Validação de intervalos
			if(configN.getIntervaloReset() <= 0) {
				
				throw new IllegalArgumentException("Intervalo reset não pode ser menor ou igual a 0.");
				
			}else if(configN.getIntervaloReset() <= configN.getIntervaloMax()
					|| configN.getIntervaloReset() <= configN.getIntervaloIntermediario()
					|| configN.getIntervaloReset() <= configN.getIntervaloMin()) {
				throw new IllegalArgumentException("Intervalo reset deve ser maior que todos os outros intervalos.");
			}
			
			if(configN.getIntervaloMax() <= 0) {
				throw new IllegalArgumentException("Intervalo Max não pode ser menor ou igual a 0.");
			}else if(configN.getIntervaloMax() <= configN.getIntervaloIntermediario() 
					|| configN.getIntervaloMax() <= configN.getIntervaloMin()) {
				throw new IllegalArgumentException("Intervalo Max deve ser maior que os intervalos abaixo dele.");
			}
			
			if(configN.getIntervaloIntermediario() <= 0) {
				throw new IllegalArgumentException("Intervalo intermediario não pode ser menor ou igual a 0.");
			}else if(configN.getIntervaloIntermediario() <= configN.getIntervaloMin()) {
				throw new IllegalArgumentException("Intervalo intermediario deve ser maior que os intervalo minimo.");
			}
			
			if(configN.getIntervaloMin() < 0) {
				throw new IllegalArgumentException("Intervalo intermediario não pode ser menor que 0.");
			}
			
			//Validação de nível das badges
			if(configN.getNivelBadge4() <= 0) {
				throw new IllegalArgumentException("Nivel da badge4 não pode ser menor ou igual a 0.");
			}else if(configN.getNivelBadge4() <= configN.getNivelBadge3() 
					|| configN.getNivelBadge4() <= configN.getNivelBadge2() 
					|| configN.getNivelBadge4() <= configN.getNivelBadge1()) {
				throw new IllegalArgumentException("Nivel da badge4 deve ser maior do que das outras badges.");
			}
			
			if(configN.getNivelBadge3() <= 0) {
				throw new IllegalArgumentException("Nivel da badge3 não pode ser menor ou igual a 0.");
			}else if(configN.getNivelBadge3() <= configN.getNivelBadge2() 
					|| configN.getNivelBadge3() <= configN.getNivelBadge1()) {
				throw new IllegalArgumentException("Nivel da badge3 deve ser maior do que da badge2 e badge1.");
			}
			
			if(configN.getNivelBadge2() <= 0) {
				throw new IllegalArgumentException("Nivel da badge2 não pode ser menor ou igual a 0.");
			}else if (configN.getNivelBadge2() <= configN.getNivelBadge1()) {
				throw new IllegalArgumentException("Nivel da badge2 deve ser maior do que da badge1.");
			}
			
			if(configN.getNivelBadge1() < 0) {
				throw new IllegalArgumentException("Nivel da badge1 não pode ser menor que 0.");
			}
			
			//Validação de pontuação
			if(configN.getPontosPrimeiraExec() < 0) {
				throw new IllegalArgumentException("Pontos da primeira execução não pode ser menor que 0.");
			}
			
			if(configN.getPontosReset() < 0) {
				throw new IllegalArgumentException("Pontos reset execução não pode ser menor que 0.");
			}
			
			if(configN.getPontosIntervalMax() < 0) {
				throw new IllegalArgumentException("Pontos maximo execução não pode ser menor que 0.");
			}
			
			if(configN.getPontosIntervalInter() < 0) {
				throw new IllegalArgumentException("Pontos intermediario execução não pode ser menor que 0.");
			}
			
			if(configN.getPontosIntervalMin() < 0) {
				throw new IllegalArgumentException("Pontos minimo execução não pode ser menor que 0.");
			}
			
			//Validação do creditos recebidos
			if(configN.getCreditosPrimeiraExec() < 0) {
				throw new IllegalArgumentException("Creditos da primeira execução não pode ser menor que 0.");
			}
			
			if(configN.getCreditosReset() < 0) {
				throw new IllegalArgumentException("Creditos reset não pode ser menor que 0.");
			}
			
			if(configN.getCreditosIntervalMax() < 0) {
				throw new IllegalArgumentException("Creditos do intervalo maximo não pode ser menor que 0.");
			}
			
			if(configN.getCreditosIntervalInter() < 0) {
				throw new IllegalArgumentException("Creditos do intervalo intermediario não pode ser menor que 0.");
			}
			
			if(configN.getCreditosIntervalMin() < 0) {
				throw new IllegalArgumentException("Creditos do intervalo minimo não pode ser menor que 0.");
			}
			
			//Validação do preço dos itens
			if(configN.getPreco_alcool() < 0) {
				throw new IllegalArgumentException("O preço do item 'ALCOOL' não pode ser menor que 0.");
			}
			
			if(configN.getPreco_coroa() < 0) {
				throw new IllegalArgumentException("O preço do item 'COROA' não pode ser menor que 0.");
			}
			
			if(configN.getPreco_cruz() < 0) {
				throw new IllegalArgumentException("O preço do item 'CRUZ' não pode ser menor que 0.");
			}
			
			if(configN.getPreco_persistente() < 0) {
				throw new IllegalArgumentException("O preço do item 'PERSISTENTE' não pode ser menor que 0.");
			}
			
			if(configN.getPreco_pontual() < 0) {
				throw new IllegalArgumentException("O preço do item 'PONTUAL' não pode ser menor que 0.");
			}
			
			if(configN.getPreco_superLimpo() < 0) {
				throw new IllegalArgumentException("O preço do item 'SUPER_LIMPO' não pode ser menor que 0.");
			}
			
			//Validação de reprocessamento dos pontos
			if(config_exist) {
				ConfigSystem configA = repo.getById(1);
				if(configN.getPontosNivel() <= 0) {
					throw new IllegalArgumentException("Pontos para passar de nível não pode ser menor ou igual a 0.");
				} else if(configN.getPontosNivel() != configA.getPontosNivel()) {
					repo.updateReprocessarNivel(configN.getPontosNivel());
				}
			}else {
				if(configN.getPontosNivel() <= 0) {
					throw new IllegalArgumentException("Pontos para passar de nível não pode ser menor ou igual a 0.");
				}
				repo.updateReprocessarNivel(configN.getPontosNivel());
			}
			configN.setUltimoEditor(user.getNome()+"; "+user.getEmail());
			
			configN.setUltimaAlt(d.obterDataEHora());
			
			if(config_exist) {
				ConfigSystem configA = repo.getById(1);
				
				//Validação para envio do email de nova configuração
				if(configN.getCreditosIntervalInter() != configA.getCreditosIntervalInter()) {
					repoEmail.enviaEmailConfig(false);
				}else if(configN.getCreditosIntervalMax() != configA.getCreditosIntervalMax()) {
					repoEmail.enviaEmailConfig(false);
				}else if(configN.getCreditosIntervalMin() != configA.getCreditosIntervalMin()) {
					repoEmail.enviaEmailConfig(false);
				}else if(configN.getCreditosPrimeiraExec() != configA.getCreditosPrimeiraExec()) {
					repoEmail.enviaEmailConfig(false);
				}else if(configN.getCreditosReset() != configA.getCreditosReset()) {
					repoEmail.enviaEmailConfig(false);
				}else if(configN.getPontosIntervalInter() != configA.getPontosIntervalInter()) {
					repoEmail.enviaEmailConfig(false);
				}else if(configN.getPontosIntervalMax() != configA.getPontosIntervalMax()) {
					repoEmail.enviaEmailConfig(false);
				}else if(configN.getPontosIntervalMin() != configA.getPontosIntervalMin()) {
					repoEmail.enviaEmailConfig(false);
				}else if(configN.getPontosPrimeiraExec() != configA.getPontosPrimeiraExec()) {
					repoEmail.enviaEmailConfig(false);
				}else if(configN.getPontosReset() != configA.getPontosReset()) {
					repoEmail.enviaEmailConfig(false);
				}else if(configN.getIntervaloIntermediario() != configA.getIntervaloIntermediario()) {
					repoEmail.enviaEmailConfig(false);
				}else if(configN.getIntervaloMax() != configA.getIntervaloMax()) {
					repoEmail.enviaEmailConfig(false);
				}else if(configN.getIntervaloMin() != configA.getIntervaloMin()) {
					repoEmail.enviaEmailConfig(false);
				}else if(configN.getIntervaloMin() != configA.getIntervaloMin()) {
					repoEmail.enviaEmailConfig(false);
				}else if(configN.getIntervaloReset() != configA.getIntervaloReset()) {
					repoEmail.enviaEmailConfig(false);
				}else if(configN.getPreco_alcool() != configA.getPreco_alcool()) {
					repoEmail.enviaEmailConfig(false);
				}else if(configN.getPreco_coroa() != configA.getPreco_coroa()) {
					repoEmail.enviaEmailConfig(false);
				}else if(configN.getPreco_cruz() != configA.getPreco_cruz()) {
					repoEmail.enviaEmailConfig(false);
				}else if(configN.getPreco_persistente() != configA.getPreco_persistente()) {
					repoEmail.enviaEmailConfig(false);
				}else if(configN.getPreco_pontual() != configA.getPreco_pontual()) {
					repoEmail.enviaEmailConfig(false);
				}else if(configN.getPreco_superLimpo() != configA.getPreco_superLimpo()) {
					repoEmail.enviaEmailConfig(false);
				}else if(configN.getNivelBadge1() != configA.getNivelBadge1()) {
					repoEmail.enviaEmailConfig(false);
				}else if(configN.getNivelBadge2() != configA.getNivelBadge2()) {
					repoEmail.enviaEmailConfig(false);
				}else if(configN.getNivelBadge3() != configA.getNivelBadge3()) {
					repoEmail.enviaEmailConfig(false);
				}else if(configN.getNivelBadge4() != configA.getNivelBadge4()) {
					repoEmail.enviaEmailConfig(false);
				}else if(configN.getPontosNivel() != configA.getPontosNivel()) {
					repoEmail.enviaEmailConfig(false);
				}
				
				configA.setCreditosIntervalInter(configN.getCreditosIntervalInter());
				configA.setCreditosIntervalMax(configN.getCreditosIntervalMax());
				configA.setCreditosIntervalMin(configN.getCreditosIntervalMin());//
				configA.setCreditosPrimeiraExec(configN.getCreditosPrimeiraExec());//
				configA.setCreditosReset(configN.getCreditosReset());//
				configA.setPontosIntervalInter(configN.getPontosIntervalInter());//
				configA.setPontosIntervalMax(configN.getPontosIntervalMax());//
				configA.setPontosIntervalMin(configN.getPontosIntervalMin());//
				configA.setPontosPrimeiraExec(configN.getPontosPrimeiraExec());//
				configA.setPontosReset(configN.getPontosReset());//
				configA.setIntervaloIntermediario(configN.getIntervaloIntermediario());//
				configA.setIntervaloMax(configN.getIntervaloMax());//
				configA.setIntervaloMin(configN.getIntervaloMin());//
				configA.setIntervaloReset(configN.getIntervaloReset());//
				configA.setPreco_alcool(configN.getPreco_alcool());//
				configA.setPreco_coroa(configN.getPreco_coroa());//
				configA.setPreco_cruz(configN.getPreco_cruz());//
				configA.setPreco_persistente(configN.getPreco_persistente());//
				configA.setPreco_pontual(configN.getPreco_pontual());//
				configA.setPreco_superLimpo(configN.getPreco_superLimpo());//
				configA.setNivelBadge1(configN.getNivelBadge1());
				configA.setNivelBadge2(configN.getNivelBadge2());
				configA.setNivelBadge3(configN.getNivelBadge3());
				configA.setNivelBadge4(configN.getNivelBadge4());
				configA.setPontosNivel(configN.getPontosNivel());
				configA.setUltimoEditor(configN.getUltimoEditor());
				configA.setUltimaAlt(configN.getUltimaAlt());
				repo.saveAndFlush(configA);
			}else {
				repo.save(configN);
			}
		}else {
			throw new IllegalAccessException("Conta bloqueada, contate um administrador.");
		}
	}
	
	public ConfigSystem getConfig(HttpServletRequest request) throws IllegalAccessException {
		String header = request.getHeader("Authorization");
		SysUser user = repoUser.findByEmail(jwtutil.getUsername(header.substring(7)));
		
		if(user.getQtd_FLogin() < 10) {
			if(repo.count() == 0) {
				throw new IllegalAccessException("Não existe configurações na base de dados.");
			}else {
				ConfigSystem config = repo.findById(1).get();
				return config;
			}
		}else {
			throw new IllegalAccessException("Conta bloqueada, contate um administrador.");
		}
	}
	
	public ConfigSystemNonAdminStoreDTO getConfigNonAdminStore() throws Exception {
		
		ConfigSystemNonAdminStoreDTO configNonADM = new ConfigSystemNonAdminStoreDTO();
		
		if(repo.count() == 0) {
			throw new IllegalAccessException("Não existe configurações na base de dados.");
		}else {
			ConfigSystem config = repo.findById(1).get();
			configNonADM.setPreco_alcool(config.getPreco_alcool());
			configNonADM.setPreco_coroa(config.getPreco_coroa());
			configNonADM.setPreco_cruz(config.getPreco_cruz());
			configNonADM.setPreco_persistente(config.getPreco_persistente());
			configNonADM.setPreco_pontual(config.getPreco_pontual());
			configNonADM.setPreco_superLimpo(config.getPreco_superLimpo());
			
			return configNonADM;
		}
	}
	
	public ConfigSystemNonAdminBadgeDTO getConfigNonAdminBadge() throws Exception {
		
		ConfigSystemNonAdminBadgeDTO configNonADM = new ConfigSystemNonAdminBadgeDTO();
		
		if(repo.count() == 0) {
			throw new IllegalAccessException("Não existe configurações na base de dados.");
		}else {
			ConfigSystem config = repo.findById(1).get();
			
			configNonADM.setNivelBadge1(config.getNivelBadge1());
			configNonADM.setNivelBadge2(config.getNivelBadge2());
			configNonADM.setNivelBadge3(config.getNivelBadge3());
			configNonADM.setNivelBadge4(config.getNivelBadge4());
			
			return configNonADM;
		}
	}

	public void testaConsulta(int nivel) {
		repo.updateReprocessarNivel(nivel);
	}
}
