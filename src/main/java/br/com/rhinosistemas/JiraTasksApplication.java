package br.com.rhinosistemas;

import br.com.rhinosistemas.config.JiraConfig;
import br.com.rhinosistemas.util.JiraSingleton;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
@ComponentScan(basePackages = {"br.com.rhinosistemas.controller"})
public class JiraTasksApplication {

	public static void main(String[] args) {
		SpringApplication.run(JiraTasksApplication.class, args);
	}
	
	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public JiraConfig getJiraConfig() {
	    return new JiraConfig();
	}
	
	@Bean("conexaoJira")
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public JiraSingleton getJiraSingleton() {
	    return new JiraSingleton();
	}
}

// DateFormat formato = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmm");