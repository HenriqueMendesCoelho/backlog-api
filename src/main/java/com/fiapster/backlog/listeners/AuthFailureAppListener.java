package com.fiapster.backlog.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import com.fiapster.backlog.services.AuthService;

@Component
public class AuthFailureAppListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
	
	@Autowired
	private AuthService service;
	
    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        Object userName = event.getAuthentication().getPrincipal();
        //Object credentials = event.getAuthentication().getCredentials();
        if(userName != null) {
        	String user = (String) userName;
            service.cont_FLogin(user);
        }
    }
}
