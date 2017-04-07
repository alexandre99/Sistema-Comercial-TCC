package br.com.sistemacomercialerp.servico;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.sistemacomercialerp.dao.EmpresaDao;
import br.com.sistemacomercialerp.entidade.Empresa;
import br.com.sistemacomercialerp.util.Servico;
import br.com.sistemacomercialerp.util.exception.SistemaComercialException;
import br.com.sistemacomercialerp.util.jpa.Transacao;
import br.com.sistemacomercialerp.util.validacao.ValidacaoEmComum;

public class EmpresaServico implements Servico<Empresa>, Serializable {

	private static final long serialVersionUID = 1241453700734744260L;

	@Inject
	private EmpresaDao dao;

	@Transacao
	public void inserir(Empresa entidade) throws SistemaComercialException {

		if (!"".equals(entidade.getInscricaoEstadual())
				&& !"".equals(entidade.getEstado().getDescricao())) {
			ValidacaoEmComum.validarIncricaoEstadual(entidade
					.getInscricaoEstadual(), entidade.getEstado()
					.getDescricao());
		}

		if (entidade.getId() != null && entidade.getId() > 0) {
			atualizar(entidade);
		} else {
			dao.inserir(entidade);
		}
	}

	@Override
	public void atualizar(Empresa entidade) {
		dao.atualizar(entidade);
	}

	@Override
	public List<Empresa> consultarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Empresa consultarId(Long id) {
		Empresa empresa = dao.consultarId(id);
		return empresa;
	}

}
