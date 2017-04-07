package br.com.sistemacomercialerp.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.sistemacomercialerp.entidade.Cliente;
import br.com.sistemacomercialerp.util.Dao;

public class ClienteDao extends Dao<Cliente> implements Serializable {

	private static final long serialVersionUID = 7182607155774265627L;

	EntityManager en;

	@Inject
	public ClienteDao(EntityManager en) {
		this.en = en;
	}

	@Override
	public void inserir(Cliente entidade) {
		en.persist(entidade);
	}

	@Override
	public void atualizar(Cliente entidade) {
		en.merge(entidade);
	}

	@Override
	public List<Cliente> consultarTodos() {
		String query = "SELECT c FROM Cliente c WHERE " + "c.ativo = TRUE "
				+ "ORDER BY c.nome ASC";
		TypedQuery<Cliente> listaCliente = en.createQuery(query, Cliente.class);
		return listaCliente.getResultList();
	}

	public List<Cliente> consultarPorFiltro(String filtro) {
		String query = "SELECT c FROM Cliente c " + "WHERE "
				+ "(c.nome LIKE :filtro OR " + "c.cpf LIKE :filtro OR "
				+ "c.cnpj LIKE :filtro OR " + "c.telefone LIKE :filtro OR "
				+ "c.celular LIKE :filtro OR " + "c.cidade LIKE :filtro OR "
				+ "c.estado LIKE :filtro) AND " + "c.ativo = TRUE "
				+ "ORDER BY c.nome ASC";
		TypedQuery<Cliente> listaCliente = en.createQuery(query, Cliente.class);
		listaCliente.setParameter("filtro", filtro);
		return listaCliente.getResultList();
	}

	@Override
	public Cliente consultarId(Long id) {
		return en.find(Cliente.class, id);
	}

	public List<Cliente> consultarClientesInativos() {
		String query = "SELECT c FROM Cliente c ORDER BY c.nome ASC";
		TypedQuery<Cliente> listaCliente = en.createQuery(query, Cliente.class);
		return listaCliente.getResultList();
	}

	public Cliente verificaCpfCnpjCadastrado(String filtroCpfCnpj,
			Long filtroIdCliente) {
		try {
			String query = "SELECT c FROM Cliente c "
					+ "WHERE c.id <> :filtroIdCliente AND ";
			if (filtroCpfCnpj.length() == 18) {
				query = query.concat("c.cnpj = :filtroCpfCnpj");
			} else {
				query = query.concat("c.cpf = :filtroCpfCnpj");
			}
			return en.createQuery(query, Cliente.class)
					.setParameter("filtroCpfCnpj", filtroCpfCnpj)
					.setParameter("filtroIdCliente", filtroIdCliente)
					.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public List<Cliente> autoCompleteCliente(String nomeCliente) {
		String query = "SELECT c FROM Cliente c WHERE c.nome LIKE :filtro";
		TypedQuery<Cliente> listaCliente = en.createQuery(query, Cliente.class)
				.setParameter("filtro", nomeCliente);
		return listaCliente.getResultList();
	}

}
