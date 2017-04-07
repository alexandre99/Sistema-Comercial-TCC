package br.com.sistemacomercialerp.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.sistemacomercialerp.entidade.Usuario;
import br.com.sistemacomercialerp.servico.UsuarioServico;
import br.com.sistemacomercialerp.util.cdi.CDIServiceLocator;

@FacesConverter(value = "usuarioConverter")
public class UsuarioConverter implements Converter {

	UsuarioServico servicoUsuario;

	public UsuarioConverter() {
		this.servicoUsuario = CDIServiceLocator.getBean(UsuarioServico.class);
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		if (value != null && !value.equals("Selecione")) {
			return servicoUsuario.consultarId(Long.valueOf(value));
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		if (value != null) {
			return String.valueOf(((Usuario) value).getId());
		}
		return null;
	}

}
