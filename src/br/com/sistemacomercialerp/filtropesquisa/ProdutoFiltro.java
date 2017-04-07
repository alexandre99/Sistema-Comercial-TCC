package br.com.sistemacomercialerp.filtropesquisa;

import java.io.Serializable;

public class ProdutoFiltro implements Serializable {

	private static final long serialVersionUID = 7540218213565973900L;

	private String codigoBarras;
	private String descricao;
	private String unidadeVenda;
	private String precoCusto;
	private String precoVenda;

	public ProdutoFiltro() {
		super();
	}

	public ProdutoFiltro(String codigoBarras, String descricao,
			String unidadeVenda, String precoCusto, String precoVenda) {
		super();
		this.codigoBarras = codigoBarras;
		this.descricao = descricao;
		this.unidadeVenda = unidadeVenda;
		this.precoCusto = precoCusto;
		this.precoVenda = precoVenda;
	}

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUnidadeVenda() {
		return unidadeVenda;
	}

	public void setUnidadeVenda(String unidadeVenda) {
		this.unidadeVenda = unidadeVenda;
	}

	public String getPrecoCusto() {
		return precoCusto;
	}

	public void setPrecoCusto(String precoCusto) {
		this.precoCusto = precoCusto;
	}

	public String getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(String precoVenda) {
		this.precoVenda = precoVenda;
	}

}
