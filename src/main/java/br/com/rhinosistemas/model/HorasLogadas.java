package br.com.rhinosistemas.model;

import java.util.Date;

public class HorasLogadas implements Comparable<HorasLogadas> {

	private String nomeUsuario;
	private Date dataWorkLog;
	private Long quantidadeHoras;

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public Date getDataWorkLog() {
		return dataWorkLog;
	}

	public void setDataWorkLog(Date dataWorkLog) {
		this.dataWorkLog = dataWorkLog;
	}

	public Long getQuantidadeHoras() {
		return quantidadeHoras;
	}

	public void setQuantidadeHoras(Long quantidadeHoras) {
		this.quantidadeHoras = quantidadeHoras;
	}

	@Override
	public int compareTo(HorasLogadas o) {
		
		return o.getDataWorkLog().compareTo(this.getDataWorkLog());	
		
	}

	@Override
	public String toString() {
		return "HorasLogadas [nomeUsuario=" + nomeUsuario + ", dataWorkLog=" + dataWorkLog + ", quantidadeHoras="
				+ quantidadeHoras + "]";
	}
	
}
