package com.fiapster.backlog.dao;

/**
 * @author Henrique
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import com.fiapster.backlog.methods.ObterDataEHora;
import com.fiapster.backlog.models.SysUser;
import com.fiapster.backlog.util.DbUtilBacklog;
import com.sun.xml.txw2.IllegalAnnotationException;


public class SysUserDao{
	
private Connection connection;
	
	public SysUserDao() {
        connection = DbUtilBacklog.getConnection();
    }
	
	//Cadastra usuário na tabela
	public String addUser(SysUser user) {
        try {
        	ObterDataEHora data = new ObterDataEHora();
        	
        	if(user.getNome() == "" || user.getNome() == null) {
        		//throw new NoSuchFieldException("Usuário não pode ser nulo");
        		return "Usuário não pode ser nulo";
        	}
        	if(user.getEmail() == "" || user.getEmail() == null) {
        		return "Email não pode ser nulo";
        	}
        	if(user.getCargo() == "" || user.getCargo() == null) {
        		return "Cargo não pode ser nulo";
        	}
        	if(user.getSenha() == "" || user.getSenha() == null) {
        		return "Senha não pode ser nulo";
        	}
        	
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into sysusers(nome,cargo,email,pontos,nivel,senha,data_criacao,data_exec) values (?,?,?,?,?,PASSWORD(?),?,?)");
            // Parameters start with 1
            preparedStatement.setString(1, user.getNome());
            preparedStatement.setString(2, user.getCargo());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setInt(4, user.getPontos());
            preparedStatement.setInt(5, user.getNivel());
            preparedStatement.setString(6, user.getSenha());
            preparedStatement.setString(7, data.obterDataEHora());
            preparedStatement.setLong(8, user.getData_exec());
            preparedStatement.executeUpdate();
            return "Sucesso! Usuário Cadastrado.";
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
            
        }
    }
	
	//Deleta o usuário
	public String deleteUser(String email) {
        try {
        	
        	if(email == "" || email == null) {
        		return "Email nulo, nenhuma linha afetada.";
        	}
        	
        	int antes = getAllUsers().size();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from sysusers where email=?");
            // Parameters start with 1
            preparedStatement.setString(1, email);
            preparedStatement.executeUpdate();
            int depois = getAllUsers().size();
            
            if(antes == depois) {
            	return "Nenhuma linha afetada.";
            }else {
            	return "Usuário removido.";
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
	
	//Atualiza o usuário na tabela
	public void updateUserAdmin(SysUser user) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update sysusers set nome=?,email=?, cargo=?, pontos=?, nivel=?, senha=?" +
                            "where email=?");
            // Parameters start with 1
            preparedStatement.setString(1, user.getNome());
            preparedStatement.setString(2, user.getCargo());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setInt(4, user.getPontos());
            preparedStatement.setInt(5, user.getNivel());
            preparedStatement.setString(6, user.getSenha());
            preparedStatement.setString(7, user.getEmail());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	public String updateUser(SysUser user, String emailAtual) {
        try {
        	
        	if(emailAtual == "" || emailAtual == null) {
        		return "Email atual não pode estar vazio.";
        	}
        	
        	if(getUserByEmail(emailAtual).getNome() == null || getUserByEmail(emailAtual).getNome() == "") {
        		return "Email atual informado não existe na base.";
        	}
        	
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update sysusers set nome=?,email=?, cargo=?" +
                            "where email=?");
           
            if(user.getNome() == "" || user.getNome() == null) {
            	user.setNome(getUserByEmail(emailAtual).getNome());
            	System.out.println("parametro nome em branco, nome não atualizado");
            }
            
            if(user.getEmail() == "" || user.getEmail() == null) {
            	user.setEmail(getUserByEmail(emailAtual).getEmail());
            	System.out.println("parametro email em branco, email não atualizado");
            }
            
            if(user.getCargo() == "" || user.getCargo() == null) {
            	user.setCargo(getUserByEmail(emailAtual).getCargo());
            	System.out.println("parametro cargo em branco, cargo não atualizado");
            }
            // Parameters start with 1
            preparedStatement.setString(1, user.getNome());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getCargo());
            preparedStatement.setString(4, emailAtual);
            preparedStatement.executeUpdate();
            return "Dados atualizados.";

        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
	
	
	//Busca todos os usuários rankeados por pontos
	public List<SysUser> getAllUsers() {
        List<SysUser> listaDeUsuario = new ArrayList<SysUser>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM sysusers ORDER BY pontos DESC");
            while (rs.next()) {
            	SysUser user = new SysUser();
                //user.setUserid(rs.getInt("userid"));
            	user.setId(0);
                user.setNome(rs.getString("nome"));
                user.setCargo(rs.getString("cargo"));
                user.setPontos(rs.getInt("pontos"));
                user.setNivel(rs.getInt("nivel"));
                user.setSenha("******");
                //user.setSenha(rs.getString("senha"));
                user.setEmail(rs.getString("email"));
                user.setData_criacao(rs.getString("data_criacao"));
                listaDeUsuario.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getErrorCode();
        }

        return listaDeUsuario;
    }
	
	//Busca na tabela o top 3 dos usuários rankeados por pontos
	public List<SysUser> getTop3() {
        List<SysUser> listaDeUsuario = new ArrayList<SysUser>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM sysusers ORDER BY pontos DESC LIMIT 3");
            while (rs.next()) {
            	SysUser user = new SysUser();
                //user.setUserid(rs.getInt("userid"));
            	user.setId(0);
                user.setNome(rs.getString("nome"));
                user.setCargo(rs.getString("cargo"));
                user.setPontos(rs.getInt("pontos"));
                user.setNivel(rs.getInt("nivel"));
                user.setSenha("******");
                user.setData_exec(0);
                user.setData_criacao(rs.getString("data_criacao"));
                //user.setSenha(rs.getString("senha"));
                user.setEmail(rs.getString("email"));
                listaDeUsuario.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getErrorCode();
        }

        return listaDeUsuario;
    }
	
	//Busca usuário na tabela por meio do email
	public SysUser getUserByEmail(String email){
		SysUser user = new SysUser();
        try {
        	
        	if(email == null || email == "") {
        		throw new IllegalAnnotationException("Email não pode ser nulo");
        	}
        	
            PreparedStatement preparedStatement = connection.prepareStatement("select nome,email,cargo,pontos,nivel,data_criacao from sysusers where email=?");
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                user.setId(0);
                user.setNome(rs.getString("nome"));
                user.setEmail(rs.getString("email"));
                user.setCargo(rs.getString("cargo"));
                user.setPontos(rs.getInt("pontos"));
                user.setNivel(rs.getInt("nivel"));
                user.setSenha("******");
                user.setData_criacao(rs.getString("data_criacao"));
            }
            
            if(user.getNome() == null || user.getNome() == "" || user.getEmail() == null || user.getEmail() == "") {
            	user.setNome("Usuário não encontrado.");
        		user.setEmail("Usuário não encontrado.");
        		user.setCargo("Usuário não encontrado.");
        		user.setSenha("Usuário não encontrado.");
        		return user;
            }
            
        } catch (SQLException e) {
        	e.printStackTrace();
            
        }
        return user;
	}
	
	//Soma uma quantidade de pontos para o usuário
	public String updatePontosUser(String email) {
			SysUser user = new SysUser();
            try {
            	ObterDataEHora data = new ObterDataEHora();
            	
            	
            	
            	/*
            	if(p <= 0) {
            		return "Pontos negativos ou igual a 0";
            	}*/
            	
                PreparedStatement preparedStatement = connection.prepareStatement("select nome,pontos,data_exec from sysusers where email=?");
                preparedStatement.setString(1, email);
                ResultSet rs = preparedStatement.executeQuery();
                
                if (rs.next()) {
                    user.setNome(rs.getString("nome"));
                    user.setPontos(rs.getInt("pontos"));
                    user.setData_exec(rs.getLong("data_exec"));
                }
                double horaExecUser = data.obterIntevaloHoraEmMinutos(user.getData_exec());
                if(user.getNome() != null || user.getNome() != "") {
                	
                	if(user.getData_exec() == 0) {
                		user.setPontos(user.getPontos()+10);
                	}else {
                		if(horaExecUser > 720) {
                			user.setPontos(user.getPontos() + 10);
                		}else if(horaExecUser > 60 && horaExecUser <= 720) {
                			user.setPontos(user.getPontos() + 0);
                		}else if(horaExecUser > 35 && horaExecUser <= 60) {
                			user.setPontos(user.getPontos() + 5);
                		}else if(horaExecUser <= 35 && horaExecUser >= 2) {
                			user.setPontos(user.getPontos() + 10);
                		}else{
                			user.setPontos(user.getPontos() + 0);
                		}
                	}
                	
                	//user.setPontos(user.getPontos() + p);
                    PreparedStatement preparedStatement_att = connection.prepareStatement("update sysusers set pontos=?, data_exec=? where email=?");
                    preparedStatement_att.setInt(1, user.getPontos());
                    preparedStatement_att.setLong(2, data.obterDataAgora());
                    preparedStatement_att.setString(3, email);
                    preparedStatement_att.executeUpdate();
                    
                    return "Pontos atualizados";
                }
                
                return "Usuário não encontrado";
                
            } catch (SQLException e) {
            	e.printStackTrace();
            	return e.getMessage();
            }
    }
	
	//Altera a senha do usuário, mediante a passagem da senha antiga
	public String updateSenha(String email, String senhaAtual, String senhaNova) {
        try {
        	if(email =="" || email == null || senhaAtual == null || senhaAtual == "" || senhaNova == null || senhaNova =="") {
        		return"email, senha atual e nova senha não podem estar vazios.";
        	}
        	
        	if(senhaAtual.equals(senhaNova)) {
        		return "A senha atual não pode ser igual a nova senha.";
        	}
        	
        	if(existeUser(email, senhaAtual)) {
        		
        		PreparedStatement preparedStatement_att = connection.prepareStatement("update sysusers set senha=PASSWORD(?) where email=?");
                preparedStatement_att.setString(1, senhaNova);
                preparedStatement_att.setString(2, email);
                preparedStatement_att.executeUpdate();
                return "senha alterada.";
                
        	}else {
        		return "senha incorreta.";
        	}
        } catch (SQLException e) {
        	e.printStackTrace();
        	return e.getMessage();
        }
	}
        
	//Verifica se na tabela se o email e senha passados estão corretos
	public boolean existeUser(String email, String senha) {
		//SysUser user = new SysUser(0, null, 0, null, 0, null, null);
		boolean validador =false;
		try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from sysusers where (email=? AND senha=PASSWORD(?));");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, senha);
            ResultSet rs = preparedStatement.executeQuery();
            
            if (rs.next() != false) {
            	validador =true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return validador;
    }
	
	public SysUser getUserByEmailNomeCargo(String email){
		SysUser user = new SysUser();
        try {
        	
        	if(email == null || email == "") {
        		throw new IllegalAnnotationException("Email não pode ser nulo");
        	}
        	
            PreparedStatement preparedStatement = connection.prepareStatement("select nome,email,cargo,pontos,nivel,data_criacao from sysusers where email=?");
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                user.setId(0);
                user.setNome(rs.getString("nome"));
                user.setEmail(rs.getString("email"));
                user.setCargo(rs.getString("cargo"));
                user.setPontos(rs.getInt("pontos"));
                user.setNivel(rs.getInt("nivel"));
                user.setSenha("******");
                user.setData_criacao(rs.getString("data_criacao"));
            }
            
            if(user.getNome() == null || user.getNome() == "" || user.getEmail() == null || user.getEmail() == "") {
            	user.setNome("Usuário não encontrado.");
        		user.setEmail("Usuário não encontrado.");
        		user.setCargo("Usuário não encontrado.");
        		user.setSenha("Usuário não encontrado.");
        		return user;
            }
            
        } catch (SQLException e) {
        	e.printStackTrace();
            
        }
        return user;
	}

}