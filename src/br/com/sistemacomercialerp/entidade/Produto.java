package br.com.sistemacomercialerp.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="produto")
public class Produto implements Serializable {

	private static final long serialVersionUID = 6280735764044513420L;

	@Id
	@GeneratedValue
	private Long id;

	@NotBlank
	@Column(nullable = false, length = 50)
	private String descricao;

	@NotNull
	@Column(name = "unidade_venda", nullable = false, length = 20)
	@Enumerated(EnumType.STRING)
	private UnidadeVenda unidadeVenda;

	@Column(name = "preco_custo", precision = 10, scale = 2)
	private BigDecimal precoCusto;

	@NotNull
	@Column(name = "preco_venda", precision = 10, scale = 2)
	private BigDecimal precoVenda;

	@NotBlank
	@Column(name = "codigo_barras", nullable = false, length = 30)
	private String codigoBarras;

	@Column(name = "percentual_venda")
	private double percentualVenda;

	@Column(length = 100)
	private String observacao;

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "data_cadastro")
	private Date dataCadastro;

	@NotNull
	@DecimalMin(value = "0")
	@Column(precision = 5, scale = 2)
	private double quantidade;

	@NotNull
	private boolean ativo;

	public Produto() {
		this.dataCadastro = new Date();
		this.ativo = true;
		this.precoCusto = new BigDecimal("0.00");
		this.precoVenda = new BigDecimal("0.00");
	}

	public Produto(Long id, String descricao, UnidadeVenda unidadeVenda,
			BigDecimal precoCusto, BigDecimal precoVenda, String codigoBarras,
			double percentualVenda, String observacao, Date dataCadastro,
			double quantidade, boolean ativo) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.unidadeVenda = unidadeVenda;
		this.precoCusto = precoCusto;
		this.precoVenda = precoVenda;
		this.codigoBarras = codigoBarras;
		this.percentualVenda = percentualVenda;
		this.observacao = observacao;
		this.dataCadastro = dataCadastro;
		this.quantidade = quantidade;
		this.ativo = ativo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public UnidadeVenda getUnidadeVenda() {
		return unidadeVenda;
	}

	public void setUnidadeVenda(UnidadeVenda unidadeVenda) {
		this.unidadeVenda = unidadeVenda;
	}

	public BigDecimal getPrecoCusto() {
		return precoCusto;
	}

	public void setPrecoCusto(BigDecimal precoCusto) {
		this.precoCusto = precoCusto;
	}

	public BigDecimal getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(BigDecimal precoVenda) {
		this.precoVenda = precoVenda;
	}

	public String getcodigoBarras() {
		return codigoBarras;
	}

	public void setcodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public double getPercentualVenda() {
		return percentualVenda;
	}

	public void setPercentualVenda(double percentualVenda) {
		this.percentualVenda = percentualVenda;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}

	public boolean isativo() {
		return ativo;
	}

	public void setativo(boolean ativo) {
		this.ativo = ativo;
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
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
