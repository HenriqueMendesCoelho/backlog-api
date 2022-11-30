package com.fiapster.backlog.security;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;



import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiapster.backlog.dto.SysUserCredenciaisDTO;
import com.fiapster.backlog.methods.ObterDataEHora;
import com.fiapster.backlog.services.AuthService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	private AuthenticationManager authenticationManager;
    
    private JWTUtil jwtUtil;
    
    private ObterDataEHora hora = new ObterDataEHora();
    
    private AuthService service;
    

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, ApplicationContext ctx) {
    	setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.service= ctx.getBean(AuthService.class);
    }
	
	@Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
		
		try {
			SysUserCredenciaisDTO creds = new ObjectMapper()
	                .readValue(req.getInputStream(), SysUserCredenciaisDTO.class);
	
	        UsernamePasswordAuthenticationToken authToken = 
	        		new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getSenha(), new ArrayList<>());
	        
	        Authentication auth = authenticationManager.authenticate(authToken);
	        return auth;
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
		
		String username = ((SysUserSS) auth.getPrincipal()).getUsername();
		boolean tmp1 = ((SysUserSS) auth.getPrincipal()).isStemp();
        String token = jwtUtil.generateToken(username);
        String tmp = Boolean.toString(tmp1);
        
        service.sucessLogin(username);
        if(tmp1) {
        	service.credTempUse(username);
        }
        
        res.addHeader("Access-Control-Expose-Headers", "Authorization, tmp");
        res.addHeader("Authorization", "Bearer " + token);
        res.addHeader("tmp", tmp);
        res.addDateHeader("Expires", hora.obterDataAgora() + jwtUtil.expiration);
	}
	
	
	private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {
		@Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
                throws IOException, ServletException {
            response.setStatus(401);
            response.setContentType("application/json");
            
	        if(exception.getMessage().contains("expired")) {
	        	response.getWriter().append(jsonExpired());
	        }else if(exception.getMessage().contains("Bad credentials")){
	        	response.getWriter().append(jsonNormal());
	        }else {
	        	response.getWriter().append(jsonLocked());
	        }

        }
        
        private String jsonNormal() {
            long date = new Date().getTime();
            return "{\"timestamp\": " + date + ", "
                + "\"status\": 401, "
                + "\"error\": \"Não autorizado\", "
                + "\"message\": \"Email ou senha inválidos.\", "
                + "\"path\": \"/login\"}";
        }
        
        private String jsonExpired() {
            long date = new Date().getTime();
            return "{\"timestamp\": " + date + ", "
                + "\"status\": 401, "
                + "\"error\": \"Não autorizado\", "
                + "\"message\": \"Senha temporária expirada.\", "
                + "\"path\": \"/login\"}";
        }
        
        private String jsonLocked() {
            long date = new Date().getTime();
            return "{\"timestamp\": " + date + ", "
                + "\"status\": 401, "
                + "\"error\": \"Não autorizado\", "
                + "\"message\": \"Conta bloqueada. Contate o administrador.\", "
                + "\"path\": \"/login\"}";
        }
    }

}
