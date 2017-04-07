package br.com.sistemacomercialerp.util;

import java.util.List;

public abstract class Dao<T> {

	public abstract void inserir(T entidade);

	public abstract void atualizar(T entidade);

	public abstract List<T> consultarTodos();

	public abstract T consultarId(Long id);

}
