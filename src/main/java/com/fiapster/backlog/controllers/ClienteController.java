package com.fiapster.backlog.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
		return dao.getUserByCPF(user.getCpf());
	}
	@PostMapping("/cadastro")
	@ResponseBody
	public String cadastroUsuario(@RequestBody Cliente cliente, HttpServletResponse response) throws Exception{
		
		cliente.setFirstName(cliente.getFirstName());
		cliente.setLastName(cliente.getLastName());
		cliente.setEmail(cliente.getEmail());
		cliente.setCpf(cliente.getCpf());
		cliente.setRg(cliente.getRg());
		cliente.setId(0);
		
		System.out.println("cliente:"+cliente.getFirstName());
		
		return dao.CadastroCliente(cliente, response);
	}
	
	@GetMapping ("/busca/v1")
	public Cliente buscaUsuarioPorCPF(
			@RequestParam (name = "c") String cpf) throws Exception {
		return dao.getUserByCPF(cpf);
	}
}
