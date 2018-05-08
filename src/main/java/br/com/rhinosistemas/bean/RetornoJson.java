package br.com.rhinosistemas.bean;

import java.util.List;

import br.com.rhinosistemas.model.Sprint;

public class RetornoJson {

	private List<Issues> issues;
	private List<Sprint> values;

	public List<Issues> getIssues() {
		return issues;
	}

	public void setIssues(List<Issues> issues) {
		this.issues = issues;
	}

	public List<Sprint> getValues() {
		return values;
	}

	public void setValues(List<Sprint> values) {
		this.values = values;
	}

}
