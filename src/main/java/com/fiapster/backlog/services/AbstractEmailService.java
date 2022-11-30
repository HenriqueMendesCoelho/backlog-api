package com.fiapster.backlog.services;

import java.util.Date;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.fiapster.backlog.models.ConfigSystem;
import com.fiapster.backlog.models.SysUser;
import com.fiapster.backlog.repositories.SysUserRepository;

public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;
	
	@Autowired
	TemplateEngine engine;
	
	@Autowired
	JavaMailSender javaMailSender;
	
	@Autowired
	private SysUserRepository repo;
	
	
	@Override
	public void sendCreationAccountConfirmationEmail(SysUser user) {
		SimpleMailMessage sm = emailSimplesDeConfirmaçãoDeCriacaoConta(user);
		sendEmail(sm);
	}
	
	protected SimpleMailMessage emailSimplesDeConfirmaçãoDeCriacaoConta(SysUser user) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(user.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Olá,"+user.getNome()+" bem Vindo ao Fiapster-Backlog");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Bem vindo ao sistema Backlog, onde você poderá acompanhar o seu avanço diário no game!");
		return sm;
	}
	
	//-------------------------------------------------------------------------------------
	
	//Envio do email de confirmação de criação de conta
	@Override
	public void sendCreationAccountConfirmationHTMLEmail(SysUser user) {
		
		try {
			MimeMessage mm;
			mm = emailHTMLDeConfirmaçãoDeCriacaoConta(user);
			sendHtmlEmail(mm);
			user.getEmailS().setEmailBemVindo(true);
			repo.saveAndFlush(user);
		} catch (MessagingException e) {
			System.err.println("Erro ao enviar e-mail de boas vindas para o user: " + user.getEmail());
			user.getEmailS().setEmailBemVindo(false);
			repo.saveAndFlush(user);
		}
		
	}
	
	//Arquivo do email de criação de conta/Setar variaveis do email de confirmação de criação de conta
	protected String htmlFromCreationAccountConfirmation(SysUser user) {
		Context context = new Context();
		context.setVariable("usuario", user);
		return engine.process("email/criaContaEmail", context);
	}
	
	//E-mail de confirmação de criação de conta
	protected MimeMessage emailHTMLDeConfirmaçãoDeCriacaoConta(SysUser user) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(user.getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Olá, "+user.getNome()+" bem-vindo ao Backlog");
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromCreationAccountConfirmation(user), true);
		return mimeMessage;
	}
	
	//-------------------------------------------------------------------------------------
	
	//Email para recuperação de senha
	@Override
	public void sendNovaSenhaEmail(SysUser user, String novaSenha) {
		try {
			MimeMessage mm;
			mm = prepareNovaSenhaEmail(user, novaSenha);
			sendHtmlEmail(mm);
		} catch (MessagingException e) {
			System.err.println(e);
		}
	}
	
	protected String htmlNovaSenhaEmail(SysUser user, String novaSenha) {
		Context context = new Context();
		context.setVariable("usuario", user);
		context.setVariable("nova_senha", novaSenha);
		return engine.process("email/novaSenhaEmail", context);
	}

	protected MimeMessage prepareNovaSenhaEmail(SysUser user, String novaSenha) throws MessagingException{
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(user.getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Backlog - Recuperação de senha");
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlNovaSenhaEmail(user, novaSenha), true);
		return mimeMessage;
	}
	
	//-------------------------------------------------------------------------------------
	//Email de quando uma nova configuração do sistema é enviada para a tabela
	@Override
	public void sendNovaConfigEmail(SysUser user, ConfigSystem config) {
		try {
			MimeMessage mm;
			mm = prepareNovaConfigEmail(user, config);
			sendHtmlEmail(mm);
			user.getEmailS().setEmailConfig(true);;
			repo.saveAndFlush(user);
		} catch (MessagingException e) {
			System.err.println(e);
			user.getEmailS().setEmailConfig(false);;
			repo.saveAndFlush(user);
		}
	}
	
	protected String htmlNovaConfig(SysUser user, ConfigSystem config) {
		Context context = new Context();
		context.setVariable("usuario", user);
		context.setVariable("config", config);
		return engine.process("email/novaConfigEmail", context);
	}

	protected MimeMessage prepareNovaConfigEmail(SysUser user, ConfigSystem config) throws MessagingException{
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(user.getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Backlog - As regras do jogo mudaram!");
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlNovaConfig(user, config), true);
		return mimeMessage;
	}
	
}
