package br.com.rhinosistemas.util;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;

import br.com.rhinosistemas.model.Usuario;

@Configuration
public class JiraSingleton {
	
	private static final String ENDERECO_JIRA = "https://jira.ci.gsnet.corp";

	@Bean("conexaoJira")
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public static JiraRestClient getAccountService(HttpSession session) throws URISyntaxException {
		
		URI enderecoJira = new URI(ENDERECO_JIRA);
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		
		return new AsynchronousJiraRestClientFactory().createWithBasicHttpAuthentication(enderecoJira, usuario.getUsuario(), usuario.getPassword());
	
	}

}
