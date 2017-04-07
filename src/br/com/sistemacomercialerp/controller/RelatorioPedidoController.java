package br.com.sistemacomercialerp.controller;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.sistemacomercialerp.entidade.Cliente;
import br.com.sistemacomercialerp.entidade.SituacaoPedido;
import br.com.sistemacomercialerp.entidade.Usuario;
import br.com.sistemacomercialerp.relatorio.Relatorio;
import br.com.sistemacomercialerp.servico.ClienteServico;
import br.com.sistemacomercialerp.servico.UsuarioServico;
import br.com.sistemacomercialerp.util.LoggerUtil;

@Named
@RequestScoped
public class RelatorioPedidoController implements Serializable {

	private static final long serialVersionUID = 4163114836878137255L;

	@Inject
	private ClienteServico servicoCliente;

	@Inject
	private UsuarioServico servicoUsuario;

	@Inject
	private Relatorio imprimirPedido;

	@Inject
	private DateFormat formatador;

	// variaveis de controle de tela

	private Date dataInicial;
	private Date dataFinal;
	private Cliente cliente;
	private Usuario usuario;
	private List<Cliente> listaCliente;
	private List<Usuario> listaUsuario;
	private SituacaoPedido situacao;
	private String tipoRelatorio;

	@PostConstruct
	public void inicializar() {
		dataInicial = new Date();
		dataFinal = new Date();
		listaCliente = new ArrayList<>();
		listaUsuario = new ArrayList<>();
		tipoRelatorio = "Simplificado";
	}

	public void imprimirRelatorioPedido() {
		try {
			String nomeArquivoRelatorio = "";
			String queryComplementar = "";

			if (tipoRelatorio.equals("Simplificado")) {
				nomeArquivoRelatorio = "RelatorioPedido.jasper";
			} else {
				nomeArquivoRelatorio = "RelatorioPedidoCompleto.jasper";
			}

			Map<String, Object> parametros = new HashMap<>();

			if (dataInicial != null && dataFinal != null) {
				queryComplementar = " AND date_trunc('day', pedido.data_hora) BETWEEN "
						+ "'"
						+ converterDateString(dataInicial)
						+ "'"
						+ " AND " + "'" + converterDateString(dataFinal) + "'";
			} else if (dataInicial != null) {
				queryComplementar = " date_trunc('day', pedido.data_hora) = "
						+ "'" + converterDateString(dataInicial) + "'";
			} else if (dataFinal != null) {
				queryComplementar = " AND date_trunc('day', pedido.data_hora) = "
						+ "'" + converterDateString(dataFinal) + "'";
			}

			if (cliente != null) {
				queryComplementar = queryComplementar + " AND cliente.nome = "
						+ "'" + cliente.getNome() + "'";
			}

			if (usuario != null) {
				queryComplementar = queryComplementar + " AND usuario.nome = "
						+ "'" + usuario.getNome() + "'";
			}

			queryComplementar = queryComplementar + " AND pedido.situacao = "
					+ "'" + situacao + "'";

			parametros.put("tituloRelatorio",
					"Relatório de " + situacao.getDescricao() + " "
							+ tipoRelatorio);
			parametros.put("parametrosConsultas", queryComplementar);

			imprimirPedido.executarRelatorio(parametros, nomeArquivoRelatorio);
		} catch (Exception e) {
			LoggerUtil.registrarLoggerErro(getClass(),
					"Erro ao tentar imprimir relatório: " + e.getMessage());
		}
	}

	public List<Cliente> autoCompleteCliente(String nomeCliente) {
		try {
			listaCliente = servicoCliente.autoCompleteCliente(nomeCliente
					.toUpperCase());
		} catch (Exception e) {
			LoggerUtil.registrarLoggerErro(getClass(),
					"Erro no autoCompleteCliente: " + e.getMessage());
		}

		return listaCliente;
	}

	public List<Usuario> autoCompleteVendedor(String nomeVendedor) {
		try {
			listaUsuario = servicoUsuario.autoCompleteVendedor(nomeVendedor
					.toUpperCase());
		} catch (Exception e) {
			LoggerUtil.registrarLoggerErro(getClass(),
					"Erro no autoCompleteVendedor: " + e.getMessage());
		}
		return listaUsuario;
	}

	public String converterDateString(Date data) {
		return formatador.format(data);
	}

	public SituacaoPedido[] getSituacaoPedido() {
		return SituacaoPedido.values();
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public SituacaoPedido getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoPedido situacao) {
		this.situacao = situacao;
	}

	public String getTipoRelatorio() {
		return tipoRelatorio;
	}

	public void setTipoRelatorio(String tipoRelatorio) {
		this.tipoRelatorio = tipoRelatorio;
	}

}
