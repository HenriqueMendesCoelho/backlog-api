package com.fiapster.backlog.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiapster.backlog.dto.ConfigSystemNonAdminBadgeDTO;
import com.fiapster.backlog.dto.ConfigSystemNonAdminStoreDTO;
import com.fiapster.backlog.models.ConfigSystem;
import com.fiapster.backlog.services.ConfigSystemService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/config")
public class ConfigSystemController {
	
	@Autowired
	private ConfigSystemService service;
	
	
	@PostMapping("/adm")
	@PreAuthorize("hasRole('ADMIN')")
	public String createOrUpdateConfig(@RequestBody ConfigSystem config, HttpServletRequest request) throws Exception {	
		service.createOrUpdateConfig(config, request);
		return "Sucesso.";
	}
	
	@GetMapping("/adm")
	@PreAuthorize("hasRole('ADMIN')")
	public ConfigSystem getConfig(HttpServletRequest request) throws IllegalAccessException {	
		return service.getConfig(request);
	}
	
	@GetMapping("/store")
	public ConfigSystemNonAdminStoreDTO getConfigNonAdminStore() throws Exception {	
		return service.getConfigNonAdminStore();
	}
	
	@GetMapping("/badge")
	public ConfigSystemNonAdminBadgeDTO getConfigNonAdminBadge() throws Exception {	
		return service.getConfigNonAdminBadge();
	}
	
}
