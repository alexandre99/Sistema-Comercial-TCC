package br.com.sistemacomercialerp.servico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.sistemacomercialerp.dao.ProdutoDao;
import br.com.sistemacomercialerp.entidade.BalancoEstoqueProdutos;
import br.com.sistemacomercialerp.entidade.PedidoItem;
import br.com.sistemacomercialerp.entidade.Produto;
import br.com.sistemacomercialerp.util.Servico;
import br.com.sistemacomercialerp.util.exception.SistemaComercialException;
import br.com.sistemacomercialerp.util.jpa.Transacao;
import br.com.sistemacomercialerp.util.validacao.ValidacaoEmComum;

public class ProdutoServico implements Servico<Produto>, Serializable {

	private static final long serialVersionUID = 5595855320634369435L;

	@Inject
	private ProdutoDao dao;

	@Transacao
	public void inserir(Produto entidade) throws SistemaComercialException {

		verificaReferenciaCadastrada(entidade);

		if (entidade.getId() != null && entidade.getId() > 0) {
			atualizar(entidade);
		} else {
			entidade.setId(null);
			dao.inserir(entidade);
		}

	}

	@Transacao
	public void atualizar(Produto entidade) {
		dao.atualizar(entidade);

	}

	@Override
	public List<Produto> consultarTodos() {
		return dao.consultarTodos();
	}

	@Override
	public Produto consultarId(Long id) {
		Produto produto = dao.consultarId(id);
		return produto;
	}

	public void verificaReferenciaCadastrada(Produto produto)
			throws SistemaComercialException {

		if (produto.getId() == null) {
			produto.setId(0l);
		}

		Produto produtoAux = dao.verificaReferenciaCadastrada(
				produto.getcodigoBarras(), produto.getId());

		if (produtoAux != null) {
			throw new SistemaComercialException(
					"Já existe um produto cadastrado com essa referência");
		}

	}

	public List<Produto> consultarProdutosInativos() {
		return dao.consultarProdutosInativos();
	}

	public List<Produto> consultarPorFiltro(String filtro) {
		filtro = "%" + filtro + "%".trim();

		for (int i = 0; i < filtro.length(); i++) {
			if (filtro.charAt(i) == ',') {
				filtro = ValidacaoEmComum.trocarPontoPorEspaco(filtro);
			}
		}

		filtro = ValidacaoEmComum.trocarVirgulaPorPonto(filtro);

		return dao.consultarPorFiltro(filtro);
	}

	public List<Produto> consultaProduto(String filtro) {
		List<Produto> listaProduto = new ArrayList<>();

		if (filtro.equals("")) {
			listaProduto = consultarTodos();
		} else {
			listaProduto = consultarPorFiltro(filtro);
		}

		return listaProduto;
	}

	public Produto carregarProdutoPorReferencia(String referencia) {
		return dao.carregarProdutoPorReferencia(referencia);
	}

	public void baixarEstoqueItens(List<PedidoItem> pedidoItem) {

		for (PedidoItem p : pedidoItem) {
			p.getProduto().setQuantidade(
					p.getProduto().getQuantidade() - p.getQuantidade());
			dao.atualizar(p.getProduto());
		}
	}

	public List<Produto> autoCompleteProduto(String descricaoProduto) {
		descricaoProduto = "%" + descricaoProduto + "%";
		return dao.autoCompleteProduto(descricaoProduto);
	}

	public List<PedidoItem> estornarEstoque(List<PedidoItem> pedidoItem) {
		List<PedidoItem> listaPedidoItem = new ArrayList<>();
		for (PedidoItem p : pedidoItem) {
			p.getProduto().setQuantidade(
					p.getQuantidade() + p.getProduto().getQuantidade());

			dao.atualizar(p.getProduto());
			listaPedidoItem.add(p);
		}
		return listaPedidoItem;
	}
	
	public List<BalancoEstoqueProdutos> atualizaEstoque(List<BalancoEstoqueProdutos> balancoProduto) {
		List<BalancoEstoqueProdutos> listaBalancoItem = new ArrayList<>();
		for (BalancoEstoqueProdutos b : balancoProduto) {
			Produto p = dao.consultarId(b.getProduto().getId());
//			if(b.getTipoBalanco() == 1){
			p.setQuantidade(b.getVolumeAjustado());
//		}else{
//			p.setQuantidade(
//					p.getQuantidade() - b.getVolumeAjuste());
//		}
			dao.atualizar(p);

			listaBalancoItem.add(b);
		}
		return listaBalancoItem;
	}

	
	public List<BalancoEstoqueProdutos> estornarEstoqueBalancoEstoque(List<BalancoEstoqueProdutos> balancoProduto) {
		List<BalancoEstoqueProdutos> listaBalancoItem = new ArrayList<>();
		for (BalancoEstoqueProdutos b : balancoProduto) {
			Produto p = dao.consultarId(b.getProduto().getId());
			if(b.getTipoBalanco() == 1){
			p.setQuantidade(
					p.getQuantidade() - b.getVolumeAjuste());
		}else{
			p.setQuantidade(
					p.getQuantidade() + b.getVolumeAjuste());
		}
			dao.atualizar(b.getProduto());

			listaBalancoItem.add(b);
		}
		return listaBalancoItem;
	}

}
