package br.com.sistemacomercialerp.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sistemacomercialerp.entidade.Pedido;
import br.com.sistemacomercialerp.servico.PedidoServico;
import br.com.sistemacomercialerp.util.LoggerUtil;
import br.com.sistemacomercialerp.util.MensagemPadrao;

@Named
@ViewScoped
public class PesquisaPedidoController implements Serializable {

	private static final long serialVersionUID = -2179335895704782289L;

	@Inject
	private PedidoServico servico;

	@Inject
	private ImprimirPedidoController imprimirPedido;

	private Pedido pedidoSelecionado;
	private List<Pedido> listaPedido;

	// Variáveis de filtro
	private String filtro;
	private boolean pedidoInativos;
	private String situacaoPedido;

	@PostConstruct
	public void inicializar() {
		pedidoSelecionado = new Pedido();
		pedidoInativos = false;
		listaPedido = new ArrayList<>();
		situacaoPedido = "VENDA";
	}

	public void pesquisarPedido() {
		try {

			if (pedidoInativos) {
				pedidoInativos = false;
			}
			listaPedido = servico.consultaPedido(filtro, situacaoPedido);
			pedidoSelecionado = new Pedido();
		} catch (Exception e) {
			LoggerUtil.registrarLoggerErro(getClass(), "Erro " + e.getMessage()
					+ " " + e.getCause());
		}
	}

	public void pesquisarPedidosInativos() {
		try {
			if (!"".equals(filtro)) {
				filtro = "";
			}
			situacaoPedido = "VENDA";
			listaPedido = servico.consultarPedidosInativos();
			pedidoSelecionado = new Pedido();
		} catch (Exception e) {
			LoggerUtil.registrarLoggerErro(getClass(), "Erro " + e.getMessage()
					+ " " + e.getCause());
		}

	}

	public void cancelarPedido() {
		try {
			servico.cancelarPedido(pedidoSelecionado);
			posCancelamentoPedido();
		} catch (Exception e) {
			MensagemPadrao.mensagemErroValidacao(e.getMessage());
			LoggerUtil.registrarLoggerErro(getClass(), "Erro " + e.getMessage()
					+ " " + e.getCause());
		}
	}

	public void posCancelamentoPedido() {
		listaPedido.remove(pedidoSelecionado);
		pedidoSelecionado = new Pedido();

		MensagemPadrao.mensagemCancelamentoPedido();
	}

	public void reimprimirPedido() {
		try {
			ImprimirPedidoController.setPedidoSelecionado(this.pedidoSelecionado);
			imprimirPedido.imprimirPedido();
			
		} catch (Exception e) {
			MensagemPadrao.mensagemErroSalvar(e.getMessage());
			LoggerUtil.registrarLoggerErro(getClass(),
					e.getMessage() + " " + e.getCause());
		}
	}

	public Pedido getPedidoSelecionado() {
		return pedidoSelecionado;
	}

	public void setPedidoSelecionado(Pedido pedidoSelecionado) {
		this.pedidoSelecionado = pedidoSelecionado;
	}

	public List<Pedido> getListaPedido() {
		return listaPedido;
	}

	public void setListaPedido(List<Pedido> listaPedido) {
		this.listaPedido = listaPedido;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public boolean isPedidoInativos() {
		return pedidoInativos;
	}

	public void setPedidoInativos(boolean pedidoInativos) {
		this.pedidoInativos = pedidoInativos;
	}

	public String getSituacaoPedido() {
		return situacaoPedido;
	}

	public void setSituacaoPedido(String situacaoPedido) {
		this.situacaoPedido = situacaoPedido;
	}

}
