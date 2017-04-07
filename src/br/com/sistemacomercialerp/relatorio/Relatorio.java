package br.com.sistemacomercialerp.relatorio;

import java.io.Serializable;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import br.com.sistemacomercialerp.util.LoggerUtil;
import br.com.sistemacomercialerp.util.MensagemPadrao;
import br.com.sistemacomercialerp.util.jsf.FacesUtil;

@Named
@RequestScoped
public class Relatorio implements Serializable {

	private static final long serialVersionUID = 6631531823152502605L;

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	@Inject
	private EntityManager manager;

	public void executarRelatorio(Map<String, Object> parametros,
			String nomeArquivoRelatorio) {
		try {
			String caminhoRelatorio = FacesUtil.caminhoContext()
					+ nomeArquivoRelatorio;

			ExecutorRelatorio executor = new ExecutorRelatorio(
					caminhoRelatorio, this.response, parametros);

			Session session = manager.unwrap(Session.class);
			session.doWork(executor);

			if (executor.isRelatorioGerado()) {
				facesContext.responseComplete();//Interroper o ciclo do jsf, para não renderizar mais as páginas.
			} else {
				MensagemPadrao.mensagemSemRegistroDeDadosRelatorio();
			}
		} catch (Exception e) {
			LoggerUtil.registrarLoggerErro(getClass(), e.getMessage());
			MensagemPadrao.mensagemErroGerarRelatorio(e.getMessage() + " " + e);
		}

	}
}
