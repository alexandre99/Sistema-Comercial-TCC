package br.com.sistemacomercialerp.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import br.com.sistemacomercialerp.entidade.Usuario;
import br.com.sistemacomercialerp.servico.UsuarioServico;
import br.com.sistemacomercialerp.util.LoggerUtil;
import br.com.sistemacomercialerp.util.MensagemPadrao;
import br.com.sistemacomercialerp.util.jsf.FacesUtil;

@Named
@ViewScoped
public class UsuarioController implements Serializable {

	private static final long serialVersionUID = 8461318541832942333L;

	@Inject
	private UsuarioServico servico;

	private Usuario novoUsuario;
	private int abaDadosPrincipais;

	@PostConstruct
	public void inicializar() {
		novoUsuario = new Usuario();
		abaDadosPrincipais = 0;
	}

	public void inicializarParametros() {

		if (StringUtils.isNotBlank(FacesUtil.getParameter("idUsuario"))) {
			try {
				setNovoUsuario(this.servico.consultarId((Long.valueOf(FacesUtil
						.getParameter("idUsuario")))));
			} catch (Exception e) {
				MensagemPadrao.mensagemErroCarregarParametro(e.getMessage());
			}
		}

	}

	public void salvar() {
		try {
			servico.inserir(novoUsuario);
			MensagemPadrao.mensagemSalvar();
			limpar();
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

	public void limpar() {
		inicializar();
	}

	public Usuario getNovoUsuario() {
		return novoUsuario;
	}

	public void setNovoUsuario(Usuario novoUsuario) {
		this.novoUsuario = novoUsuario;
	}

	public int getAbaDadosPrincipais() {
		return abaDadosPrincipais;
	}

	public void setAbaDadosPrincipais(int abaDadosPrincipais) {
		this.abaDadosPrincipais = abaDadosPrincipais;
	}

}
