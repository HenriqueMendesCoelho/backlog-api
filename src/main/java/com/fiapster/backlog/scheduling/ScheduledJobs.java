package com.fiapster.backlog.scheduling;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import com.fiapster.backlog.models.ConfigSystem;
import com.fiapster.backlog.models.SysUser;
import com.fiapster.backlog.repositories.ConfigSystemRepository;
import com.fiapster.backlog.services.EmailService;
import com.fiapster.backlog.services.SysUserService;

@SpringBootApplication
public class ScheduledJobs {
	
	@Autowired
	SysUserService serviceU;
	
	@Autowired
	ConfigSystemRepository repoConfig;
	
	@Autowired
	EmailService emailService;
	
	@Async
	@Scheduled(fixedDelayString = "PT5M")
	void enviaEmailBoasVindas() {
		ArrayList<SysUser> users = (ArrayList<SysUser>) serviceU.getListaUSer();
		
		for (int i = 0; i < users.size(); i++) {
			if(users.get(i) != null) {
				if(!users.get(i).getEmailS().isEmailBemVindo()) {
					emailService.sendCreationAccountConfirmationHTMLEmail(users.get(i));
				}
			}
			
		}
	}
	
	@Async
	@Scheduled(cron = "0 0 17 * * *")
	void enviaEmailNovaConfig() {
		ArrayList<SysUser> users = (ArrayList<SysUser>) serviceU.getListaUSer();
		ConfigSystem config = repoConfig.findById(1).get();
		
		if(config != null) {
			for (int i = 0; i < users.size(); i++) {
				if(users.get(i) != null) {
					if(!users.get(i).getEmailS().isEmailConfig()) {
						emailService.sendNovaConfigEmail(users.get(i), config);
					}
				}
				
			}	
		}
	}
}
