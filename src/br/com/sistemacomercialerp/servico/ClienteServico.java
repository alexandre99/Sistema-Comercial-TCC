package br.com.sistemacomercialerp.servico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.sistemacomercialerp.dao.ClienteDao;
import br.com.sistemacomercialerp.entidade.Cliente;
import br.com.sistemacomercialerp.util.Servico;
import br.com.sistemacomercialerp.util.exception.SistemaComercialException;
import br.com.sistemacomercialerp.util.jpa.Transacao;
import br.com.sistemacomercialerp.util.validacao.ValidacaoEmComum;

public class ClienteServico implements Serializable, Servico<Cliente> {

	private static final long serialVersionUID = -8405573345679643115L;

	@Inject
	private ClienteDao dao;

	@Inject
	private Cliente clienteValidacao;

	@Transacao
	public void inserir(Cliente entidade) throws SistemaComercialException {
		if (entidade.getDataNascimento() != null) {
			clienteValidacao.validarDataDeNascimento(entidade
					.getDataNascimento());
		}
		if (!"".equals(entidade.getInscricaoEstadual())
				&& !"".equals(entidade.getEstado().getDescricao())) {
			ValidacaoEmComum.validarIncricaoEstadual(entidade
					.getInscricaoEstadual(), entidade.getEstado()
					.getDescricao());
		}

		verificaCpfCnpjCadastrado(entidade);

		if (entidade.getId() != null && entidade.getId() > 0) {
			atualizar(entidade);
		} else {
			entidade.setId(null);
			dao.inserir(entidade);
		}

	}

	@Override
	public void atualizar(Cliente entidade) {
		dao.atualizar(entidade);
	}

	public List<Cliente> consultaCliente(String filtro) {
		List<Cliente> listaCliente = new ArrayList<>();

		if (filtro.equals("")) {
			listaCliente = consultarTodos();
		} else {
			listaCliente = consultarPorFiltro(filtro);
		}

		return listaCliente;
	}

	@Override
	public List<Cliente> consultarTodos() {
		return dao.consultarTodos();
	}

	public List<Cliente> consultarPorFiltro(String filtro) {
		filtro = "%" + filtro + "%".trim();
		return dao.consultarPorFiltro(filtro);
	}

	@Override
	public Cliente consultarId(Long id) {
		Cliente cliente = dao.consultarId(id);
		return cliente;
	}

	public List<Cliente> consultarClientesInativos() {
		return dao.consultarClientesInativos();

	}

	public void verificaCpfCnpjCadastrado(Cliente entidade)
			throws SistemaComercialException {

		String filtro;

		if (entidade.getCpf() != null) {
			filtro = entidade.getCpf();
		} else {
			filtro = entidade.getCnpj();
		}

		if (entidade.getId() == null) {
			entidade.setId(0l);
		}

		Cliente cliente = dao.verificaCpfCnpjCadastrado(filtro,
				entidade.getId());
		if (cliente != null) {
			throw new SistemaComercialException(
					"Já existe um cliente cadastrado com esse CPF ou CNPJ");
		}
		// }
	}

	public List<Cliente> autoCompleteCliente(String nomeCliente) {
		nomeCliente = "%" + nomeCliente + "%";
		return dao.autoCompleteCliente(nomeCliente);
	}
}
