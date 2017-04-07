package br.com.sistemacomercialerp.entidade;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Entity
public class PedidoItem implements Serializable {

	private static final long serialVersionUID = 7612503397568690511L;

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Column(nullable = false)
	private int numeroItem;

	@ManyToOne
	@JoinColumn(name = "id_pedido")
	private Pedido pedido;

	@ManyToOne
	@JoinColumn(name = "id_produto")
	private Produto produto;

	@NotNull
	@DecimalMin(value = "0")
	@Column(precision = 5, scale = 2)
	private double quantidade;

	@NotNull
	@Column(name = "valor_unitario_item", precision = 10, scale = 2, nullable = false)
	private BigDecimal valorUnitarioItem;

	@NotNull
	@Column(name = "valor_total_item", precision = 10, scale = 2, nullable = false)
	private BigDecimal valorTotalItem;

	public PedidoItem() {
		super();
		this.valorTotalItem = new BigDecimal("0.00");
		this.valorUnitarioItem = new BigDecimal("0.00");
		this.quantidade = 1l;
	}

	public PedidoItem(Long id, int numeroItem, Pedido pedido, Produto produto,
			double quantidade, BigDecimal valorUnitarioItem,
			BigDecimal valorTotalItem) {
		super();
		this.id = id;
		this.numeroItem = numeroItem;
		this.pedido = pedido;
		this.produto = produto;
		this.quantidade = quantidade;
		this.valorUnitarioItem = valorUnitarioItem;
		this.valorTotalItem = valorTotalItem;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNumeroItem() {
		return numeroItem;
	}

	public void setNumeroItem(int numeroItem) {
		this.numeroItem = numeroItem;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorUnitarioItem() {
		return valorUnitarioItem;
	}

	public void setValorUnitarioItem(BigDecimal valorUnitarioItem) {
		this.valorUnitarioItem = valorUnitarioItem;
	}

	public BigDecimal getValorTotalItem() {
		return valorTotalItem;
	}

	public void setValorTotalItem(BigDecimal valorTotalItem) {
		this.valorTotalItem = valorTotalItem;
	}

	@Override
	public String toString() {
		return "id= " + id + ", Número item= " + numeroItem + ", Número Pedido= "
				+ pedido + ", produto= " + produto + ", quantidade= "
				+ quantidade + ", valor unitario item= " + valorUnitarioItem
				+ ", valor total item= " + valorTotalItem;
	}

	@Transient
	public boolean isProdutoNull() {
		return this.produto.getId() == null;
	}

	@Transient
	public boolean isEstoqueDisponivel() {
		return getProduto().getQuantidade() >= getQuantidade()
				|| getProduto().getId() == null;
	}

	@Transient
	public boolean isEstoqueIndisponivel() {
		return !isEstoqueDisponivel();
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
		PedidoItem other = (PedidoItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
