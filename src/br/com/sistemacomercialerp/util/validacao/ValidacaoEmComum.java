package br.com.sistemacomercialerp.util.validacao;

import br.com.sistemacomercialerp.util.ApiDllSintegra;
import br.com.sistemacomercialerp.util.exception.SistemaComercialException;

public class ValidacaoEmComum {

	public static void validarIncricaoEstadual(String inscricaoEstadual,
			String uf) throws SistemaComercialException {
		int resultado = ApiDllSintegra.lerDll(
				removerCaractere(inscricaoEstadual), uf);
		if (resultado != 0) {
			throw new SistemaComercialException("Inscrição estadual inválida");
		}
	}

	public static String removerCaractere(String palavra) {

		palavra = palavra.replace(".", "");
		palavra = palavra.replace("-", "");
		return palavra;
	}

	public static String trocarPontoPorEspaco(String texto) {
		texto = texto.replace(".", "");
		return texto;
	}

	public static String trocarVirgulaPorPonto(String texto) {
		texto = texto.replace(",", ".");
		return texto;
	}

	public static String trocarPontoPorVirgula(String texto) {
		texto = texto.replace(".", ",");
		return texto;
	}
	
	public static void validarLoginESenha(String login, String senha) throws SistemaComercialException {
		if(senha.length() > 10 && login.length() > 15)
			throw new SistemaComercialException("O login deve conter até 15 caracteres e a senha deve conter até 10 caracteres");
		else if(senha.length() > 10)
			throw new SistemaComercialException("A senha deve conter até 10 caracteres");
		else if(login.length() > 10)
			throw new SistemaComercialException("O login deve conter até 15 caracteres");
	
	}
	
}
