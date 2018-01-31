package br.com.rhinosistemas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"br.com.rhinosistemas.controller"})
public class JiraTasksApplication {

	public static void main(String[] args) {
		SpringApplication.run(JiraTasksApplication.class, args);
	}
}

// DateFormat formato = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmm");