package br.com.sistemacomercialerp.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import br.com.sistemacomercialerp.entidade.Cliente;
import br.com.sistemacomercialerp.entidade.Estado;
import br.com.sistemacomercialerp.servico.ClienteServico;
import br.com.sistemacomercialerp.util.LoggerUtil;
import br.com.sistemacomercialerp.util.MensagemPadrao;
import br.com.sistemacomercialerp.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ClienteController implements Serializable {

	private static final long serialVersionUID = 4555160156262150532L;

	@Inject
	private ClienteServico servico;

	private Cliente cliente;

	// Variaveis para controle de tela
	private boolean abaPessoaFisicaVisivel = true;
	private boolean abaPessoaJuridicaVisivel = false;
	private String labelNomeRazaoSocial = "Nome";
	private int abaDadosPrincipais;

	@PostConstruct
	public void inicializar() {
		cliente = new Cliente();
		abaDadosPrincipais = 0;
	}

	public void salvar() {
		try {

			servico.inserir(cliente);
			MensagemPadrao.mensagemSalvar();
			limpar();
			alteraTipoPessoa();
			LoggerUtil.registrarLoggerInfo(getClass(),
					"Registro salvo com sucesso!");
		} catch (Exception e) {
			MensagemPadrao.mensagemErroSalvar(e.getMessage());
			LoggerUtil.registrarLoggerErro(
					getClass(),
					"Erro ao salvar o registro! " + e.getMessage() + " "
							+ e.getCause());
		}
	}

	public void alteraTipoPessoa() {
		if (getCliente().getTipoPessoa() == 'F') {
			abaPessoaFisicaVisivel = true;
			abaPessoaJuridicaVisivel = false;
			labelNomeRazaoSocial = "Nome";
		} else {
			abaPessoaFisicaVisivel = false;
			abaPessoaJuridicaVisivel = true;
			labelNomeRazaoSocial = "Razão Social";
		}
	}

	public void inicializarParametros() {

		if (StringUtils.isNotBlank(FacesUtil.getParameter("idCliente"))) {
			try {
				setCliente(this.servico.consultarId((Long.valueOf(FacesUtil
						.getParameter("idCliente")))));

				alteraTipoPessoa();

			} catch (Exception e) {
				MensagemPadrao.mensagemErroCarregarParametro(e.getMessage());
			}
		}

	}

	public void limpar() {
		inicializar();
	}

	public Estado[] getListaEstado() {
		return Estado.values();
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public boolean isAbaPessoaFisicaVisivel() {
		return abaPessoaFisicaVisivel;
	}

	public void setAbaDadosPrincipais(int abaDadosPrincipais) {
		this.abaDadosPrincipais = abaDadosPrincipais;
	}

	public boolean isAbaPessoaJuridicaVisivel() {
		return abaPessoaJuridicaVisivel;
	}

	public String getLabelNomeRazaoSocial() {
		return labelNomeRazaoSocial;
	}

	public int getAbaDadosPrincipais() {
		return abaDadosPrincipais;
	}

}
