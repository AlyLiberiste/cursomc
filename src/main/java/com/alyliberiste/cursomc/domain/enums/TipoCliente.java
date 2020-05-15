package com.alyliberiste.cursomc.domain.enums;

public enum TipoCliente {
	PESSOAFISICA(1, "Pessoa Física"),//COD CRIADO MANNUALMTE P/ DIF AS 2 CAT DE PESSOA
	PESSOAJURIDICA(2, "Pessoa Júridica");
	
	private int cod;
	private String descricao;
	
	private TipoCliente(int cod, String descricao) { //CONSTRUTOR DE TIPO ENUM É PRIVATE
		this.cod = cod;
		this.descricao = descricao;
		
	}
	public int getCod() {
		return cod;
	}
	public String getDescricao() {
		return descricao;
	}
	public static TipoCliente toEnum(Integer cod) {	//DADO 1 NUM INT. RETORNA O TIPO CLIENTE EQUIV

		if (cod == null) {
			return null;
		}
		for (TipoCliente x : TipoCliente.values()){
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido: " + cod);
	}

}
