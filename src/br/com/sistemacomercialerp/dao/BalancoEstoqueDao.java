package br.com.sistemacomercialerp.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.sistemacomercialerp.entidade.BalancoEstoque;
import br.com.sistemacomercialerp.util.Dao;

public class BalancoEstoqueDao extends Dao<BalancoEstoque> implements
		Serializable {

	private static final long serialVersionUID = -8924808612069425426L;
	private EntityManager en;

	@Inject
	public BalancoEstoqueDao(EntityManager en) {
		this.en = en;
	}

	@Override
	public void inserir(BalancoEstoque entidade) {
		en.persist(entidade);
	}

	public BalancoEstoque save(BalancoEstoque entidade) {
		return en.merge(entidade);
	}

	@Override
	public void atualizar(BalancoEstoque entidade) {
		en.merge(entidade);
	}

	@Override
	public List<BalancoEstoque> consultarTodos() {
		String query = "SELECT b FROM BalancoEstoque b where b.ativo = TRUE "
				+ "ORDER BY b.dataHora DESC";
		TypedQuery<BalancoEstoque> listaBalancoEstoque = en.createQuery(query,
				BalancoEstoque.class);
		return listaBalancoEstoque.getResultList();
	}

	@Override
	public BalancoEstoque consultarId(Long id) {
		BalancoEstoque balanco = en.find(BalancoEstoque.class, id);
		balanco.getItens().size();
		return balanco;
	}

	public void removerListaItem(BalancoEstoque entidade) {
		Query query = en
				.createNativeQuery("DELETE FROM balancoEstoqueProdutos bep WHERE bep.id_balanco_estoque = "
						+ entidade.getId());
		query.executeUpdate();
		// en.remove(entidade);
	}

	public List<BalancoEstoque> consultarBalancoEstoqueInativos() {
		String query = "SELECT b FROM BalancoEstoque b WHERE b.ativo = FALSE";
		TypedQuery<BalancoEstoque> listaBalancoEstoque = en.createQuery(query,
				BalancoEstoque.class);
		return listaBalancoEstoque.getResultList();
	}

	public void limparSessaoJpa() {
		en.clear();
	}

}
