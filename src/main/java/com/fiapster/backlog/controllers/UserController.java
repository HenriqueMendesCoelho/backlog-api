package com.fiapster.backlog.controllers;


/**
 * 
 * @author Henrique
 *
 */

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fiapster.backlog.dao.SysUserDao;
import com.fiapster.backlog.dto.EmailDTO;
import com.fiapster.backlog.dto.SysUserAddPontosECreditosDTO;
import com.fiapster.backlog.dto.SysUserAlteraSenhaDTO;
import com.fiapster.backlog.dto.SysUserCredenciaisDTO;
import com.fiapster.backlog.dto.SysUserPerfilDTO;
import com.fiapster.backlog.dto.SysUserRankDTO;
import com.fiapster.backlog.dto.SysUserRemoveItemDTO;
import com.fiapster.backlog.dto.SysUserUpdateDadosUserDTO;
import com.fiapster.backlog.dto.ValidaLoginDTO;
import com.fiapster.backlog.models.SysUser;
import com.fiapster.backlog.services.SysUserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {
	SysUserDao dao = new SysUserDao();
	SysUser user = new SysUser();
	
	@Autowired
	private SysUserService service;
	
	// Busca usuario pelo email
	@GetMapping("/busca")
	public SysUser buscaUsuarioPorEmail(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException {
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
	public String deletarUser(@RequestBody SysUserCredenciaisDTO user, HttpServletResponse response) throws IllegalAccessException {
		
		return service.deletUser(user.getEmail(), response);
	}
	
	// Soma uma quantidade de pontos para o usuario
	@PatchMapping("/p")
	public String updatePontos(HttpServletRequest request) throws IllegalAccessException {
		service.updatePontos(request);
		return "Pontos atualizados.";
	}
	
	// Soma uma quantidade de pontos para o usuario
	@PatchMapping("/update")
	public void updateDadosUser(@RequestBody SysUserUpdateDadosUserDTO user, HttpServletRequest request) {
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
	public String alteraSenha(@RequestBody SysUserAlteraSenhaDTO user, HttpServletRequest request) throws IllegalAccessException {
		service.alteraSenha(request, user.getSenhaAtual(), user.getNovaSenha());
		return "Senha alterada.";
	}
	
	//Adiciona direito de acesso admin para usuário
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/adm/add")
	public String addAdmin(@RequestBody SysUserPerfilDTO user) throws IllegalAccessException {
		service.addAdmin(user.getEmail());
		return "Direito de acesso adicionado ao usuário.";
	}
	
	//Remove direito de acesso admin para usuário
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/adm/rmv")
	public String removeAdmin(@RequestBody SysUserPerfilDTO user, HttpServletResponse response) throws IllegalAccessException {
		service.removeAdmin(user.getEmail());
		return "Direito de acesso removido do usuário.";
	}
	
	@PatchMapping("/item/buy")
	public String addItem(@RequestBody String item, HttpServletRequest request) throws IllegalAccessException {
		service.addItem(item, request);
		return "Item adicionado.";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/adm/item/rmv")
	public String rmvOuAddItem(@RequestBody SysUserRemoveItemDTO user) throws IllegalAccessException {
		return service.rmvOuAddItem(user.getEmail(), user.getItem());
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/adm/addcp")
	public String addPontosECreditos(@RequestBody SysUserAddPontosECreditosDTO user) throws IllegalAccessException {
		return service.addPontosECreditosADM(user.getEmail(), user.getPontos(), user.getCreditos());
	}
	
	@PostMapping("/valid")
	public void validaLogin(@RequestBody ValidaLoginDTO user, HttpServletRequest request) throws IllegalAccessException {
		service.valid(user.getSenha(), request);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/adm/lock")
	public String bloqueiaOuDesbloqueiaContaADM(@RequestBody EmailDTO email) throws IllegalAccessException {
		
		return service.bloqueiaOuDesbloqueiaContaADM(email);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping("/adm/srank")
	public String apareNoRank(@RequestBody EmailDTO email) throws IllegalAccessException {
		return service.escondeOuMostraNoRank(email.getEmail());
	}
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/adm/busca")
	public SysUser buscaPorEmail(@RequestBody EmailDTO email) throws IllegalAccessException {
		return service.buscaPorEmailADM(email.getEmail());
	}
}