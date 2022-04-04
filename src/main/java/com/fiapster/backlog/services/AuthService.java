package com.fiapster.backlog.services;

import java.util.Random;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fiapster.backlog.models.SysUser;
import com.fiapster.backlog.repositories.SysUserRepository;

@Service
public class AuthService {
	
	@Autowired
	SysUserRepository repo;
	
	@Autowired
	BCryptPasswordEncoder pe;
	
	@Autowired
	EmailService emailService;
	
	private Random rand = new Random();
	
	public void geraNovaSenha(String email) throws Exception {
		
		if(email == null || email == "") {
			throw new IllegalArgumentException("Email não pode ser nulo.");
		}
		
		SysUser user = repo.findByEmail(email);
		if(user != null) {
			if(user.getQtd_FLogin() < 10) {
				String novaSenha = novaSenha();
				user.setSenha(pe.encode(novaSenha));
				
				user.setStemp(true);
				user.setCredTmpUses(0);
				repo.save(user);
				
				emailService.sendNovaSenhaEmail(user, novaSenha);
			}else {
				int opt = rand.nextInt(4000);
				Thread.sleep(10000 + opt);
			}
			
		}else {
			int opt = rand.nextInt(4000);
			Thread.sleep(10000 + opt);
		}
		
	}

	private String novaSenha() {
		char[] vet = new char[12];
		
		for (int i = 0; i < vet.length; i++) {
			vet[i] = randomChar();
		}
		
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(4); // Gera um número de 0 a 4 para randomizar a decisão de caracteres aleatórios.
		
		if(opt == 0) { //Gerar um dígito
			return (char) (rand.nextInt(10) + 48);
		}else if(opt == 1) { //Gera letra Maiuscula
			return (char) (rand.nextInt(26) + 65);
		} else if(opt == 2) { //Gera letra minuscula
			return (char) (rand.nextInt(26) + 97);
		}else { //Gera caractere especial
			int opt1 = rand.nextInt(2);
			if(opt1 == 0) { // Caracteres especiais
				return (char) (rand.nextInt(3) + 35);
			}else {
				return (char) (rand.nextInt(7) + 58);
			}
		}
	}
	
	public void credTempUse(String email) {
		SysUser user = repo.findByEmail(email);
		if(user != null) {
			user.setCredTmpUses(user.getCredTmpUses() + 1);
			repo.saveAndFlush(user);
		}
		
	}
	
	public void cont_FLogin(String email) {
		SysUser user = repo.findByEmail(email);
		if(user != null) {
			user.setQtd_FLogin(user.getQtd_FLogin() + 1);
			repo.saveAndFlush(user);
		}
	}
	
	public void sucessLogin(String email) {
		SysUser user = repo.findByEmail(email);
		if(user != null) {
			user.setQtd_FLogin(0);
			repo.saveAndFlush(user);
		}
	}
}
