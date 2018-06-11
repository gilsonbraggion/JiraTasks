package br.com.rhinosistemas.util;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;

import br.com.rhinosistemas.config.JiraConfig;
import br.com.rhinosistemas.model.Usuario;

//@Configuration
public class JiraSingleton {
	
    @Autowired
    private JiraConfig config;
    
    @Autowired
    private HttpSession session;

//	@Bean("conexaoJira")
//	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public JiraRestClient getAccountService() throws URISyntaxException {
		
		URI enderecoJira = new URI(config.getUrl());
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		
		return new AsynchronousJiraRestClientFactory().createWithBasicHttpAuthentication(enderecoJira, usuario.getUsuario(), usuario.getPassword());
	
	}

}
