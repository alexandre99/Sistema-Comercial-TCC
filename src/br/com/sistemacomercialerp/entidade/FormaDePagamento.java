package br.com.sistemacomercialerp.entidade;

public enum FormaDePagamento {
	DINHEIRO("Dinheiro"), CARTAO("Cartão"), BOLETO("Boleto"), CHEQUEAPRAZO(
			"Cheque a Prazo"), CHEQUEAVISTA("Cheque a Vista");

	private String descricao;

	FormaDePagamento(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
