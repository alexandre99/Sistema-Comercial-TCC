package br.com.sistemacomercialerp.filtropesquisa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PedidoFiltro implements Serializable {

	private static final long serialVersionUID = 5734464290382019474L;

	private Date dataHora;
	private BigDecimal valorLiquido;

	public PedidoFiltro() {
		super();
	}

	public PedidoFiltro(Date dataHora, BigDecimal valorLiquido) {
		super();
		this.dataHora = dataHora;
		this.valorLiquido = valorLiquido;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public BigDecimal getValorLiquido() {
		return valorLiquido;
	}

	public void setValorLiquido(BigDecimal valorLiquido) {
		this.valorLiquido = valorLiquido;
	}

}
