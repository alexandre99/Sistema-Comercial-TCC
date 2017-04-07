package br.com.sistemacomercialerp.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.sistemacomercialerp.entidade.Usuario;
import br.com.sistemacomercialerp.util.Dao;

public class UsuarioDao extends Dao<Usuario> implements Serializable {

	private static final long serialVersionUID = 3768485152971730079L;

	EntityManager en;

	@Inject
	public UsuarioDao(EntityManager en) {
		this.en = en;
	}

	@Override
	public void inserir(Usuario entidade) {
		en.persist(entidade);
	}

	@Override
	public void atualizar(Usuario entidade) {
		en.merge(entidade);
	}

	public List<Usuario> consultarPorFiltro(String filtro) {
		String query = "SELECT u FROM Usuario u "
				+ "WHERE "
				+ "(u.nome LIKE :filtro OR u.login LIKE :filtro) AND u.ativo = TRUE "
				+ "ORDER BY u.nome ASC";
		TypedQuery<Usuario> listaUsuario = en.createQuery(query, Usuario.class);
		listaUsuario.setParameter("filtro", filtro);
		return listaUsuario.getResultList();
	}

	@Override
	public List<Usuario> consultarTodos() {
		String query = "SELECT u FROM Usuario u WHERE u.ativo = TRUE "
				+ "ORDER BY u.nome ASC";
		TypedQuery<Usuario> listaUsuario = en.createQuery(query, Usuario.class);
		return listaUsuario.getResultList();
	}

	@Override
	public Usuario consultarId(Long id) {
		return en.find(Usuario.class, id);
	}

	public Usuario verificaLoginCadastrado(String filtro) {
		try {
			String query = "SELECT u FROM Usuario u "
					+ "WHERE u.usuario_login LIKE :filtro ";
			return en.createQuery(query, Usuario.class)
					.setParameter("filtro", filtro).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public List<Usuario> consultarUsuariosInativos() {
		String query = "SELECT u FROM Usuario u ORDER BY u.nome ASC";
		TypedQuery<Usuario> listaUsuario = en.createQuery(query, Usuario.class);
		return listaUsuario.getResultList();
	}

	public Usuario verificarUsuarioPorNomeESenha(String nome, String senha) {

		try {
			String query = "SELECT u FROM Usuario u "
					+ "WHERE u.login = :nome AND u.senha = :senhaUsuario ";
			return en.createQuery(query, Usuario.class)
					.setParameter("nome", nome)
					.setParameter("senhaUsuario", senha).getSingleResult();
		} catch (Exception e) {
			return null;
		}

	}

	public List<Usuario> autoCompleteVendedor(String nomeVendedor) {
		String query = "SELECT u FROM Usuario u WHERE u.nome LIKE :filtro";
		TypedQuery<Usuario> listaVendedor = en
				.createQuery(query, Usuario.class).setParameter("filtro",
						nomeVendedor);
		return listaVendedor.getResultList();
	}
}
