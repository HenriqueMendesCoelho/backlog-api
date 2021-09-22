package com.fiapster.backlog.enums;

public enum Perfil {
	
	ADMIN(1, "ROLE_ADMIN"),
	USUARIO(2, "ROLE_USUARIO");
	
	private int cod;
	private String descricao;
	
	private Perfil(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static Perfil toEnum(Integer cod) {
		
		if(cod == null) {
			return null;
		}
		
		for(Perfil i : Perfil.values()) {
			if(cod.equals(i.getCod())) {
				return i;
			}
		}
		
		throw new IllegalArgumentException("Tipo do usuário invalido " + cod);
		
	}

}
