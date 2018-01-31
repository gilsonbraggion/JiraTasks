package br.com.rhinosistemas.model;

public class Usuario {

	private String usuario;
	private String password;

	private String urlJira;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrlJira() {
		return urlJira;
	}

	public void setUrlJira(String urlJira) {
		this.urlJira = urlJira;
	}

}
