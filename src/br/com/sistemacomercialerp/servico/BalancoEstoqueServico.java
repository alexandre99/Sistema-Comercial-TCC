package br.com.sistemacomercialerp.servico;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.sistemacomercialerp.dao.BalancoEstoqueDao;
import br.com.sistemacomercialerp.entidade.BalancoEstoque;
import br.com.sistemacomercialerp.entidade.Cliente;
import br.com.sistemacomercialerp.entidade.Produto;
import br.com.sistemacomercialerp.util.Servico;
import br.com.sistemacomercialerp.util.exception.SistemaComercialException;
import br.com.sistemacomercialerp.util.jpa.Transacao;

public class BalancoEstoqueServico implements Serializable,
		Servico<BalancoEstoque> {

	private static final long serialVersionUID = 8190488047853302744L;
	@Inject
	private BalancoEstoqueDao dao;

	@Inject
	private ClienteServico servicoCliente;

	@Inject
	private ProdutoServico servicoProduto;

	@Override
	@Transacao
	public void inserir(BalancoEstoque entidade)
			throws SistemaComercialException {

		if (entidade.getId() != null && entidade.getId() > 0) {
			atualizar(entidade);
		} else {
			dao.inserir(entidade);
		}
	}

	@Override
	public void atualizar(BalancoEstoque entidade) {
		dao.atualizar(entidade);
	}

	@Override
	public List<BalancoEstoque> consultarTodos() {
		return dao.consultarTodos();
	}

	@Transacao
	public BalancoEstoque save(BalancoEstoque entidade) {
		entidade = dao.save(entidade);

		servicoProduto.atualizaEstoque(entidade.getItens());
		return entidade;
	}

	@Transacao
	public void cancelarBalancoEstoque(BalancoEstoque entidade)
			throws SistemaComercialException {

		if (entidade == null) {
			throw new SistemaComercialException(
					"Selecione um balanço de estoque para fazer o cancelamento");
		}
		if (entidade.isAtivo()) {
			entidade.setAtivo(false);
			BalancoEstoque balancoEstoque = dao.consultarId(entidade.getId());
			balancoEstoque.setAtivo(entidade.isAtivo());
			atualizar(balancoEstoque);
			servicoProduto.estornarEstoqueBalancoEstoque(balancoEstoque
					.getItens());
		} else {
			throw new SistemaComercialException(
					"Balanço de estoque selecionado já está cancelado!");
		}
	}

	public List<BalancoEstoque> consultarBalancoEstoqueInativos() {
		return dao.consultarBalancoEstoqueInativos();
	}

	public List<Cliente> consultaTodosClientesAtivos() {
		return servicoCliente.consultarTodos();
	}

	public Produto carregarProdutoPorReferencia(String referencia) {
		return servicoProduto.carregarProdutoPorReferencia(referencia);
	}

	public List<Produto> autoCompleteProduto(String descricaoProduto) {
		return servicoProduto.autoCompleteProduto(descricaoProduto);
	}

	@Override
	public BalancoEstoque consultarId(Long id) {
		return dao.consultarId(id);
		// dao.limparSessaoJpa();
		// return balancoEstoque;
	}
}
