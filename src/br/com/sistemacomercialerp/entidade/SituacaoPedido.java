package br.com.sistemacomercialerp.entidade;

public enum SituacaoPedido {

	ORCAMENTO("Orçamento"), PEDIDOVENDA("Pedido de Venda"), VENDA("Venda");

	private String descricao;

	SituacaoPedido(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
