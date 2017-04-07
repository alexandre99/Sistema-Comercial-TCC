package br.com.sistemacomercialerp.servico;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import org.apache.commons.lang3.time.DateUtils;

import br.com.sistemacomercialerp.dao.PedidoDao;
import br.com.sistemacomercialerp.entidade.Cliente;
import br.com.sistemacomercialerp.entidade.Pedido;
import br.com.sistemacomercialerp.entidade.PedidoItem;
import br.com.sistemacomercialerp.entidade.Produto;
import br.com.sistemacomercialerp.entidade.SituacaoPedido;
import br.com.sistemacomercialerp.entidade.Usuario;
import br.com.sistemacomercialerp.filtropesquisa.PedidoFiltro;
import br.com.sistemacomercialerp.util.Servico;
import br.com.sistemacomercialerp.util.exception.SistemaComercialException;
import br.com.sistemacomercialerp.util.jpa.Transacao;
import br.com.sistemacomercialerp.util.validacao.ValidacaoEmComum;

public class PedidoServico implements Serializable, Servico<Pedido> {

	private static final long serialVersionUID = 6381500834348734340L;

	@Inject
	private PedidoDao dao;

	@Inject
	private ClienteServico servicoCliente;

	@Inject
	private UsuarioServico servicoUsuario;

	@Inject
	private ProdutoServico servicoProduto;

	@Override
	public void inserir(Pedido entidade) throws SistemaComercialException {
	}

	@Transacao
	public Pedido save(Pedido entidade) {
		entidade = dao.save(entidade);

		if (!entidade.getSituacao().getDescricao().equals("Orçamento")) {
			servicoProduto.baixarEstoqueItens(entidade.getPedidoItem());
		}

		return entidade;
	}

	@Override
	public void atualizar(Pedido entidade) {
		dao.atualizar(entidade);
	}

	@Override
	public List<Pedido> consultarTodos() {
		return null;
	}

	@Override
	@Transacao
	public Pedido consultarId(Long id) {
		boolean estornarEstoque = false;
		Pedido pedido = dao.consultarId(id);
		dao.limparSessaoJpa();

		if (!pedido.getSituacao().getDescricao().equals("Orçamento")) {
			pedido.setSituacao(SituacaoPedido.ORCAMENTO);
			estornarEstoque = true;
		}

		if (estornarEstoque) {
			List<PedidoItem> listaPedidoItem = servicoProduto
					.estornarEstoque(pedido.getPedidoItem());
			pedido.getPedidoItem().clear();
			pedido.setPedidoItem(listaPedidoItem);
		}

		atualizar(pedido);
		return pedido;
	}

	public List<Pedido> consultaPedido(String filtro, String situacaoPedido) {
		List<Pedido> listaPedido = new ArrayList<>();

		if (filtro.equals("")) {
			listaPedido = dao.consultarTodosPedidos(situacaoPedido);
		} else {
			listaPedido = consultarPorFiltro(filtro, situacaoPedido);
		}

		return listaPedido;
	}

	public List<Pedido> consultarPorFiltro(String filtro, String situacaoPedido) {
		filtro = "%" + filtro + "%".trim();

		for (int i = 0; i < filtro.length(); i++) {
			if (filtro.charAt(i) == ',') {
				filtro = ValidacaoEmComum.trocarPontoPorEspaco(filtro);
			}
		}

		filtro = ValidacaoEmComum.trocarVirgulaPorPonto(filtro);

		return dao.consultarPorFiltro(filtro, situacaoPedido);
	}

	@Transacao
	public void cancelarPedido(Pedido entidade)
			throws SistemaComercialException {

		if (entidade == null) {
			throw new SistemaComercialException(
					"Selecione um pedido para fazer o cancelamento");
		}

		if (entidade.isAtivo()) {
			if (entidade.getSituacao().getDescricao().equals("Orçamento")) {
				throw new SistemaComercialException(
						"Não é possível cancelar um pedido com situação orçamento");
			}
			entidade.setAtivo(false);
			Pedido pedido = dao.consultarId(entidade.getId());
			pedido.setAtivo(entidade.isAtivo());
			atualizar(pedido);
			servicoProduto.estornarEstoque(pedido.getPedidoItem());
		} else {
			throw new SistemaComercialException(
					"Pedido selecionado já está cancelado!");
		}
	}

	public Map<Date, BigDecimal> valoresTotaisPorData(String loginUsuario) {
		int numeroDeDias = 9;
		Calendar dataInicial = Calendar.getInstance();
		dataInicial = DateUtils.truncate(dataInicial, Calendar.DAY_OF_MONTH);
		dataInicial.add(Calendar.DAY_OF_MONTH, numeroDeDias * -1);

		Map<Date, BigDecimal> resultado = criarMapaVazio(numeroDeDias,
				dataInicial);

		List<PedidoFiltro> listaPedidosFiltro = dao
				.consultarTotalPedidoPorData(dataInicial.getTime(),
						loginUsuario);

		for (PedidoFiltro pf : listaPedidosFiltro) {
			resultado.put(pf.getDataHora(), pf.getValorLiquido());
		}

		return resultado;
	}

	private Map<Date, BigDecimal> criarMapaVazio(Integer numeroDeDias,
			Calendar dataInicial) {
		dataInicial = (Calendar) dataInicial.clone();
		Map<Date, BigDecimal> mapaInicial = new TreeMap<>();

		for (int i = 0; i <= numeroDeDias; i++) {
			mapaInicial.put(dataInicial.getTime(), BigDecimal.ZERO);
			dataInicial.add(Calendar.DAY_OF_MONTH, 1);
		}

		return mapaInicial;
	}

	public List<Pedido> consultarPedidosInativos() {
		return dao.consultarPedidosInativos();
	}

	public List<Cliente> consultaTodosClientesAtivos() {
		return servicoCliente.consultarTodos();
	}

	public List<Usuario> consultaTodosUsuariosAtivos() {
		return servicoUsuario.consultarTodos();
	}

	public Produto carregarProdutoPorReferencia(String referencia) {
		return servicoProduto.carregarProdutoPorReferencia(referencia);
	}

	public List<Produto> autoCompleteProduto(String descricaoProduto) {
		return servicoProduto.autoCompleteProduto(descricaoProduto);
	}

}
