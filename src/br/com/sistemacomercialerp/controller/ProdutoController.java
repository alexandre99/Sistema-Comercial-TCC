package br.com.sistemacomercialerp.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.JOptionPane;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.UploadedFile;

import br.com.sistemacomercialerp.entidade.Produto;
import br.com.sistemacomercialerp.entidade.UnidadeVenda;
import br.com.sistemacomercialerp.servico.ProdutoServico;
import br.com.sistemacomercialerp.util.LoggerUtil;
import br.com.sistemacomercialerp.util.MensagemPadrao;
import br.com.sistemacomercialerp.util.jsf.FacesUtil;
import br.com.sistemacomercialerp.util.jsf.PrimeFacesUtil;

@Named
@ViewScoped
public class ProdutoController implements Serializable {

	private static final long serialVersionUID = 36280753814085176L;

	@Inject
	private ProdutoServico servico;

	private Produto produto;

	private UploadedFile fileFoto;

	// Variaveis para controle de tela
	private int abaDadosPrincipais;

	@PostConstruct
	public void inicializar() {
		produto = new Produto();
		abaDadosPrincipais = 0;
	}

	public void salvar() {
		try {
			servico.inserir(produto);
			limpar();
			MensagemPadrao.mensagemSalvar();
			LoggerUtil.registrarLoggerInfo(getClass(),
					"Registro Gravado com Sucesso");
			PrimeFacesUtil.executarFuncaoJavascript("configurarMoeda()");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage() + " " + e.getCause());
			MensagemPadrao.mensagemErroSalvar(e.getMessage());
			LoggerUtil.registrarLoggerErro(
					getClass(),
					"Erro ao salvar o registro! " + e.getMessage() + " "
							+ e.getCause());
		}
	}

	public void inicializarParametros() {

		if (StringUtils.isNotBlank(FacesUtil.getParameter("idProduto"))) {
			try {
				setProduto(this.servico.consultarId((Long.valueOf(FacesUtil
						.getParameter("idProduto")))));

			} catch (Exception e) {
				MensagemPadrao.mensagemErroCarregarParametro(e.getMessage());
			}
		}

	}

	public void limpar() {
		inicializar();
	}

	public UnidadeVenda[] getUnidadesVenda() {
		return UnidadeVenda.values();
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public int getAbaDadosPrincipais() {
		return abaDadosPrincipais;
	}

	public void setAbaDadosPrincipais(int abaDadosPrincipais) {
		this.abaDadosPrincipais = abaDadosPrincipais;
	}

	public UploadedFile getFileFoto() {
		return fileFoto;
	}

	public void setFileFoto(UploadedFile fileFoto) {
		this.fileFoto = fileFoto;
	}

}
