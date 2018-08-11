package br.com.rhinosistemas.model;

import java.util.Date;

import br.com.rhinosistemas.util.Util;

public class Sprint {

	private String id;
	private String name;
	private String startDate;
	private String endDate;
	private String originBoardId;

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
	
	public String getOriginBoardId() {
		return originBoardId;
	}

	public void setOriginBoardId(String originBoardId) {
		this.originBoardId = originBoardId;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Sprint [name=" + name + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
	
	

}
