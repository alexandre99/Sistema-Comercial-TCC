package br.com.sistemacomercialerp.servico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.sistemacomercialerp.dao.UsuarioDao;
import br.com.sistemacomercialerp.entidade.Usuario;
import br.com.sistemacomercialerp.util.Servico;
import br.com.sistemacomercialerp.util.exception.SistemaComercialException;
import br.com.sistemacomercialerp.util.jpa.Transacao;
import br.com.sistemacomercialerp.util.validacao.ValidacaoEmComum;

@Named
public class UsuarioServico implements Serializable, Servico<Usuario> {

	private static final long serialVersionUID = -8405573345679643115L;

	@Inject
	private UsuarioDao dao;

	@Transacao
	public void inserir(Usuario entidade) throws SistemaComercialException {

		if (entidade.getSenha() != null && entidade.getLogin() != null) {
			ValidacaoEmComum.validarLoginESenha(entidade.getLogin(),
					entidade.getSenha());
			if (entidade.getId() != null && entidade.getId() > 0) {
				atualizar(entidade);
			} else {
				dao.inserir(entidade);
			}
		}
	}

	@Override
	public void atualizar(Usuario entidade) {
		dao.atualizar(entidade);
	}

	public List<Usuario> consultaUsuario(String filtro) {
		List<Usuario> listaUsuario = new ArrayList<>();
		if (filtro.equals("")) {
			listaUsuario = consultarTodos();
		} else {
			listaUsuario = consultarPorFiltro(filtro);
		}

		return listaUsuario;
	}

	@Override
	public List<Usuario> consultarTodos() {
		return dao.consultarTodos();
	}

	public List<Usuario> consultarPorFiltro(String filtro) {
		filtro = "%" + filtro + "%".trim();
		return dao.consultarPorFiltro(filtro);
	}

	@Override
	public Usuario consultarId(Long id) {
		return dao.consultarId(id);
	}

	public void verificaLoginJaCadastrado(String filtro)
			throws SistemaComercialException {
		Usuario usuario = dao.verificaLoginCadastrado(filtro);
		if (usuario != null) {
			throw new SistemaComercialException(
					"Já existe um usuário cadastrado com esse login");
		}
	}

	public List<Usuario> consultarUsuariosInativos() {
		return dao.consultarUsuariosInativos();
	}
	
	public Usuario verificarUsuarioPorNomeESenha(String nome, String senha){
		return dao.verificarUsuarioPorNomeESenha(nome, senha);
	}

	public List<Usuario> autoCompleteVendedor(String nomeVendedor) {
		nomeVendedor = "%"+nomeVendedor+"%";
		return dao.autoCompleteVendedor(nomeVendedor);
	}

}
