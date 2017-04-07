package br.com.sistemacomercialerp.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

@Entity
@Table(name = "empresa")
public class Empresa implements Serializable {

	private static final long serialVersionUID = -5270880550781267801L;

	@Id
	@GeneratedValue
	private Long id;

	@NotNull(message = "teste")
	@Column(name = "razao_social", nullable = false, length = 50)
	private String razaoSocial;

	@Column(name = "nome_fantasia", length = 50)
	private String nomeFantasia;

	@NotBlank
	@Column(nullable = false, length = 18)
	@CNPJ
	private String cnpj;

	@Column(name = "inscricao_estadual", length = 20)
	private String inscricaoEstadual;

	@Column(name = "inscricao_municipal", length = 20)
	private String inscricaoMunicipal;

	@NotBlank
	@Column(length = 40, nullable = false)
	private String endereco;

	@Column(length = 10, nullable = false)
	private String numero;

	@Column(length = 40, nullable = false)
	private String complemento;

	@NotBlank
	@Column(length = 9, nullable = false)
	private String cep;

	@NotBlank
	@Column(length = 30, nullable = false)
	private String bairro;

	@NotNull
	@Column(length = 20, nullable = false)
	@Enumerated(EnumType.STRING)
	private Estado estado;

	@NotBlank
	@Column(length = 30, nullable = false)
	private String cidade;

	@NotBlank
	@Column(length = 30, nullable = false)
	private String pais;

	@NotBlank
	@Column(length = 30, nullable = false)
	private String responsavel;

	@NotBlank
	@Column(length = 14, nullable = false)
	private String telefone;

	@Column(length = 14, nullable = false)
	private String fax;

	@Column(length = 30, nullable = false)
	@Email
	private String email;

	public Empresa() {

	}

	public Empresa(Long id, String razaoSocial, String nomeFantasia,
			String cnpj, String inscricaoEstadual, String inscricaoMunicipal,
			String endereco, String numero, String complemento, String cep,
			String bairro, Estado estado, String cidade, String pais,
			String responsavel, String telefone, String fax, String email) {
		super();
		this.id = id;
		this.razaoSocial = razaoSocial;
		this.nomeFantasia = nomeFantasia;
		this.cnpj = cnpj;
		this.inscricaoEstadual = inscricaoEstadual;
		this.inscricaoMunicipal = inscricaoMunicipal;
		this.endereco = endereco;
		this.numero = numero;
		this.complemento = complemento;
		this.cep = cep;
		this.bairro = bairro;
		this.estado = estado;
		this.cidade = cidade;
		this.pais = pais;
		this.responsavel = responsavel;
		this.telefone = telefone;
		this.fax = fax;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public String getInscricaoMunicipal() {
		return inscricaoMunicipal;
	}

	public void setInscricaoMunicipal(String inscricaoMunicipal) {
		this.inscricaoMunicipal = inscricaoMunicipal;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
		Empresa other = (Empresa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
