package br.com.sistemacomercialerp.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.sistemacomercialerp.entidade.Produto;
import br.com.sistemacomercialerp.servico.ProdutoServico;
import br.com.sistemacomercialerp.util.cdi.CDIServiceLocator;

@FacesConverter(value = "produtoConverter")
public class ProdutoConverter implements Converter {

	ProdutoServico servicoProduto;

	public ProdutoConverter() {
		this.servicoProduto = CDIServiceLocator.getBean(ProdutoServico.class);
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (value != null && !value.equals("")) {
			return servicoProduto.consultarId(Long.valueOf(value));
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		if (value != null) {
			return String.valueOf(((Produto) value).getId());
		}
		return null;
	}

}
