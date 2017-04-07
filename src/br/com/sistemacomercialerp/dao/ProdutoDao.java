package br.com.sistemacomercialerp.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.sistemacomercialerp.entidade.Produto;
import br.com.sistemacomercialerp.util.Dao;

public class ProdutoDao extends Dao<Produto> implements Serializable {
	private static final long serialVersionUID = 8702270149503827224L;

	private EntityManager en;

	@Inject
	public ProdutoDao(EntityManager en) {
		this.en = en;
	}

	@Override
	public void inserir(Produto entidade) {
		en.persist(entidade);
	}

	@Override
	public void atualizar(Produto entidade) {
		en.merge(entidade);
	}

	@Override
	public List<Produto> consultarTodos() {
		String query = "SELECT p FROM Produto p WHERE p.ativo = TRUE "
				+ "ORDER BY p.descricao";
		TypedQuery<Produto> listaProduto = en.createQuery(query, Produto.class);
		return listaProduto.getResultList();
	}

	@Override
	public Produto consultarId(Long id) {
		Produto produto = en.find(Produto.class, id);
		return produto;
	}

	public Produto verificaReferenciaCadastrada(String filtroReferencia,
			Long filtroCodigoProduto) {
		try {
			String query = "SELECT p FROM Produto p WHERE p.id <> :filtroCodigoProduto "
					+ "AND p.codigoBarras = :filtroReferencia";
			return en.createQuery(query, Produto.class)
					.setParameter("filtroReferencia", filtroReferencia)
					.setParameter("filtroCodigoProduto", filtroCodigoProduto)
					.getSingleResult();
		} catch (Exception e) {
			return null;
		}

	}

	public List<Produto> consultarPorFiltro(String filtro) {
		String query = "SELECT p FROM Produto p "
				+ "WHERE (p.codigoBarras LIKE :filtro OR p.descricao LIKE :filtro OR "
				+ "p.unidadeVenda LIKE :filtro OR CAST(p.precoCusto AS text) LIKE :filtro OR "
				+ "CAST(p.precoVenda AS text) LIKE :filtro) AND p.ativo = TRUE "
				+ "ORDER BY p.descricao";
		TypedQuery<Produto> listaProduto = en.createQuery(query, Produto.class);
		listaProduto.setParameter("filtro", filtro);
		return listaProduto.getResultList();
	}

	public List<Produto> consultarProdutosInativos() {
		String query = "SELECT p FROM Produto p ORDER BY p.descricao";
		TypedQuery<Produto> listaProduto = en.createQuery(query, Produto.class);
		return listaProduto.getResultList();
	}

	public Produto carregarProdutoPorReferencia(String referencia) {
		try {

			String query = "SELECT p FROM Produto p "
					+ "WHERE p.codigoBarras = :filtro AND p.ativo = TRUE";
			return en.createQuery(query, Produto.class)
					.setParameter("filtro", referencia).getSingleResult();
		} catch (Exception e) {
			return null;
		}

	}

	public List<Produto> autoCompleteProduto(String descricaoProduto) {
		String query = "SELECT p FROM Produto p WHERE p.descricao LIKE :filtro AND p.ativo = TRUE";
		TypedQuery<Produto> listaProduto = en.createQuery(query, Produto.class)
				.setParameter("filtro", descricaoProduto);
		return listaProduto.getResultList();
	}

	public void flush() {
		en.flush();
	}
}
