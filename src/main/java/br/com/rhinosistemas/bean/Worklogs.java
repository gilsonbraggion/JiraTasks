package br.com.rhinosistemas.bean;

import br.com.rhinosistemas.util.Util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Worklogs {

	private String started;
	private long timeSpentSeconds;

	private Author author;

	public String getStarted() {
		return started;
	}
	
	public Date getStartedDate() {
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmm");
		try {
			Date d = f.parse(started);
			return d;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Date getStartedDateDay() {
        return Util.convertStringToDate(started);
	}

	public void setStarted(String started) {
		this.started = started;
	}

	public long getTimeSpentSeconds() {
		return timeSpentSeconds;
	}
	
	public long getHoursSpent() {
		return timeSpentSeconds / 3600;
	}

	public void setTimeSpentSeconds(long timeSpentSeconds) {
		this.timeSpentSeconds = timeSpentSeconds;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}
	
}
