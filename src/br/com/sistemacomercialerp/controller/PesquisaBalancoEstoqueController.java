package br.com.sistemacomercialerp.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sistemacomercialerp.entidade.BalancoEstoque;
import br.com.sistemacomercialerp.entidade.BalancoEstoqueProdutos;
import br.com.sistemacomercialerp.servico.BalancoEstoqueServico;
import br.com.sistemacomercialerp.util.LoggerUtil;
import br.com.sistemacomercialerp.util.MensagemPadrao;
import br.com.sistemacomercialerp.util.exception.SistemaComercialException;
import br.com.sistemacomercialerp.util.jsf.PrimeFacesUtil;

@Named
@ViewScoped
public class PesquisaBalancoEstoqueController implements Serializable {

	private static final long serialVersionUID = 3391479365306582373L;

	@Inject
	private BalancoEstoqueServico servico;

	private BalancoEstoque balancoEstoqueSelecionado;
	private List<BalancoEstoque> listaBalancoEstoque;
	private boolean balancoEstoqueInativos;
	private List<BalancoEstoqueProdutos> listaItensBalanco;

	@PostConstruct
	public void inicializar() {
		balancoEstoqueSelecionado = new BalancoEstoque();
		balancoEstoqueInativos = false;
		listaBalancoEstoque = new ArrayList<>();
	}

	public void pesquisarBalancoEstoque() {
		try {

			if (balancoEstoqueInativos) {
				balancoEstoqueInativos = false;
			}
			listaBalancoEstoque = servico.consultarTodos();
			balancoEstoqueSelecionado = new BalancoEstoque();
		} catch (Exception e) {
			LoggerUtil.registrarLoggerErro(getClass(), "Erro " + e.getMessage()
					+ " " + e.getCause());
		}
	}

	public void pesquisarPedidosInativos() {
		try {
			listaBalancoEstoque = servico.consultarBalancoEstoqueInativos();
			balancoEstoqueSelecionado = new BalancoEstoque();
		} catch (Exception e) {
			LoggerUtil.registrarLoggerErro(getClass(), "Erro " + e.getMessage()
					+ " " + e.getCause());
		}

	}

	public void cancelarBalancoEstoque() {
		try {
			servico.cancelarBalancoEstoque(balancoEstoqueSelecionado);
			posCancelamentoBalancoEstoque();
		} catch (Exception e) {
			MensagemPadrao.mensagemErroValidacao(e.getMessage());
			LoggerUtil.registrarLoggerErro(getClass(), "Erro " + e.getMessage()
					+ " " + e.getCause());
		}
	}

	public void detalharBalancoEstoque() {
		try {
			if (balancoEstoqueSelecionado == null) {
				throw new SistemaComercialException(
						"Informe um balanço de estoque para fazer o detalhamento");
			}

			balancoEstoqueSelecionado = servico
					.consultarId(balancoEstoqueSelecionado.getId());

			listaItensBalanco = balancoEstoqueSelecionado.getItens();

			PrimeFacesUtil
					.executarFuncaoJavascript("PF('dialogDetalhamentoBalanco').show()");

		} catch (Exception e) {
			MensagemPadrao.mensagemErroValidacao(e.getMessage());
		}
	}

	public void posCancelamentoBalancoEstoque() {
		listaBalancoEstoque.remove(balancoEstoqueSelecionado);
		balancoEstoqueSelecionado = new BalancoEstoque();

		MensagemPadrao.mensagemCancelamentoBalancoEstoque();
	}

	public BalancoEstoqueServico getServico() {
		return servico;
	}

	public void setServico(BalancoEstoqueServico servico) {
		this.servico = servico;
	}

	public BalancoEstoque getBalancoEstoqueSelecionado() {
		return balancoEstoqueSelecionado;
	}

	public void setBalancoEstoqueSelecionado(
			BalancoEstoque balancoEstoqueSelecionado) {
		this.balancoEstoqueSelecionado = balancoEstoqueSelecionado;
	}

	public List<BalancoEstoque> getListaBalancoEstoque() {
		return listaBalancoEstoque;
	}

	public void setListaBalancoEstoque(List<BalancoEstoque> listaBalancoEstoque) {
		this.listaBalancoEstoque = listaBalancoEstoque;
	}

	public boolean isBalancoEstoqueInativos() {
		return balancoEstoqueInativos;
	}

	public void setBalancoEstoqueInativos(boolean balancoEstoqueInativos) {
		this.balancoEstoqueInativos = balancoEstoqueInativos;
	}

	public List<BalancoEstoqueProdutos> getListaItensBalanco() {
		return listaItensBalanco;
	}

	public void setListaItensBalanco(
			List<BalancoEstoqueProdutos> listaItensBalanco) {
		this.listaItensBalanco = listaItensBalanco;
	}

}
