package br.com.sistemacomercialerp.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import br.com.sistemacomercialerp.entidade.BalancoEstoque;
import br.com.sistemacomercialerp.entidade.BalancoEstoqueProdutos;
import br.com.sistemacomercialerp.entidade.Cliente;
import br.com.sistemacomercialerp.entidade.Produto;
import br.com.sistemacomercialerp.servico.BalancoEstoqueServico;
import br.com.sistemacomercialerp.util.LoggerUtil;
import br.com.sistemacomercialerp.util.MensagemPadrao;
import br.com.sistemacomercialerp.util.exception.SistemaComercialException;
import br.com.sistemacomercialerp.util.jsf.FacesUtil;

@Named
@ViewScoped
public class BalancoEstoqueController implements Serializable{

	private static final long serialVersionUID = -7458145414776723889L;

	@Inject
	private BalancoEstoqueServico servico;

	private BalancoEstoque balancoEstoque;

	private BalancoEstoqueProdutos balancoEstoqueProduto;

	private List<BalancoEstoqueProdutos> listaBalancoEstoqueProdutos;

	// Variaveis para controle de tela
	private static int numeroItem;
	private List<Produto> listaProduto;
	private Produto produto;
	private int abaDadosItens;
	private String referencia;

	@PostConstruct
	public void inicializar() {
		balancoEstoque = new BalancoEstoque();
		listaBalancoEstoqueProdutos = new ArrayList<>();
		inserirItemVazio();
		numeroItem = 1;
		abaDadosItens = 0;
	}

	public void salvar() {
		try {
			inserirItem();
			balancoEstoque = servico.save(balancoEstoque);
			limpar();
			MensagemPadrao.mensagemSalvar();
			LoggerUtil.registrarLoggerInfo(getClass(),
					"Registro Gravado com Sucesso");

		} catch (Exception e) {
			MensagemPadrao.mensagemErroSalvar(e.getMessage());
			LoggerUtil.registrarLoggerErro(
					getClass(),
					"Erro ao salvar o registro! " + e.getMessage() + " "
							+ e.getCause());
		}
	}

	public void inserirItem() throws SistemaComercialException {
		getBalancoEstoque().setItens(getListaBalancoEstoqueProdutos());
		if (balancoEstoque.verificarListaVazia()) {
			throw new SistemaComercialException(
					"Informe pelo menos 1 produto para fechar o balanço de estoque!");
		}
		getBalancoEstoque().getItens().remove(0);
	}

	public void inicializarParametros() {
		if (StringUtils.isNotBlank(FacesUtil.getParameter("idBalancoEstoque"))) {
			try {
				setBalancoEstoque(this.servico.consultarId((Long.valueOf(FacesUtil
						.getParameter("idBalancoEstoque")))));
				this.balancoEstoque.ordenarLista();
				listaBalancoEstoqueProdutos = this.balancoEstoque.getItens();

			} catch (Exception e) {
				MensagemPadrao.mensagemErroCarregarParametro(e.getMessage());
				LoggerUtil.registrarLoggerErro(getClass(),
						"Erro: " + e.getMessage() + " " + e.getCause());
			}
		}

	}

	public List<Cliente> getListarCliente() {
		try {
			return servico.consultaTodosClientesAtivos();
		} catch (Exception e) {
			LoggerUtil.registrarLoggerErro(getClass(),
					"Erro ao listar os clientes: " + e.getMessage());
			return null;
		}
	}
	
	public void inserirItemVazio() {
		instanciarBalancoEstoqueProduto();
		balancoEstoqueProduto.setNumeroItem(0);
		listaBalancoEstoqueProdutos.add(0, balancoEstoqueProduto);
	}

	public void instanciarBalancoEstoqueProduto() {
		balancoEstoqueProduto = new BalancoEstoqueProdutos();
		balancoEstoqueProduto.setProduto(new Produto());
	}

	public List<Produto> autoCompleteProduto(String descricaoProduto) {
		try {
			listaProduto = new ArrayList<>();
			listaProduto = servico.autoCompleteProduto(descricaoProduto
					.toUpperCase());
		} catch (Exception e) {
			LoggerUtil.registrarLoggerErro(getClass(),
					"Erro no auto complete produto " + e.getMessage());
		}
		return listaProduto;
	}

	public void setarProdutoNaListaItem() throws SistemaComercialException {
		try {
			verificarItemExistenteNaListaItem();
			if (produto.getId() == null) {
				produto = servico.carregarProdutoPorReferencia(produto
						.getcodigoBarras().toUpperCase());
			}
			if (produto != null) {
				instanciarBalancoEstoqueProduto();
				balancoEstoqueProduto.setProduto(produto);
					balancoEstoqueProduto.setBalancoEstoque(balancoEstoque);
					balancoEstoqueProduto.setNumeroItem(numeroItem);
					balancoEstoqueProduto.setVolumeAnterior(produto.getQuantidade());
					if(balancoEstoqueProduto.getTipoBalanco() == 1){
					balancoEstoqueProduto
							.setVolumeAjustado(produto.getQuantidade() 
									+ balancoEstoqueProduto.getVolumeAjuste());
					}else{
						balancoEstoqueProduto
						.setVolumeAjustado(produto.getQuantidade() 
								- balancoEstoqueProduto.getVolumeAjuste());	
					}
					listaBalancoEstoqueProdutos.add(balancoEstoqueProduto);
					setarNulo();
					numeroItem += 1;
			} else {
				setarNulo();
			}
		} catch (Exception e) {
			LoggerUtil.registrarLoggerErro(getClass(), e.getMessage());
			MensagemPadrao.mensagemErroValidacao(e.getMessage());
			setarNulo();
		}
	}

	public void setarNulo() {
		referencia = null;
		produto = null;
	}

	public void verificarItemExistenteNaListaItem()
			throws SistemaComercialException {
		if (referencia != null) {
			produto = new Produto();
			produto.setcodigoBarras(referencia);
		}
		for (BalancoEstoqueProdutos b : listaBalancoEstoqueProdutos) {
			if (b.getProduto().getcodigoBarras() != null) {
				if (b.getProduto().getcodigoBarras()
						.equals(produto.getcodigoBarras())) {
					throw new SistemaComercialException(
							"Produto com referência "
									+ produto.getcodigoBarras()
									+ " já informado");
				}
			}
		}
	}
	
	public void atualizarQuantidade(BalancoEstoqueProdutos item, int linha) {
		try {
			balancoEstoqueProduto = item;
			if(balancoEstoqueProduto.getTipoBalanco() == 1){
			balancoEstoqueProduto.setVolumeAjustado(balancoEstoqueProduto.getVolumeAnterior() + balancoEstoqueProduto.getVolumeAjuste());
			}else{
			balancoEstoqueProduto.setVolumeAjustado(balancoEstoqueProduto.getVolumeAnterior() - balancoEstoqueProduto.getVolumeAjuste());
			}
		} catch (Exception e) {
			MensagemPadrao.mensagemErroValidacao(e.getMessage());
		}
	}
	
	public void limpar() {
		inicializar();
	}

	public BalancoEstoqueServico getServico() {
		return servico;
	}

	public void setServico(BalancoEstoqueServico servico) {
		this.servico = servico;
	}

	public BalancoEstoque getBalancoEstoque() {
		return balancoEstoque;
	}

	public void setBalancoEstoque(BalancoEstoque balancoEstoque) {
		this.balancoEstoque = balancoEstoque;
	}

	public BalancoEstoqueProdutos getBalancoEstoqueProduto() {
		return balancoEstoqueProduto;
	}

	public void setBalancoEstoqueProduto(
			BalancoEstoqueProdutos balancoEstoqueProduto) {
		this.balancoEstoqueProduto = balancoEstoqueProduto;
	}

	public List<BalancoEstoqueProdutos> getListaBalancoEstoqueProdutos() {
		return listaBalancoEstoqueProdutos;
	}

	public void setListaBalancoEstoqueProdutos(
			List<BalancoEstoqueProdutos> listaBalancoEstoqueProdutos) {
		this.listaBalancoEstoqueProdutos = listaBalancoEstoqueProdutos;
	}

	public static int getNumeroItem() {
		return numeroItem;
	}

	public static void setNumeroItem(int numeroItem) {
		BalancoEstoqueController.numeroItem = numeroItem;
	}

	public List<Produto> getListaProduto() {
		return listaProduto;
	}

	public void setListaProduto(List<Produto> listaProduto) {
		this.listaProduto = listaProduto;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public int getAbaDadosItens() {
		return abaDadosItens;
	}

	public void setAbaDadosItens(int abaDadosItens) {
		this.abaDadosItens = abaDadosItens;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

}
