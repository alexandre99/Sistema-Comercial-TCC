package br.com.sistemacomercialerp.util;

import com.sun.jna.Library;

public interface DLLSintegra extends Library {

	public int ConsisteInscricaoEstadual(String ie, String uf);

}
