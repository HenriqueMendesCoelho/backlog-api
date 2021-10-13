package com.fiapster.backlog.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fiapster.backlog.dto.EmailDTO;
import com.fiapster.backlog.dto.SysUserRankDTO;
import com.fiapster.backlog.dto.SysUserUpdateDadosUserDTO;
import com.fiapster.backlog.enums.Item;
import com.fiapster.backlog.enums.Perfil;
import com.fiapster.backlog.exceptions.ApiNotAcceptableException;
import com.fiapster.backlog.methods.ObterDataEHora;
import com.fiapster.backlog.models.ConfigSystem;
import com.fiapster.backlog.models.EmailStatus;
import com.fiapster.backlog.models.SysUser;
import com.fiapster.backlog.repositories.ConfigSystemRepository;
import com.fiapster.backlog.repositories.SysUserRepository;
import com.fiapster.backlog.security.JWTUtil;
import com.fiapster.backlog.security.SysUserSS;

@Service
@CrossOrigin(origins = "*")
public class SysUserService {

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private SysUserRepository repo;

	@Autowired
	private ConfigSystemRepository repoConfig;

	@Autowired
	EmailService emailService;

	private ObterDataEHora d = new ObterDataEHora();

	@Autowired
	private JWTUtil jwtutil;
	
	@PostConstruct
	private void usuarioRoot() {
		System.out.println("Verificando se existem usuários na tabela...");
		if(repo.count() == 0) {
			SysUser root = new SysUser();
			root.setData_criacao(d.obterDataEHora());
			root.setNivel(1);
			root.setPontos(0);
			root.setId(0);
			root.setData_exec(0);
			root.setCredTmpUses(0);
			root.setStemp(false);
			root.setQtd_FLogin(0);
			root.setEmailS(new EmailStatus(true, true, root));
			root.setAparecerNoRank(false);
			root.setNome("Root");
			root.setCargo("Root");
			root.setEmail("root@root");
			root.setSenha(pe.encode("root"));
			root.addPerfil(Perfil.ADMIN);
			repo.save(root);
			System.out.println("Usuário root criado.");
		}
		
	}

	public SysUser buscarUserId(Integer id) {
		SysUser user = repo.findById(id).get();
		return user;
	}

	public void cadastroUser(SysUser user) throws IllegalAccessException {
		user.setData_criacao(d.obterDataEHora());
		user.setNivel(1);
		user.setPontos(0);
		user.setId(0);
		user.setData_exec(0);
		user.setCredTmpUses(0);
		user.setStemp(false);
		user.setQtd_FLogin(0);
		user.setEmailS(new EmailStatus(false, true, user));
		user.setAparecerNoRank(true);
		// Exceções
		if (user.getNome() == "" || user.getNome() == null) {
			throw new IllegalAccessException("Nome do usuário não pode ser nulo.");
		}
		if (user.getCargo() == "" || user.getCargo() == null) {
			throw new IllegalAccessException("Cargo do usuário não pode ser nulo.");
		}
		if (user.getSenha() == "" || user.getSenha() == null) {
			throw new IllegalAccessException("Senha do usuário não pode ser nulo.");
		}
		System.out.println(user.getSenha());
		user.setSenha(pe.encode(user.getSenha()));
		repo.save(user);
		// emailCadastro(user.getEmail());
	}

	public void updateDadosUser(SysUserUpdateDadosUserDTO user, HttpServletRequest request) throws IllegalAccessException {
		String header = request.getHeader("Authorization");
		SysUser userS = repo.findByEmail(jwtutil.getUsername(header.substring(7)));
		
		if(userS.getQtd_FLogin() < 10) {
			
			if (user.getNome() == null || user.getNome() == "" || user.getNome() == userS.getNome()) {
				userS.setNome(userS.getNome());
			} else {
				userS.setNome(user.getNome());
			}

			if (user.getEmail() == null || user.getEmail() == "" || user.getEmail() == userS.getEmail()) {
				userS.setEmail(userS.getEmail());
			} else {
				userS.setEmail(user.getEmail());
			}

			if (user.getCargo() == null || user.getCargo() == "" || user.getCargo() == userS.getCargo()) {
				userS.setCargo(userS.getCargo());
			} else {
				userS.setCargo(user.getCargo());
			}

			repo.saveAndFlush(userS);
		}else {
			throw new IllegalAccessException("Conta bloqueada, contate um administrador.");
		}
	}

	public void emailCadastro(String email) {
		SysUser user = repo.findByEmail(email);
		emailService.sendCreationAccountConfirmationHTMLEmail(user);
	}

	public void alteraSenha(HttpServletRequest request, String senhaAtual, String novaSenha)
			throws IllegalAccessException {

		String header = request.getHeader("Authorization");
		SysUser user = repo.findByEmail(jwtutil.getUsername(header.substring(7)));
		
		if(user.getQtd_FLogin() < 10) {
			user.setStemp(false);
			user.setCredTmpUses(0);

			if (pe.matches(senhaAtual, user.getSenha())) {
				if (senhaAtual.equalsIgnoreCase(novaSenha)) {
					throw new IllegalAccessException("Nova senha não pode ter mesma sequência de caracteres/números.");
				} else {
					user.setSenha(pe.encode(novaSenha));
				}
			} else {
				throw new IllegalAccessException("Senha incorreta.");
			}

			repo.saveAndFlush(user);
		}else {
			throw new IllegalAccessException("Conta bloqueada, contate um administrador.");
		}
		
	}

	public void addAdmin(String email, HttpServletRequest request) throws IllegalAccessException {
		String header = request.getHeader("Authorization");
		SysUser userAtual = repo.findByEmail(jwtutil.getUsername(header.substring(7)));
		
		if(userAtual.getQtd_FLogin() < 10) {
			SysUser user = repo.findByEmail(email);

			if (user == null) {
				throw new IllegalAccessException("Usuário " + "'" + email + "'" + " não foi encontrado na base.");
			}

			if (user.getPerfis().toString().contains("ADMIN")) {
				throw new IllegalAccessException(
						"Usuário " + "'" + email + "'" + " já tem direto de acesso de administrador.");
			} else {
				user.addPerfil(Perfil.ADMIN);
				repo.saveAndFlush(user);
			}
		}else {
			throw new IllegalAccessException("Conta bloqueada, contate um administrador.");
		}
	}

	public void removeAdmin(String email, HttpServletRequest request) throws IllegalAccessException {
		
		String header = request.getHeader("Authorization");
		SysUser userAtual = repo.findByEmail(jwtutil.getUsername(header.substring(7)));
		
		if(userAtual.getQtd_FLogin() < 10) {
			SysUser user = repo.findByEmail(email);
			if (user == null) {
				throw new IllegalAccessException("Usuário " + "'" + email + "'" + " não foi encontrado na base.");
			}

			if (user.getPerfis().toString().contains("ADMIN")) {
				user.removePerfil(Perfil.ADMIN);
				repo.saveAndFlush(user);
			} else {
				throw new IllegalAccessException("Usuário " + "'" + email + "'" + " não é administrador.");
			}
		}else {
			throw new IllegalAccessException("Conta bloqueada, contate um administrador.");
		}
	}

	public SysUser buscaUser(HttpServletRequest request) throws IllegalAccessException {
		String header = request.getHeader("Authorization");

		SysUser user = repo.findByEmail(jwtutil.getUsername(header.substring(7)));
		
		if (user == null) {
			throw new IllegalAccessException("Usuário não encontrado");
		}
		
		if(user.getQtd_FLogin() <10) {
			user.setSenha("*****");
			return user;
		}else {
			throw new IllegalAccessException("Conta bloqueada, contate um administrador.");
		}
		
	}

	public void updatePontos(HttpServletRequest request) throws IllegalAccessException {
		ObterDataEHora data = new ObterDataEHora();
		ConfigSystem config = repoConfig.findById(1).get();
		String header = request.getHeader("Authorization");
		SysUser user = repo.findByEmail(jwtutil.getUsername(header.substring(7)));

		if (config != null) {
			if (user != null) {
				if(user.getQtd_FLogin() < 10) {
					double horaExecUser = data.obterIntevaloHoraEmMinutos(user.getData_exec());

					if (user.getData_exec() == 0) {
						user.setPontos(user.getPontos() + config.getPontosPrimeiraExec());
						user.setCreditos(user.getCreditos() + config.getCreditosPrimeiraExec());
						user.setData_exec(data.obterDataAgora());
						System.out.println("Entrei");
						System.out.println("Data exec: " + user.getData_exec());
					} else {
						if (horaExecUser > config.getIntervaloReset()) { // Intervalo Reset
							user.setPontos(user.getPontos() + config.getPontosReset());
							user.setCreditos(user.getCreditos() + config.getCreditosReset());
							user.setData_exec(data.obterDataAgora());
						} else if (horaExecUser > config.getIntervaloMax() && horaExecUser <= config.getIntervaloReset()) { // Intervalo
																															// Max
							user.setPontos(user.getPontos() + config.getPontosIntervalMax());
							user.setCreditos(user.getCreditos() + config.getCreditosIntervalMax());
							user.setData_exec(data.obterDataAgora());
						} else if (horaExecUser > config.getIntervaloIntermediario()
								&& horaExecUser <= config.getIntervaloMax()) { // Intervalo Intermediario
							user.setPontos(user.getPontos() + config.getPontosIntervalInter());
							user.setCreditos(user.getCreditos() + config.getCreditosIntervalInter());
							user.setData_exec(data.obterDataAgora());
						} else if (horaExecUser <= config.getIntervaloIntermediario()
								&& horaExecUser >= config.getIntervaloMin()) { // Intervalo Min
							user.setPontos(user.getPontos() + config.getPontosIntervalMin());
							user.setCreditos(user.getCreditos() + config.getCreditosIntervalMin());
							user.setData_exec(data.obterDataAgora());

						} else {
							user.setPontos(user.getPontos() + 0);
							user.setCreditos(user.getCreditos() + 0);
							user.setData_exec(data.obterDataAgora());
						}

					}
					user.setNivel(updateNivel(user.getPontos()));
					repo.saveAndFlush(user);
				}else {
					throw new IllegalAccessException("Conta bloqueada, contate um administrador.");
				}
			}
		} else {
			throw new IllegalAccessException(
					"Não existem configurações do sistema na base, impossível executar o update de pontos para o usuário.");
		}
	}

	public int updateNivel(int pontos) throws IllegalAccessException {
		ConfigSystem config = repoConfig.findById(1).get();
		if (config != null) {
			if ((pontos / config.getPontosNivel()) <= 1) {
				return 1;
			} else {
				return (pontos / config.getPontosNivel());
			}
		} else {
			throw new IllegalAccessException(
					"Não existem configurações do sistema na base, impossível executar o calculo do nivel para o usuário.");
		}
	}

	@CrossOrigin(origins = "*")
	public String deletUser(String email, HttpServletRequest request) throws IllegalAccessException, ApiNotAcceptableException {
		String header = request.getHeader("Authorization");
		SysUser userAtual = repo.findByEmail(jwtutil.getUsername(header.substring(7)));
		
		SysUser user = repo.findByEmail(email);
		
		if(user != null) {
			if(userAtual.getEmail() == user.getEmail()) {
				throw new ApiNotAcceptableException("Usuário não pode deltar a própria conta.");
			}else {
				if (userAtual.getQtd_FLogin() < 10) {
					repo.deleteById(user.getId());
					return "Usuário deletado.";
				} else {
					throw new IllegalAccessException("Conta bloqueada, contate um administrador.");
				}
			}
		}else {
			throw new IllegalAccessException("Usuário " + "'" + email + "'" + " não foi encontrado na base.");
		}
		
	}

	public List<SysUserRankDTO> getRank() {
		List<SysUserRankDTO> listaDeUsuarioFinal = new ArrayList<SysUserRankDTO>();

		for (SysUser user : repo.findAllByOrderByPontosDesc()) {
			SysUserRankDTO userRank = new SysUserRankDTO();

			userRank.setCargo(user.getCargo());
			userRank.setNome(user.getNome());
			userRank.setNivel(user.getNivel());
			userRank.setPontos(user.getPontos());
			userRank.setItens(user.getItens().toString());
			
			if(user.isAparecerNoRank()) {
				listaDeUsuarioFinal.add(userRank);
			}
		}

		return listaDeUsuarioFinal;
	}

	public List<SysUser> getListaUSer() {
		List<SysUser> listaDeUsuario = new ArrayList<SysUser>();
		for (SysUser SysUser : repo.findAllByOrderByPontosDesc()) {
			listaDeUsuario.add(SysUser);
		}

		return listaDeUsuario;
	}
	
	public List<SysUser> getListaUSerADM(HttpServletRequest request) throws IllegalAccessException {
		String header = request.getHeader("Authorization");
		SysUser userAtual = repo.findByEmail(jwtutil.getUsername(header.substring(7)));
		if(userAtual.getQtd_FLogin() < 10) {
			List<SysUser> listaDeUsuario = new ArrayList<SysUser>();
			for (SysUser SysUser : repo.findAll()) {
				SysUser.setSenha("*****");
				listaDeUsuario.add(SysUser);
			}

			return listaDeUsuario;
		}else {
			throw new IllegalAccessException("Conta bloqueada, contate um administrador.");
		}
	}

	public void addItem(String item, HttpServletRequest request) throws IllegalAccessException {
		String header = request.getHeader("Authorization");
		SysUser user = repo.findByEmail(jwtutil.getUsername(header.substring(7)));
		System.out.println("Créditos: " + user.getCreditos());
		ConfigSystem config = repoConfig.getById(1);

		if (config != null) {
			if(user.getQtd_FLogin() < 10) {
				switch (item) {
				case "PONTUAL":
					if (user.getCreditos() - config.getPreco_pontual() >= 0) {
						user.setCreditos(user.getCreditos() - config.getPreco_pontual());
						user.addItem(Item.PONTUAL);
					} else {
						throw new IllegalAccessException("Usuário não tem saldo para comprar o item: " + "'" + item + "'.");
					}
					break;
				case "ALCOOL":
					if (user.getCreditos() - config.getPreco_alcool() >= 0) {
						user.setCreditos(user.getCreditos() - config.getPreco_alcool());
						user.addItem(Item.ALCOOL);
					} else {
						throw new IllegalAccessException("Usuário não tem saldo para comprar o item: " + "'" + item + "'.");
					}
					break;
				case "CRUZ":
					if (user.getCreditos() - config.getPreco_cruz() >= 0) {
						user.setCreditos(user.getCreditos() - config.getPreco_cruz());
						user.addItem(Item.CRUZ);
					} else {
						throw new IllegalAccessException("Usuário não tem saldo para comprar o item: " + "'" + item + "'.");
					}
					break;
				case "COROA":
					if (user.getCreditos() - config.getPreco_coroa() >= 0) {
						user.setCreditos(user.getCreditos() - config.getPreco_coroa());
						user.addItem(Item.COROA);
					} else {
						throw new IllegalAccessException("Usuário não tem saldo para comprar o item: " + "'" + item + "'.");
					}
					break;
				case "PERSISTENTE":
					if (user.getCreditos() - config.getPreco_persistente() >= 0) {
						user.setCreditos(user.getCreditos() - config.getPreco_persistente());
						user.addItem(Item.PERSISTENTE);
					} else {
						throw new IllegalAccessException("Usuário não tem saldo para comprar o item: " + "'" + item + "'.");
					}
					break;
				case "SUPER_LIMPO":
					if (user.getCreditos() - config.getPreco_superLimpo() >= 0) {
						user.setCreditos(user.getCreditos() - config.getPreco_superLimpo());
						user.addItem(Item.SUPER_LIMPO);
					} else {
						throw new IllegalAccessException("Usuário não tem saldo para comprar o item: " + "'" + item + "'.");
					}
					break;
				default:
					throw new IllegalAccessException("Não existe um item com o nome de " + "'" + item + "'.");
				}
				repo.saveAndFlush(user);
			}else {
				throw new IllegalAccessException("Conta bloqueada, contate um administrador.");
			}
		} else {
			throw new IllegalAccessException(
					"Não existem configurações do sistema na base impossível executar a compra de itens.");
		}
	}

	public String rmvOuAddItem(String email, String item, HttpServletRequest request) throws IllegalAccessException {
		String header = request.getHeader("Authorization");
		SysUser userAtual = repo.findByEmail(jwtutil.getUsername(header.substring(7)));
		
		SysUser user = repo.findByEmail(email);
		String retorno;
		
		if(userAtual.getQtd_FLogin() < 10) {
			if (user == null) {
				throw new IllegalAccessException("Usuário " + "'" + email + "'" + " não foi encontrado na base.");
			}

			switch (item) {
			case "PONTUAL":
				if (!user.getItens().toString().contains("PONTUAL")) {
					user.addItem(Item.PONTUAL);
					retorno= "Item adicionado.";
				}else {
					user.removeItem(Item.PONTUAL);
					retorno= "Item removido.";
				}
				break;
			case "ALCOOL":
				if (!user.getItens().toString().contains("ALCOOL")) {
					retorno= "Item adicionado.";
					user.addItem(Item.ALCOOL);
				}else {
					retorno= "Item removido.";
					user.removeItem(Item.ALCOOL);
				}
				break;
			case "CRUZ":
				if (!user.getItens().toString().contains("BORRIFADOR")) {
					user.addItem(Item.CRUZ);
					retorno= "Item adicionado.";
				}else {
					user.removeItem(Item.CRUZ);
					retorno= "Item removido.";
				}
				break;
			case "COROA":
				if (!user.getItens().toString().contains("COROA")) {
					user.addItem(Item.COROA);
					retorno= "Item adicionado.";
				}else {
					user.removeItem(Item.COROA);
					retorno= "Item removido.";
				}
				
				break;
			case "PERSISTENTE":
				if (!user.getItens().toString().contains("PERSISTENTE")) {
					user.addItem(Item.PERSISTENTE);
					retorno= "Item adicionado.";
				}else {
					user.removeItem(Item.PERSISTENTE);
					retorno= "Item removido.";
				}
				
				break;
			case "SUPER_LIMPO":
				if (!user.getItens().toString().contains("SUPER_LIMPO")) {
					user.addItem(Item.SUPER_LIMPO);
					retorno= "Item adicionado.";
				}else {
					user.removeItem(Item.SUPER_LIMPO);
					retorno= "Item removido.";
				}
				
				break;
			default:
				throw new IllegalAccessException("Não existe um item com o nome de " + "'" + item + "'.");
			}
			repo.saveAndFlush(user);
			return retorno;
		}else {
			throw new IllegalAccessException("Conta bloqueada, contate um administrador.");
		}
	}

	public String addPontosECreditosADM(String email, int valorPontos, int valorCreditos, HttpServletRequest request) throws IllegalAccessException {
		String header = request.getHeader("Authorization");
		SysUser userAtual = repo.findByEmail(jwtutil.getUsername(header.substring(7)));
		if(userAtual.getQtd_FLogin() < 10) {
			SysUser user = repo.findByEmail(email);
			if (user != null) {
				user.setPontos(user.getPontos() + valorPontos);
				user.setCreditos(user.getCreditos() + valorCreditos);
				user.setNivel(updateNivel(user.getPontos()));
				repo.saveAndFlush(user);
				return "Pontos e créditos adicionados.";
			} else {
				throw new IllegalAccessError("Usuário " + "'" + email + "'" + " não foi encontrado na base.");
			}
		}else {
			throw new IllegalAccessException("Conta bloqueada, contate um administrador.");
		}
	}

	public static SysUserSS authenticated() {
		try {
			return (SysUserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}

	}

	public String valid(String senha, HttpServletRequest request) throws IllegalAccessException {
		String header = request.getHeader("Authorization");
		SysUser user = repo.findByEmail(jwtutil.getUsername(header.substring(7)));

		if(user.getQtd_FLogin() < 10) {
			if (pe.matches(senha, user.getSenha())) {
				return "true";
			}else {
				throw new IllegalAccessException("Senha incorreta.");
			}
		}else {
			throw new IllegalAccessException("Conta bloqueada, contate um administrador.");
		}
	}

	public String bloqueiaOuDesbloqueiaContaADM(EmailDTO email, HttpServletRequest request) throws IllegalAccessException, ApiNotAcceptableException {
		String header = request.getHeader("Authorization");
		SysUser userAtual = repo.findByEmail(jwtutil.getUsername(header.substring(7)));
		
		if(userAtual.getQtd_FLogin() < 10) {
			SysUser user = repo.findByEmail(email.getEmail());
				
				if (user != null) {
					if(userAtual.getEmail() == user.getEmail()) {
						throw new ApiNotAcceptableException("Usuário não pode bloquear a própria conta.");
					}else {
						if (user.getQtd_FLogin() >= 10) {
							user.setQtd_FLogin(5);
							repo.saveAndFlush(user);
							return "Conta do usuário desbloqueada.";
						} else {
							user.setQtd_FLogin(10);
							repo.saveAndFlush(user);
							return "Conta do usuário bloqueada.";
						}
					}
				}else {
					throw new IllegalAccessError("Usuário não encontrado");
				}
		}else {
			throw new IllegalAccessException("Conta bloqueada, contate um administrador.");
		}
	}
	
	public String escondeOuMostraNoRank(String email, HttpServletRequest request) throws IllegalAccessException {
		String header = request.getHeader("Authorization");
		SysUser userAtual = repo.findByEmail(jwtutil.getUsername(header.substring(7)));
		
		if(userAtual.getQtd_FLogin() < 10) {
			SysUser user = repo.findByEmail(email);
			
			if(user == null) {
				
				throw new IllegalAccessException("Usuário não encontrado.");
			}
			
			if(user.isAparecerNoRank()) {
				user.setAparecerNoRank(false);
				repo.saveAndFlush(user);
				return "Usuário não irá mais aparecer no Rank.";
			}else {
				user.setAparecerNoRank(true);
				repo.saveAndFlush(user);
				return "Usuário voltou a aparecer no Rank.";
			}
		}else {
			throw new IllegalAccessException("Conta bloqueada, contate um administrador.");
		}
	}
	
	public SysUser buscaPorEmailADM(String email, HttpServletRequest request) throws IllegalAccessException {
		String header = request.getHeader("Authorization");
		SysUser userAtual = repo.findByEmail(jwtutil.getUsername(header.substring(7)));
		
		if(userAtual.getQtd_FLogin() < 10) {
			SysUser user = repo.findByEmail(email);
			if(user == null) {
				throw new IllegalAccessException("Usuário não encontrado.");
			}
			user.setSenha("*****");
			return user;
		}else {
			throw new IllegalAccessException("Conta bloqueada, contate um administrador.");
		}
	}
}