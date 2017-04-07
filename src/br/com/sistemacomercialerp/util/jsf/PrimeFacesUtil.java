package br.com.sistemacomercialerp.util.jsf;

import org.primefaces.context.RequestContext;

public class PrimeFacesUtil {

	public static void executarFuncaoJavascript(String funcao) {
		RequestContext.getCurrentInstance().execute(funcao);
	}
	
	public static void executarUpdate(String form){
		RequestContext.getCurrentInstance().update(form);
	}

}
