package br.com.sistemacomercialerp.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.sistemacomercialerp.entidade.Cliente;
import br.com.sistemacomercialerp.servico.ClienteServico;
import br.com.sistemacomercialerp.util.cdi.CDIServiceLocator;

@FacesConverter(value = "clienteConverter")
public class ClienteConverter implements Converter {

	ClienteServico servicoCliente;

	public ClienteConverter() {
		this.servicoCliente = CDIServiceLocator.getBean(ClienteServico.class);
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (value != null && !value.equals("Selecione")) {
			return servicoCliente.consultarId(Long.valueOf(value));
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		if (value != null) {
			return String.valueOf(((Cliente) value).getId());
		}
		return null;
	}

}
