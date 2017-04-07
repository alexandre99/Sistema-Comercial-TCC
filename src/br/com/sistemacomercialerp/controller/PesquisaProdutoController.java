package br.com.sistemacomercialerp.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sistemacomercialerp.entidade.Produto;
import br.com.sistemacomercialerp.servico.ProdutoServico;
import br.com.sistemacomercialerp.util.LoggerUtil;

@Named
@ViewScoped
public class PesquisaProdutoController implements Serializable {

	private static final long serialVersionUID = -2179335895704782289L;

	@Inject
	private ProdutoServico servico;

	private Produto produtoSelecionado;
	private List<Produto> listaProduto;

	// Variaveis de filtro
	private String filtro;
	private boolean produtosInativos;

	@PostConstruct
	public void inicializar() {
		produtoSelecionado = new Produto();
		produtosInativos = false;
		listaProduto = new ArrayList<>();
	}

	public void pesquisarProduto() {
		try {
			if (produtosInativos) {
				produtosInativos = false;
			}
			listaProduto = servico.consultaProduto(filtro);
			produtoSelecionado = new Produto();
		} catch (Exception e) {
			LoggerUtil.registrarLoggerErro(getClass(), "Erro " + e.getMessage()
					+ " " + e.getCause());
		}
	}

	public void pesquisarProdutosInativos() {
		try {
			if (!"".equals(filtro)) {
				filtro = "";
			}
			listaProduto = servico.consultarProdutosInativos();
			produtoSelecionado = new Produto();
		} catch (Exception e) {
			LoggerUtil.registrarLoggerErro(getClass(), "Erro " + e.getMessage()
					+ " " + e.getCause());
		}

	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Produto> getListaProduto() {
		return listaProduto;
	}

	public void setListaProduto(List<Produto> listaProduto) {
		this.listaProduto = listaProduto;
	}

	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}

	public boolean isProdutosInativos() {
		return produtosInativos;
	}

	public void setProdutosInativos(boolean produtosInativos) {
		this.produtosInativos = produtosInativos;
	}

}
