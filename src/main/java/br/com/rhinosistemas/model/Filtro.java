package br.com.rhinosistemas.model;

import java.io.Serializable;
import java.util.Date;

public class Filtro implements Serializable {

	private static final long serialVersionUID = -4234636855578637686L;

	private String key;
	private String sprint;
	private String fields;
	private String sprintName;
	private String numeroUsuario;
	private Date dataInicio;
	private String dataInicioHoras;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSprint() {
		return sprint;
	}

	public void setSprint(String sprint) {
		this.sprint = sprint;
	}

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public String getSprintName() {
		return sprintName;
	}

	public void setSprintName(String sprintName) {
		this.sprintName = sprintName;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String getNumeroUsuario() {
		return numeroUsuario;
	}

	public void setNumeroUsuario(String numeroUsuario) {
		this.numeroUsuario = numeroUsuario;
	}

	public String getDataInicioHoras() {
		return dataInicioHoras;
	}

	public void setDataInicioHoras(String dataInicioHoras) {
		this.dataInicioHoras = dataInicioHoras;
	}

}