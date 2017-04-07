package br.com.sistemacomercialerp.util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public abstract class ConfiguracaoMensagem {

	private static Severity ERROR = FacesMessage.SEVERITY_ERROR;
	private static Severity INFO = FacesMessage.SEVERITY_INFO;
	private static Severity WARN = FacesMessage.SEVERITY_WARN;

	public static void addMensagem(Severity severity, String msg) {
		FacesContext context = FacesContext.getCurrentInstance();

		context.getExternalContext().getFlash().setKeepMessages(Boolean.TRUE);

		context.addMessage(null, new FacesMessage(severity, msg, null));
	}

	public static void addMensagemError(String msg) {
		addMensagem(ERROR, msg);
	}

	public static void addMensagemInfo(String msg) {
		addMensagem(INFO, msg);
	}

	public static void addMensagemWarn(String msg) {
		addMensagem(WARN, msg);
	}
}
