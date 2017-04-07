package br.com.sistemacomercialerp.controller;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class UsuarioLoginController implements Serializable {

	private static final long serialVersionUID = 5402874335364601626L;

	private String nome;
	private Date dataLogin;
	private static String usuarioLogado;

	public boolean isLogado() {
		return nome != null;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public static String getUsuarioLogado() {
		return usuarioLogado;
	}

	public static void setUsuarioLogado(String usuarioLogado) {
		UsuarioLoginController.usuarioLogado = usuarioLogado;
	}

	public Date getDataLogin() {
		return dataLogin;
	}

	public void setDataLogin(Date dataLogin) {
		this.dataLogin = dataLogin;
	}

}
