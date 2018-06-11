package br.com.rhinosistemas.config;

import org.springframework.beans.factory.annotation.Value;

public class JiraConfig {
    
    @Value("${endereco.jira}")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
