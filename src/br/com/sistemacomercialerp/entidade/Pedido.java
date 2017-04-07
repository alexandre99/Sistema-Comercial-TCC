package br.com.sistemacomercialerp.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.com.sistemacomercialerp.util.exception.SistemaComercialException;

@Entity
public class Pedido implements Serializable {

	private static final long serialVersionUID = 8860176829704856568L;

	@Id
	@GeneratedValue
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Column(nullable = false, name = "data_hora")
	private Date dataHora;

	@NotNull
	@JoinColumn(name = "id_cliente", nullable = false)
	@ManyToOne
	private Cliente cliente;

	@NotNull
	@JoinColumn(name = "id_vendedor", nullable = false)
	@ManyToOne
	private Usuario vendedor;

	@NotNull
	@Column(name = "valor_bruto", precision = 10, scale = 2, nullable = false)
	private BigDecimal valorBruto;

	@NotNull
	@Column(name = "valor_desconto", precision = 10, scale = 2, nullable = false)
	private BigDecimal valorDesconto;

	@NotNull
	@Column(name = "valor_liquido", precision = 10, scale = 2, nullable = false)
	private BigDecimal valorLiquido;

	@NotNull
	@Column(nullable = false)
	private boolean ativo;

	@Column(length = 100)
	private String observacao;

	@NotNull
	@Column(nullable = false, length = 30)
	@Enumerated(EnumType.STRING)
	private SituacaoPedido situacao;

	@NotNull
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PedidoItem> pedidoItem;

	@NotNull
	@Column(nullable = false, length = 30, name = "forma_pagamento")
	@Enumerated(EnumType.STRING)
	private FormaDePagamento formaDePagamento;

	public Pedido() {
		super();
		this.dataHora = new Date();
		this.valorBruto = new BigDecimal("0.00");
		this.valorDesconto = new BigDecimal("0.00");
		this.valorLiquido = new BigDecimal("0.00");
		this.ativo = true;
	}

	public Pedido(Long id, Date dataHora, Cliente cliente, Usuario vendedor,
			BigDecimal valorBruto, BigDecimal valorDesconto,
			BigDecimal valorLiquido, boolean ativo, String observacao,
			SituacaoPedido situacao, List<PedidoItem> pedidoItem,
			FormaDePagamento formaDePagamento) {
		super();
		this.id = id;
		this.dataHora = dataHora;
		this.cliente = cliente;
		this.vendedor = vendedor;
		this.valorBruto = valorBruto;
		this.valorDesconto = valorDesconto;
		this.valorLiquido = valorLiquido;
		this.ativo = ativo;
		this.observacao = observacao;
		this.situacao = situacao;
		this.pedidoItem = pedidoItem;
		this.formaDePagamento = formaDePagamento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Usuario getVendedor() {
		return vendedor;
	}

	public void setVendedor(Usuario vendedor) {
		this.vendedor = vendedor;
	}

	public BigDecimal getValorBruto() {
		return valorBruto;
	}

	public void setValorBruto(BigDecimal valorBruto) {
		this.valorBruto = valorBruto;
	}

	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public BigDecimal getValorLiquido() {
		return valorLiquido;
	}

	public void setValorLiquido(BigDecimal valorLiquido) {
		this.valorLiquido = valorLiquido;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public SituacaoPedido getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoPedido situacao) {
		this.situacao = situacao;
	}

	public List<PedidoItem> getPedidoItem() {
		return pedidoItem;
	}

	public void setPedidoItem(List<PedidoItem> pedidoItem) {
		this.pedidoItem = pedidoItem;
	}

	public FormaDePagamento getFormaDePagamento() {
		return formaDePagamento;
	}

	public void setFormaDePagamento(FormaDePagamento formaDePagamento) {
		this.formaDePagamento = formaDePagamento;
	}

	public void ordenarLista() {
		PedidoItem pedidoItem = new PedidoItem();
		pedidoItem.setNumeroItem(0);
		pedidoItem.setProduto(new Produto());

		getPedidoItem().add(pedidoItem);

		getPedidoItem().sort(
				(Comparator.comparing(item1 -> item1.getNumeroItem())));
	}
	
	
	public boolean verificarListaVazia() throws SistemaComercialException {
		return getPedidoItem().size() < 2;
	}

	public boolean verificarItemComEstoqueInsuficiente() {
		for (PedidoItem item : getPedidoItem()) {
			if (item.getProduto().getcodigoBarras() != null) {
				return item.getProduto().getQuantidade() < item.getQuantidade();
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
