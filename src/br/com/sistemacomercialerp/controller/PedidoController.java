package br.com.sistemacomercialerp.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import br.com.sistemacomercialerp.entidade.Cliente;
import br.com.sistemacomercialerp.entidade.FormaDePagamento;
import br.com.sistemacomercialerp.entidade.Pedido;
import br.com.sistemacomercialerp.entidade.PedidoItem;
import br.com.sistemacomercialerp.entidade.Produto;
import br.com.sistemacomercialerp.entidade.SituacaoPedido;
import br.com.sistemacomercialerp.entidade.UnidadeVenda;
import br.com.sistemacomercialerp.entidade.Usuario;
import br.com.sistemacomercialerp.servico.PedidoServico;
import br.com.sistemacomercialerp.util.LoggerUtil;
import br.com.sistemacomercialerp.util.MensagemPadrao;
import br.com.sistemacomercialerp.util.exception.SistemaComercialException;
import br.com.sistemacomercialerp.util.jsf.FacesUtil;
import br.com.sistemacomercialerp.util.jsf.PrimeFacesUtil;

@Named
@ViewScoped
public class PedidoController implements Serializable {

	private static final long serialVersionUID = 6228694959121594669L;

	@Inject
	private PedidoServico servico;

	private Pedido pedido;

	private PedidoItem pedidoItem;

	private List<PedidoItem> listaPedidoItem;

	// Variaveis para controle de tela
	private static int numeroItem;
	private List<Produto> listaProduto;
	private Produto produto;
	private int abaDadosItens;
	private String referencia;

	@PostConstruct
	public void inicializar() {
		pedido = new Pedido();
		listaPedidoItem = new ArrayList<>();
		inserirItemVazio();
		numeroItem = 1;
		abaDadosItens = 0;
	}

	public void salvar() {
		try {
			inserirItem();
			pedido = servico.save(pedido);
			ImprimirPedidoController.setPedidoSelecionado(pedido);
			limpar();
			MensagemPadrao.mensagemSalvar();
			LoggerUtil.registrarLoggerInfo(getClass(),
					"Registro Gravado com Sucesso");
			PrimeFacesUtil
					.executarFuncaoJavascript("PF('impressaoPedido').show()");

		} catch (Exception e) {
			MensagemPadrao.mensagemErroSalvar(e.getMessage());
			LoggerUtil.registrarLoggerErro(
					getClass(),
					"Erro ao salvar o registro! " + e.getMessage() + " "
							+ e.getCause());
		}
	}

	public void inserirItem() throws SistemaComercialException {
		getPedido().setPedidoItem(getListaPedidoItem());
		if (pedido.verificarListaVazia()) {
			throw new SistemaComercialException(
					"Informe pelo menos 1 produto para fechar o pedido");
		}
		if (pedido.verificarItemComEstoqueInsuficiente()) {
			throw new SistemaComercialException(
					"Existem item(s) com estoque insuficiente para fechamento do pedido");
		}
		getPedido().getPedidoItem().remove(0);

	}

	public void inicializarParametros() {
		if (StringUtils.isNotBlank(FacesUtil.getParameter("idPedido"))) {
			try {
				setPedido(this.servico.consultarId((Long.valueOf(FacesUtil
						.getParameter("idPedido")))));
				this.pedido.ordenarLista();
				listaPedidoItem = this.pedido.getPedidoItem();

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

	public List<Usuario> getListarVendedor() {
		try {
			return servico.consultaTodosUsuariosAtivos();
		} catch (Exception e) {
			LoggerUtil.registrarLoggerErro(getClass(),
					"Erro ao listar os clientes: " + e.getMessage());
			return null;
		}
	}

	public void inserirItemVazio() {
		instanciarPedidoItem();
		pedidoItem.setNumeroItem(0);
		listaPedidoItem.add(0, pedidoItem);
	}

	public void instanciarPedidoItem() {
		pedidoItem = new PedidoItem();
		pedidoItem.setProduto(new Produto());
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
				instanciarPedidoItem();
				pedidoItem.setProduto(produto);
				if (pedidoItem.isEstoqueDisponivel()) {
					pedidoItem.setPedido(pedido);
					pedidoItem.setNumeroItem(numeroItem);
					pedidoItem.setValorUnitarioItem(produto.getPrecoVenda());
					pedidoItem
							.setValorTotalItem(produto.getPrecoVenda()
									.multiply(
											new BigDecimal(pedidoItem
													.getQuantidade())));
					listaPedidoItem.add(pedidoItem);
					setarNulo();
					calcularValorNaView();
					numeroItem += 1;
				} else {
					throw new SistemaComercialException(
							"Quantidade solicitada indisponível!");
				}
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

	public void calcularValorNaView() {
		pedido.setValorBruto(pedido.getValorBruto().add(
				pedidoItem.getValorTotalItem()));
		pedido.setValorLiquido(pedido.getValorLiquido().add(
				pedidoItem.getValorTotalItem()));
	}

	public void calcularDescontoGlobal() {
		try {
			if (pedido.getValorDesconto().compareTo(pedido.getValorLiquido()) == 1) {
				throw new SistemaComercialException(
						"O desconto não pode ser maior do que o valor final do pedido");
			} else if (pedido.getValorDesconto().equals(new BigDecimal("0.00"))) {
				pedido.setValorLiquido(pedido.getValorBruto());
			} else {
				pedido.setValorLiquido(pedido.getValorBruto().subtract(
						pedido.getValorDesconto()));
			}
		} catch (Exception e) {
			MensagemPadrao.mensagemErroValidacao(e.getMessage());
			zerarDesconto();
		}
	}

	public void atualizarQuantidade(PedidoItem item, int linha) {
		try {
			pedidoItem = item;
			if (pedidoItem.getQuantidade() == 0) {
				listaPedidoItem.remove(linha);
				recalcularValorTotalItemRemovido(true);
			} else {
				if (pedidoItem.isEstoqueDisponivel()) {
					recalcularValorTotalItemRemovido(false);
					pedidoItem
							.setValorTotalItem(pedidoItem
									.getProduto()
									.getPrecoVenda()
									.multiply(
											new BigDecimal(pedidoItem
													.getQuantidade())));
					calcularValorNaView();
				} else {
					throw new SistemaComercialException(
							"Quantidade solicitada indisponível!");
				}
			}
		} catch (Exception e) {
			MensagemPadrao.mensagemErroValidacao(e.getMessage());
		}
	}

	public void recalcularValorTotalItemRemovido(boolean zeraDesconto) {
		pedido.setValorBruto(pedido.getValorBruto().subtract(
				pedidoItem.getValorTotalItem()));
		pedido.setValorLiquido(pedido.getValorLiquido().subtract(
				pedidoItem.getValorTotalItem()));
		if (zeraDesconto) {
			if (pedido.getValorDesconto().compareTo(pedido.getValorLiquido()) == 1) {
				zerarDesconto();
				pedido.setValorLiquido(pedido.getValorBruto());
			}
		}
	}

	public void zerarDesconto() {
		pedido.setValorDesconto(new BigDecimal("0.00"));
	}

	public void verificarItemExistenteNaListaItem()
			throws SistemaComercialException {
		if (referencia != null) {
			produto = new Produto();
			produto.setcodigoBarras(referencia);
		}
		for (PedidoItem p : listaPedidoItem) {
			if (p.getProduto().getcodigoBarras() != null) {
				if (p.getProduto().getcodigoBarras()
						.equals(produto.getcodigoBarras())) {
					throw new SistemaComercialException(
							"Produto com referência "
									+ produto.getcodigoBarras()
									+ " já informado");
				}
			}
		}
	}

	public void limpar() {
		inicializar();
	}

	public UnidadeVenda[] getUnidadesVenda() {
		return UnidadeVenda.values();
	}

	public SituacaoPedido[] getSituacaoPedido() {
		return SituacaoPedido.values();
	}

	public FormaDePagamento[] getFormaDePagamento() {
		return FormaDePagamento.values();
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public PedidoItem getPedidoItem() {
		return pedidoItem;
	}

	public void setPedidoItem(PedidoItem pedidoItem) {
		this.pedidoItem = pedidoItem;
	}

	public List<PedidoItem> getListaPedidoItem() {
		return listaPedidoItem;
	}

	public void setListaPedidoItem(List<PedidoItem> listaPedidoItem) {
		this.listaPedidoItem = listaPedidoItem;
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
