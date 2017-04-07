package br.com.sistemacomercialerp.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "balanco_estoque_produtos")
public class BalancoEstoqueProdutos implements Serializable {

	private static final long serialVersionUID = -2656873855897031684L;

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Column(nullable = false)
	private int numeroItem;

	@ManyToOne
	@JoinColumn(name = "id_balanco_estoque")
	private BalancoEstoque balancoEstoque;

	@ManyToOne
	@JoinColumn(name = "id_produto")
	private Produto produto;

	@NotNull
	@DecimalMin(value = "0")
	@Column(name = "volume_anterior", precision = 5, scale = 2)
	private double volumeAnterior;

	@NotNull
	@DecimalMin(value = "0")
	@Column(name = "volume_ajuste", precision = 5, scale = 2)
	private double volumeAjuste;

	@NotNull
	@DecimalMin(value = "0")
	@Column(name = "volume_ajustado", precision = 5, scale = 2)
	private double volumeAjustado;

	@NotNull
	@Column(name = "tipo_balanco", nullable = false)
	private int tipoBalanco;

	public BalancoEstoqueProdutos() {
		tipoBalanco = 1;
	}

	public BalancoEstoqueProdutos(Long id, int numeroItem,
			BalancoEstoque balancoEstoque, Produto produto,
			double volumeAnterior, double volumeAjuste, double volumeAjustado,
			int tipoBalanco) {
		super();
		this.id = id;
		this.numeroItem = numeroItem;
		this.balancoEstoque = balancoEstoque;
		this.produto = produto;
		this.volumeAnterior = volumeAnterior;
		this.volumeAjuste = volumeAjuste;
		this.volumeAjustado = volumeAjustado;
		this.tipoBalanco = tipoBalanco;
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

	public BalancoEstoque getBalancoEstoque() {
		return balancoEstoque;
	}

	public void setBalancoEstoque(BalancoEstoque balancoEstoque) {
		this.balancoEstoque = balancoEstoque;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public double getVolumeAnterior() {
		return volumeAnterior;
	}

	public void setVolumeAnterior(double volumeAnterior) {
		this.volumeAnterior = volumeAnterior;
	}

	public double getVolumeAjuste() {
		return volumeAjuste;
	}

	public void setVolumeAjuste(double volumeAjuste) {
		this.volumeAjuste = volumeAjuste;
	}

	public double getVolumeAjustado() {
		return volumeAjustado;
	}

	public void setVolumeAjustado(double volumeAjustado) {
		this.volumeAjustado = volumeAjustado;
	}

	public int getTipoBalanco() {
		return tipoBalanco;
	}

	public void setTipoBalanco(int tipoBalanco) {
		this.tipoBalanco = tipoBalanco;
	}

	@Transient
	public boolean isProdutoNull() {
		return this.produto.getId() == null;
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
		BalancoEstoqueProdutos other = (BalancoEstoqueProdutos) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
