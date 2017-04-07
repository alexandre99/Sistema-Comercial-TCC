package br.com.sistemacomercialerp.entidade;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.com.sistemacomercialerp.util.exception.SistemaComercialException;

@Entity
@Table(name = "balanco_estoque")
public class BalancoEstoque implements Serializable{

	private static final long serialVersionUID = -5098579521411235057L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Column(nullable = false, name = "data_hora")
	private Date dataHora;

	@NotNull
	@OneToMany(mappedBy = "balancoEstoque", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<BalancoEstoqueProdutos> itens;
	
	@NotNull
	@Column(nullable = false)
	private boolean ativo;

	public BalancoEstoque() {
		this.dataHora = new Date();
		ativo = true;
	}
	
	public BalancoEstoque(Long id, Date dataHora,
			List<BalancoEstoqueProdutos> itens, boolean ativo) {
		super();
		this.id = id;
		this.dataHora = dataHora;
		this.itens = itens;
		this.ativo = ativo;
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

	public List<BalancoEstoqueProdutos> getItens() {
		return itens;
	}

	public void setItens(List<BalancoEstoqueProdutos> itens) {
		this.itens = itens;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public void ordenarLista() {
		BalancoEstoqueProdutos balancoItens = new BalancoEstoqueProdutos();
		balancoItens.setNumeroItem(0);
		balancoItens.setProduto(new Produto());

		getItens().add(balancoItens);

		getItens().sort(
				(Comparator.comparing(item1 -> item1.getNumeroItem())));
	}
	
	
	public boolean verificarListaVazia() throws SistemaComercialException {
		return getItens().size() < 2;
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
		BalancoEstoque other = (BalancoEstoque) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
