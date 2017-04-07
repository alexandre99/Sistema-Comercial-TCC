package br.com.sistemacomercialerp.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sistemacomercialerp.entidade.Empresa;
import br.com.sistemacomercialerp.entidade.Estado;
import br.com.sistemacomercialerp.servico.EmpresaServico;
import br.com.sistemacomercialerp.util.LoggerUtil;
import br.com.sistemacomercialerp.util.MensagemPadrao;

@Named
@ViewScoped
public class EmpresaController implements Serializable {

	private static final long serialVersionUID = -3595764600050017135L;

	@Inject
	private EmpresaServico servico;

	private Empresa empresa;

	@PostConstruct
	public void inicializar() {
		empresa = new Empresa();
		verificaEmpresaCadastrada();
	}

	public void salvar() {
		try {
			servico.inserir(empresa);
			limpar();
			MensagemPadrao.mensagemSalvar();
			LoggerUtil.registrarLoggerInfo(Empresa.class,
					"Registro salvo com sucesso!");
		} catch (Exception e) {
			MensagemPadrao.mensagemErroSalvar(e.getMessage());
			LoggerUtil.registrarLoggerErro(Empresa.class,
					"Falha ao salvar registro");
		}
	}

	public void limpar() {
		inicializar();
	}

	public void verificaEmpresaCadastrada() {
		Empresa empresaCadastrada = servico.consultarId(1l);
		if (empresaCadastrada != null) {
			setEmpresa(empresaCadastrada);
		}
	}

	public Estado[] getListaEstado() {
		return Estado.values();
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

}
