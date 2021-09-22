package com.fiapster.backlog.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.fiapster.backlog.models.ConfigSystem;
import com.fiapster.backlog.models.SysUser;

public interface EmailService {
	
	void sendCreationAccountConfirmationEmail(SysUser user);
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendCreationAccountConfirmationHTMLEmail(SysUser user);
	
	void sendHtmlEmail(MimeMessage msg);
	
	void sendNovaSenhaEmail(SysUser user, String novaSenha);
	
	void sendNovaConfigEmail(SysUser user, ConfigSystem config);
	
}
