package com.fiapster.backlog.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fiapster.backlog.dao.ClienteDao;
import com.fiapster.backlog.models.Cliente;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping ("/api/cliente/curso")
public class ClienteController {
	ClienteDao dao = new ClienteDao();
	Cliente user = new Cliente(0, null, null, null, null, null, null);
	
	@DeleteMapping ("/del")
	public void deletarUser(@RequestParam String email) throws Exception {
		dao.deleteUser(email);
	}
	
	@GetMapping ("/busca")
	public Cliente buscaUsuarioPorEmail(@RequestBody Cliente user) throws Exception {
		System.out.println("CPF: "+user.getCpf());
		return dao.getUserByCPF(user.getCpf());
	}
	
	@PostMapping("/cadastro")
	public String cadastroUsuario(@RequestBody Cliente user) throws Exception{
		return dao.CadastroCliente(user);
	}
	
	@GetMapping ("/busca/v1")
	public Cliente buscaUsuarioPorCPF(
			@RequestParam (name = "c") String cpf) throws Exception {
		return dao.getUserByCPF(cpf);
	}
}
