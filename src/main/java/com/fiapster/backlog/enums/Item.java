package com.fiapster.backlog.enums;

public enum Item {
	
	PONTUAL(1, "ITEM1"),
	COROA(2, "ITEM2"),
	SUPER_LIMPO(3, "ITEM3"),
	PERSISTENTE(4, "ITEM4"),
	CRUZ(5, "ITEM5"),
	ALCOOL(6, "ITEM6");
	
	private int cod;
	private String descricao;
	
	private Item(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static Item toEnum(Integer cod) {
		
		if(cod == null) {
			return null;
		}
		
		for(Item i : Item.values()) {
			if(cod.equals(i.getCod())) {
				return i;
			}
		}
		
		throw new IllegalArgumentException("Tipo do item inválido " + cod);
		
	}

}
