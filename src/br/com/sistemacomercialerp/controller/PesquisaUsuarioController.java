package br.com.sistemacomercialerp.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sistemacomercialerp.entidade.Usuario;
import br.com.sistemacomercialerp.servico.UsuarioServico;
import br.com.sistemacomercialerp.util.LoggerUtil;

@Named
@ViewScoped
public class PesquisaUsuarioController implements Serializable {

	private static final long serialVersionUID = 400247511763058012L;

	@Inject
	private UsuarioServico servico;

	private Usuario usuarioSelecionado;
	private List<Usuario> listaUsuario;

	// Variaveis de filtro
	private String filtro;
	private boolean usuariosInativos;

	@PostConstruct
	public void inicializar() {
		usuarioSelecionado = new Usuario();
		usuariosInativos = false;
		listaUsuario = new ArrayList<>();
	}

	public void pesquisarUsuario() {
		try {

			if (usuariosInativos) {
				usuariosInativos = false;
			}

			listaUsuario = servico.consultaUsuario(filtro);
			usuarioSelecionado = new Usuario();
		} catch (Exception e) {
			LoggerUtil.registrarLoggerErro(getClass(), "Erro " + e.getMessage()
					+ " " + e.getCause());
		}
	}

	public void pesquisarUsuariosInativos() {
		try {
			if (!"".equals(filtro)) {
				filtro = "";
			}
			listaUsuario = servico.consultarUsuariosInativos();
			usuarioSelecionado = new Usuario();
		} catch (Exception e) {
			LoggerUtil.registrarLoggerErro(getClass(), "Erro " + e.getMessage()
					+ " " + e.getCause());
		}

	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public boolean isUsuariosInativos() {
		return usuariosInativos;
	}

	public void setUsuariosInativos(boolean usuariosInativos) {
		this.usuariosInativos = usuariosInativos;
	}

}
