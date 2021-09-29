package com.fiapster.backlog.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import com.fiapster.backlog.exceptions.ApiBadRequestException;
import com.fiapster.backlog.methods.ObterDataEHora;
import com.fiapster.backlog.models.Cliente;
import com.fiapster.backlog.util.DbUtilCurso;


public class ClienteDao {

	private Connection connectionCliente;

	public ClienteDao() {
		connectionCliente = DbUtilCurso.getConnection();
	}

	public String CadastroCliente(Cliente user, HttpServletResponse response) throws ApiBadRequestException{
		try {
			ObterDataEHora data = new ObterDataEHora();

			if (user.getFirstName() == "" || user.getFirstName() == null) {
				throw new ApiBadRequestException("FirstName não pode ser nulo");
			}
			if (user.getLastName() == "" || user.getLastName() == null) {
				throw new ApiBadRequestException("LastName não pode ser nulo");
			}
			if (user.getEmail() == "" || user.getEmail() == null) {
				throw new ApiBadRequestException("Email não pode ser nulo");
			}
			if (user.getCpf() == "" || user.getCpf() == null) {
				throw new ApiBadRequestException("CPG não pode ser nulo");
			}
			if (user.getRg() == "" || user.getRg() == null) {
				throw new ApiBadRequestException("RG não pode ser nulo");
			}

			PreparedStatement preparedStatement = connectionCliente.prepareStatement(
					"insert into clientes(firstname,lastname,email,cpf,rg,data_criacao) values (?,?,?,?,?,?)");

			preparedStatement.setString(1, user.getFirstName());
			preparedStatement.setString(2, user.getLastName());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getCpf());
			preparedStatement.setString(5, user.getRg());
			preparedStatement.setString(6, data.obterDataEHora());
			preparedStatement.executeUpdate();
			return "Usuário cadastrado!";
		} catch (SQLException e) {
			throw new ApiBadRequestException(e.getMessage());
		}
	}

	public Cliente getUserByCPF(String cpf) throws Exception {
		try {
			Cliente user = new Cliente();
			if (cpf == null || cpf == "") {
				throw new ApiBadRequestException("Email não pode ser nulo");
			}

			PreparedStatement preparedStatement = connectionCliente
					.prepareStatement("select * from clientes where cpf=?");
			preparedStatement.setString(1, cpf);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				user.setId(rs.getInt("id"));
				user.setFirstName(rs.getString("firstname"));
				user.setLastName(rs.getString("lastname"));
				user.setEmail(rs.getString("email"));
				user.setCpf(rs.getString("cpf"));
				user.setRg(rs.getString("rg"));
				user.setData_criacao(rs.getString("data_criacao"));
			}

			if (user.getFirstName() == null || user.getFirstName() == "") {
				throw new ApiBadRequestException("Usuário não encontrado.");
			}
			return user;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e);

		}
	}
	
	public void deleteUser(String email) throws Exception {
        try {
        	
        	if(email == "" || email == null) {
        		throw new ApiBadRequestException("E-mail não pode estar vazio.");
        	}
        	
        	Cliente user = new Cliente();
        	
        	PreparedStatement preparedStatementCli = connectionCliente
					.prepareStatement("select * from clientes where email=?");
			preparedStatementCli.setString(1, email);
			ResultSet rs = preparedStatementCli.executeQuery();
			
			if (rs.next()) {
				user.setId(rs.getInt("id"));
				user.setFirstName(rs.getString("firstname"));
				user.setLastName(rs.getString("lastname"));
				user.setEmail(rs.getString("email"));
				user.setCpf(rs.getString("cpf"));
				user.setRg(rs.getString("rg"));
				user.setData_criacao(rs.getString("data_criacao"));
			}else {
				throw new ApiBadRequestException("Usuário não encontrado.");
			}
        	
        	
            PreparedStatement preparedStatement = connectionCliente
                    .prepareStatement("delete from clientes where email=?");
            // Parameters start with 1
            preparedStatement.setString(1, email);
            preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
        	throw new Exception(e);
        }
    }

}
