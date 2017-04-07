package br.com.sistemacomercialerp.util.filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.sistemacomercialerp.controller.UsuarioLoginController;

@WebFilter("*.jsf")
public class AutorizacaoFilter implements Filter{

	@Inject
	private UsuarioLoginController usuario;

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		if (!usuario.isLogado()
				&& !request.getRequestURI().endsWith("/login.jsf")
				&& !request.getRequestURI().contains("/javax.faces.resource/")) {
			response.sendRedirect(request.getContextPath() + "/login.jsf");
		} else {
			chain.doFilter(req, res);
		}
	}

	public void init(FilterConfig config) throws ServletException {
	}

	public void destroy() {
	}
}
