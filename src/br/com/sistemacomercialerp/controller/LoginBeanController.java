package br.com.sistemacomercialerp.controller;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sistemacomercialerp.entidade.Usuario;
import br.com.sistemacomercialerp.servico.UsuarioServico;

@Named
@RequestScoped
public class LoginBeanController implements Serializable {

	private static final long serialVersionUID = 7393589934365880381L;

	@Inject
	private UsuarioLoginController usuarioLogin;

	@Inject
	FacesContext context;

	@Inject
	private UsuarioServico servicoUsuario;

	private String nomeUsuario;
	private String senha;

	public String login() {
		Usuario usuario = servicoUsuario.verificarUsuarioPorNomeESenha(
				this.nomeUsuario.toUpperCase(), this.senha);

		if (usuario != null) {
			this.usuarioLogin.setNome(this.nomeUsuario.toUpperCase());
			this.usuarioLogin.setDataLogin(new Date());
			UsuarioLoginController.setUsuarioLogado(this.nomeUsuario
					.toUpperCase());
			return "/home.jsf?faces-redirect=true";

		} else {
			FacesMessage mensagem = new FacesMessage("Usuário/senha inválidos!");
			mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, mensagem);
		}
		return null;
	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext()
				.invalidateSession();
		return "/login.jsf?faces-redirect=true";
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
