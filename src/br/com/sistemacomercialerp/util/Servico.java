package br.com.sistemacomercialerp.util;

import java.util.List;

import br.com.sistemacomercialerp.util.exception.SistemaComercialException;

public interface Servico<T> {

	void inserir(T entidade) throws SistemaComercialException;

	void atualizar(T entidade);

	List<T> consultarTodos();

	T consultarId(Long id);

}
