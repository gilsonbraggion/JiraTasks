package br.com.rhinosistemas.model;

import br.com.rhinosistemas.util.Util;

import java.util.Date;

public class Sprint {

	private String name;
	private String startDate;
	private String endDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public Date getStartDateDay() {
	    return Util.convertStringToDate(getStartDate());
	}
	
	public Date getEndDateDay() {
	    Date date = Util.convertStringToDate(getEndDate());
        return Util.stringToDateFinalDia(date);
    }

}
