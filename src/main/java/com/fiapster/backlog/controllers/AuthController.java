package com.fiapster.backlog.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiapster.backlog.dto.EmailDTO;
import com.fiapster.backlog.methods.ObterDataEHora;
import com.fiapster.backlog.security.JWTUtil;
import com.fiapster.backlog.security.SysUserSS;
import com.fiapster.backlog.services.AuthService;
import com.fiapster.backlog.services.SysUserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService service;
	
	private ObterDataEHora hora = new ObterDataEHora();
	
	@PostMapping("/refresh_token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		SysUserSS user = SysUserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
		response.addDateHeader("Expires", hora.obterDataAgora() + jwtUtil.expiration);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/forgot")
	public String geraNovaSenha(@RequestBody EmailDTO email) throws Exception{
		service.geraNovaSenha(email.getEmail());
		return "E-mail enviado.";
	}

}
