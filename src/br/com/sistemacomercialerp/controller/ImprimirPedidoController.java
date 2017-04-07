package br.com.sistemacomercialerp.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sistemacomercialerp.entidade.Pedido;
import br.com.sistemacomercialerp.relatorio.Relatorio;
import br.com.sistemacomercialerp.util.LoggerUtil;

@Named
@RequestScoped
public class ImprimirPedidoController implements Serializable {

	private static final long serialVersionUID = 6631531823152502605L;

	@Inject
	private Relatorio imprimirPedido;

	private static Pedido pedidoSelecionado;

	public void imprimirPedido() {
		try {
			Map<String, Object> parametros = new HashMap<>();
			parametros.put("idPedido", pedidoSelecionado.getId());
			parametros.put("situacaoPedido", pedidoSelecionado.getSituacao()
					.getDescricao());
			parametros.put("formaPagamento", pedidoSelecionado
					.getFormaDePagamento().getDescricao());

			imprimirPedido.executarRelatorio(parametros,
					"RelatorioOrcamento.jasper");

		} catch (Exception e) {
			LoggerUtil.registrarLoggerErro(
					getClass(),
					"Erro ao imprimir o this.pedidoSelecionado: "
							+ e.getMessage() + " " + e.getCause());
		}
	}

	public static Pedido getPedidoSelecionado() {
		return pedidoSelecionado;
	}

	public static void setPedidoSelecionado(Pedido pedidoSelecionado) {
		ImprimirPedidoController.pedidoSelecionado = pedidoSelecionado;
	}

}
