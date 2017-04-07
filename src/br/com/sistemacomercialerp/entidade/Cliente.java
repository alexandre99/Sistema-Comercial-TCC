package br.com.sistemacomercialerp.entidade;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import br.com.sistemacomercialerp.util.exception.SistemaComercialException;

@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

	private static final long serialVersionUID = 2915494217673449072L;

	@Id
	@GeneratedValue
	private Long id;

	@NotBlank
	@Column(length = 50, nullable = false)
	private String nome;

	@Column(length = 1)
	private char tipoPessoa;

	@Column(length = 40)
	private String endereco;

	@Column(length = 10)
	private String numero;

	@Column(length = 40)
	private String complemento;

	@Column(length = 9)
	private String cep;

	@Column(length = 30)
	private String bairro;

	@Column(length = 20)
	@Enumerated(EnumType.STRING)
	private Estado estado;

	@Column(length = 30)
	private String cidade;

	@Column(length = 30)
	private String pais;

	@Column(length = 14)
	private String telefone;

	@Column(length = 15)
	private String celular;

	@Column(length = 14)
	private String fax;

	@Column(length = 30)
	@Email
	private String email;

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "data_cadastro", nullable = false)
	private Date dataCadastro;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_nascimento")
	private Date dataNascimento;

	@Column(name = "inscricao_estadual", length = 20)
	private String inscricaoEstadual;

	@Column(name = "inscricao_municipal", length = 20)
	private String inscricaoMunicipal;

	@Column(length = 18, unique = true)
	@CNPJ
	private String cnpj;

	@CPF
	@Column(length = 14, unique = true)
	private String cpf;

	@Column(length = 20)
	private String rg;

	@Column(length = 30)
	private String nomeFantasia;

	@Column(length = 100)
	private String observacao;

	@NotNull
	private boolean ativo;

	public Cliente() {
		this.dataCadastro = new Date();
		this.tipoPessoa = 'F';
		this.ativo = true;
	}

	public Cliente(Long id, String nome, char tipoPessoa, String endereco,
			String numero, String complemento, String cep, String bairro,
			Estado estado, String cidade, String pais, String telefone,
			String celular, String fax, String email, Date dataCadastro,
			Date dataNascimento, String inscricaoEstadual,
			String inscricaoMunicipal, String cnpj, String cpf, String rg,
			String nomeFantasia, String observacao, boolean ativo) {
		super();
		this.id = id;
		this.nome = nome;
		this.tipoPessoa = tipoPessoa;
		this.endereco = endereco;
		this.numero = numero;
		this.complemento = complemento;
		this.cep = cep;
		this.bairro = bairro;
		this.estado = estado;
		this.cidade = cidade;
		this.pais = pais;
		this.telefone = telefone;
		this.celular = celular;
		this.fax = fax;
		this.email = email;
		this.dataCadastro = dataCadastro;
		this.dataNascimento = dataNascimento;
		this.inscricaoEstadual = inscricaoEstadual;
		this.inscricaoMunicipal = inscricaoMunicipal;
		this.cnpj = cnpj;
		this.cpf = cpf;
		this.rg = rg;
		this.nomeFantasia = nomeFantasia;
		this.observacao = observacao;
		this.ativo = ativo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public char getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(char tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
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

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
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

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public void validarDataDeNascimento(Date dataDeNascimento)
			throws SistemaComercialException {
		if (dataDeNascimento.after(new Date())) {
			throw new SistemaComercialException("data de nasciento inválida");
		}
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
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
