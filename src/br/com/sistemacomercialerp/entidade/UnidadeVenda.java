package br.com.sistemacomercialerp.entidade;

public enum UnidadeVenda {

	UN("UN"), KG("KG"), LT("LT"), MT("MT"), METRO2("MT²"), METRO3("MT³"), CX(
			"CX"), PC("PC"), FD("FD");

	private String descricao;

	UnidadeVenda(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
