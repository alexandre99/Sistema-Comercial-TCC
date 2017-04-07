package br.com.sistemacomercialerp.util.jsf;

import javax.faces.context.FacesContext;

public class FacesUtil {
	
	public static FacesContext facesContext(){
		return FacesContext.getCurrentInstance();
	}

	public static boolean isPostback() {
		return FacesContext.getCurrentInstance().isPostback();
	}

	public static boolean isNotPostback() {
		return !isPostback();
	}

	public static String getParameter(String key) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(key);
	}

	public static String caminhoContext() {
		return FacesContext
				.getCurrentInstance()
				.getExternalContext()
				.getRealPath(
						"/WEB-INF/classes/relatorios/");
	}

}
