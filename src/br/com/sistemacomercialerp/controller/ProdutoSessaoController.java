package br.com.sistemacomercialerp.controller;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Named
@SessionScoped
public class ProdutoSessaoController implements Serializable {

	private static final long serialVersionUID = 6434621603241150997L;

	private StreamedContent foto;

	public void setarStreamed(InputStream in) throws FileNotFoundException {
		// InputStream in = new FileInputStream(new File(path));
		foto = new DefaultStreamedContent(in, "image/jpeg");
	}

	public StreamedContent getFoto() {
		return foto;
	}

	public void setFoto(StreamedContent foto) {
		this.foto = foto;
	}

}
