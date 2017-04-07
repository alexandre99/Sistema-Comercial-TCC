package br.com.sistemacomercialerp.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import br.com.sistemacomercialerp.entidade.Pedido;
import br.com.sistemacomercialerp.filtropesquisa.PedidoFiltro;
import br.com.sistemacomercialerp.util.Dao;

public class PedidoDao extends Dao<Pedido> implements Serializable {

	private static final long serialVersionUID = 5201711097793813512L;

	private EntityManager en;

	@Inject
	public PedidoDao(EntityManager en) {
		this.en = en;
	}

	@Override
	public void inserir(Pedido entidade) {
		en.persist(entidade);
	}

	@Override
	public void atualizar(Pedido entidade) {
		en.merge(entidade);
	}

	public Pedido save(Pedido entidade) {
		return en.merge(entidade);
	}

	@Override
	public List<Pedido> consultarTodos() {
		return null;
	}

	@Override
	public Pedido consultarId(Long id) {
		Pedido pedido = en.find(Pedido.class, id);
		pedido.getPedidoItem().size();
		return pedido;
	}

	public void removerListaItem(Pedido entidade) {
		Query query = en
				.createNativeQuery("DELETE FROM pedidoitem pi WHERE pi.id_pedido = "
						+ entidade.getId());
		query.executeUpdate();
		// en.remove(entidade);
	}

	public List<Pedido> consultarPedidosInativos() {
		String query = "SELECT p FROM Pedido p WHERE p.ativo = FALSE ORDER BY p.dataHora DESC";
		TypedQuery<Pedido> listaPedido = en.createQuery(query, Pedido.class);
		return listaPedido.getResultList();
	}

	public List<Pedido> consultarTodosPedidos(String situacaoPedido) {
		String query = "SELECT p FROM Pedido p WHERE CAST(p.situacao AS text) LIKE :pedidoSituacao AND p.ativo = TRUE "
				+ "ORDER BY p.dataHora DESC";
		TypedQuery<Pedido> listaPedido = en.createQuery(query, Pedido.class);
		listaPedido.setParameter("pedidoSituacao", situacaoPedido);
		return listaPedido.getResultList();
	}

	public List<Pedido> consultarPorFiltro(String filtro, String situacaoPedido) {
		String query = "SELECT p FROM Pedido p "
				+ "WHERE (CAST(p.id AS text) LIKE :filtro OR "
				+ "p.cliente.nome LIKE :filtro OR "
				+ "CAST(p.valorLiquido AS text) LIKE :filtro OR "
				+ "to_char(p.dataHora, 'dd/MM/yyyy') LIKE :filtro) AND "
				+ "CAST(p.situacao AS text) LIKE :pedidoSituacao AND "
				+ "p.ativo = TRUE ORDER BY p.dataHora DESC";
		TypedQuery<Pedido> listaPedido = en.createQuery(query, Pedido.class);
		listaPedido.setParameter("pedidoSituacao", situacaoPedido);
		listaPedido.setParameter("filtro", filtro);
		return listaPedido.getResultList();
	}

	public void limparSessaoJpa() {
		en.clear();
	}

	public List<PedidoFiltro> consultarTotalPedidoPorData(Date dataInicial,
			String loginUsuario) {

		String query = "SELECT NEW br.com.sistemacomercialerp.filtropesquisa.PedidoFiltro"
				+ "(date_trunc('day', p.dataHora), sum(p.valorLiquido)) FROM Pedido p "
				+ "WHERE date_trunc('day', p.dataHora) >= :dataInicial AND p.situacao = 'VENDA'";

		if (loginUsuario != null) {
			query = query + "AND p.vendedor.login = :loginUsuario ";
		}

		query = query + "GROUP BY 1";

		TypedQuery<PedidoFiltro> listaPedidoFiltro = en.createQuery(query,
				PedidoFiltro.class).setParameter("dataInicial", dataInicial,
				TemporalType.TIMESTAMP);
		
		if (loginUsuario != null) {
			listaPedidoFiltro.setParameter("loginUsuario", loginUsuario);
		}

		return listaPedidoFiltro.getResultList();
	}
}
