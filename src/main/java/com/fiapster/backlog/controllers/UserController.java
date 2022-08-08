package com.fiapster.backlog.controllers;



/**
 * 
 * @author Henrique
 *
 */

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fiapster.backlog.dto.EmailDTO;
import com.fiapster.backlog.dto.SysUserAddPontosECreditosDTO;
import com.fiapster.backlog.dto.SysUserAlteraSenhaDTO;
import com.fiapster.backlog.dto.SysUserCredenciaisDTO;
import com.fiapster.backlog.dto.SysUserPerfilDTO;
import com.fiapster.backlog.dto.SysUserRankDTO;
import com.fiapster.backlog.dto.SysUserRemoveItemDTO;
import com.fiapster.backlog.dto.SysUserUpdateDadosUserDTO;
import com.fiapster.backlog.dto.ValidaLoginDTO;
import com.fiapster.backlog.exceptions.ApiNotAcceptableException;
import com.fiapster.backlog.models.SysUser;
import com.fiapster.backlog.services.SysUserService;


@RestController
@RequestMapping("/api/user")
public class UserController {
	SysUser user = new SysUser();
	
	@Autowired
	private SysUserService service;
	
	// Busca usuario pelo email
	@GetMapping("/busca")
	public SysUser buscaUsuarioPorEmail(HttpServletRequest request) throws IllegalAccessException, ApiNotAcceptableException {
		return service.buscaUser(request);
	}
	
	// Lista todos os usuarios rankeados pelos pontos
	@GetMapping("/rank")
	public List<SysUserRankDTO> buscaListaUsuario() {
		return (List<SysUserRankDTO>) service.getRank();
	}
	
	// Deleta o usuario
	@DeleteMapping("/adm/del")
	@PreAuthorize("hasRole('ADMIN')")
	public String deletarUser(@RequestBody SysUserCredenciaisDTO user, HttpServletRequest request) throws IllegalAccessException, ApiNotAcceptableException {
		
		return service.deletUser(user.getEmail(), request);
	}
	
	// Soma uma quantidade de pontos para o usuario
	@PatchMapping("/p")
	public String updatePontos(HttpServletRequest request) throws IllegalAccessException, ApiNotAcceptableException {
		service.updatePontos(request);
		return "Pontos atualizados.";
	}
	
	// Soma uma quantidade de pontos para o usuario
	@PatchMapping("/update")
	public void updateDadosUser(@RequestBody SysUserUpdateDadosUserDTO user, HttpServletRequest request) throws IllegalAccessException, ApiNotAcceptableException {
		service.updateDadosUser(user, request);
	}
	
	// Busca de usuário por id com JPA
	@GetMapping("/busca/v3")
	public ResponseEntity<?> buscaUserId(@RequestParam Integer id) {
		SysUser user = service.buscarUserId(id);
		return ResponseEntity.ok().body(user);
	}
	
	//Cadastra o usuário JPA - faz o relacionamento automatico com tabela relacional
	@PostMapping("/cadastro")
	public String cadastroUsuarioJpa(@RequestBody SysUser user) throws IllegalAccessException{
		service.cadastroUser(user);
		return "Usuário Cadastrado!";
	}
	
	//Altera senha do usuário com JPA
	@PatchMapping("/ns")
	public String alteraSenha(@RequestBody SysUserAlteraSenhaDTO user, HttpServletRequest request) throws IllegalAccessException, ApiNotAcceptableException {
		service.alteraSenha(request, user.getSenhaAtual(), user.getNovaSenha());
		return "Senha alterada.";
	}
	
	//Adiciona direito de acesso admin para usuário
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/adm/add")
	public String addAdmin(@RequestBody SysUserPerfilDTO user, HttpServletRequest request) throws IllegalAccessException, ApiNotAcceptableException {
		service.addAdmin(user.getEmail(), request);
		return "Direito de acesso adicionado ao usuário.";
	}
	
	//Remove direito de acesso admin para usuário
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/adm/rmv")
	public String removeAdmin(@RequestBody SysUserPerfilDTO user, HttpServletRequest request) throws IllegalAccessException, ApiNotAcceptableException {
		service.removeAdmin(user.getEmail(), request);
		return "Direito de acesso removido do usuário.";
	}
	
	@PatchMapping("/item/buy")
	public String addItem(@RequestBody String item, HttpServletRequest request) throws IllegalAccessException, ApiNotAcceptableException {
		service.addItem(item, request);
		return "Item adicionado.";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/adm/item/rmv")
	public String rmvOuAddItem(@RequestBody SysUserRemoveItemDTO user, HttpServletRequest request) throws IllegalAccessException, ApiNotAcceptableException {
		return service.rmvOuAddItem(user.getEmail(), user.getItem(), request);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/adm/addcp")
	public String addPontosECreditos(@RequestBody SysUserAddPontosECreditosDTO user, HttpServletRequest request) throws IllegalAccessException, ApiNotAcceptableException {
		return service.addPontosECreditosADM(user.getEmail(), user.getPontos(), user.getCreditos(), request);
	}
	
	@PostMapping("/valid")
	public void validaLogin(@RequestBody ValidaLoginDTO user, HttpServletRequest request) throws IllegalAccessException, ApiNotAcceptableException {
		service.valid(user.getSenha(), request);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/adm/lock")
	public String bloqueiaOuDesbloqueiaContaADM(@RequestBody EmailDTO email, HttpServletRequest request) throws IllegalAccessException, ApiNotAcceptableException {	
		return service.bloqueiaOuDesbloqueiaContaADM(email, request);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/adm/srank")
	public String apareNoRank(@RequestBody EmailDTO email, HttpServletRequest request) throws IllegalAccessException, ApiNotAcceptableException {
		return service.escondeOuMostraNoRank(email.getEmail(), request);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/adm/busca")
	public SysUser buscaPorEmail(@RequestBody EmailDTO email, HttpServletRequest request) throws IllegalAccessException, ApiNotAcceptableException {
		return service.buscaPorEmailADM(email.getEmail(), request);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/rank/adm")
	public List<SysUser> buscaListaUsuarioADM(HttpServletRequest request) throws IllegalAccessException, ApiNotAcceptableException {
		return service.getListaUSerADM(request);
	}
}
