package br.com.sistemacomercialerp.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sistemacomercialerp.entidade.Cliente;
import br.com.sistemacomercialerp.servico.ClienteServico;
import br.com.sistemacomercialerp.util.LoggerUtil;

@Named
@ViewScoped
public class PesquisaClienteController implements Serializable {

	private static final long serialVersionUID = -2179335895704782289L;

	@Inject
	private ClienteServico servico;

	private Cliente clienteSelecionado;
	private List<Cliente> listaCliente;

	// Variáveis de filtro
	private String filtro;
	private boolean clientesInativos;

	@PostConstruct
	public void inicializar() {
		clienteSelecionado = new Cliente();
		clientesInativos = false;
		listaCliente = new ArrayList<>();
	}

	public void pesquisarCliente() {
		try {

			if (clientesInativos) {
				clientesInativos = false;
			}
			listaCliente = servico.consultaCliente(filtro);
			clienteSelecionado = new Cliente();
		} catch (Exception e) {
			LoggerUtil.registrarLoggerErro(getClass(), "Erro " + e.getMessage()
					+ " " + e.getCause());
		}
	}

	public void pesquisarClientesInativos() {
		try {
			if (!"".equals(filtro)) {
				filtro = "";
			}
			listaCliente = servico.consultarClientesInativos();
			clienteSelecionado = new Cliente();
		} catch (Exception e) {
			LoggerUtil.registrarLoggerErro(getClass(), "Erro " + e.getMessage()
					+ " " + e.getCause());
		}

	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public List<Cliente> getListaCliente() {
		return listaCliente;
	}

	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

	public boolean isClientesInativos() {
		return clientesInativos;
	}

	public void setClientesInativos(boolean clientesInativos) {
		this.clientesInativos = clientesInativos;
	}

}
