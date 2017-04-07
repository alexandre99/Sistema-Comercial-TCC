package br.com.sistemacomercialerp.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.sistemacomercialerp.entidade.Empresa;
import br.com.sistemacomercialerp.util.Dao;

public class EmpresaDao extends Dao<Empresa> implements Serializable {

	private static final long serialVersionUID = -6170884361955543973L;

	private EntityManager en;

	@Inject
	public EmpresaDao(EntityManager en) {
		this.en = en;
	}

	@Override
	public void inserir(Empresa entidade) {
		en.persist(entidade);
	}

	@Override
	public void atualizar(Empresa entidade) {
		en.merge(entidade);
	}

	@Override
	public List<Empresa> consultarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Empresa consultarId(Long id) {
		Empresa empresa = en.find(Empresa.class, id);
		return empresa;
	}

}
