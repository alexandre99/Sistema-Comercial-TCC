package br.com.sistemacomercialerp.util;

import com.sun.jna.Native;

public class ApiDllSintegra {

	public static int lerDll(String inscricaoEstadual, String uf) {
		DLLSintegra dllSintegra = (DLLSintegra) Native.loadLibrary(
				"DllInscE32", DLLSintegra.class);

		return dllSintegra.ConsisteInscricaoEstadual(inscricaoEstadual, uf);

	}

}
