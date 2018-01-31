package br.com.rhinosistemas.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class WorklogHours implements Serializable {
    private static final long serialVersionUID = 314342637966159453L;

    private Date              date;
    private long              time;

    public WorklogHours() {
        super();
    }

    public WorklogHours(Date date, long time) {
        super();
        this.date = date;
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
    
    public String getHtmlClass() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        int dw = calendar.get(Calendar.DAY_OF_WEEK);
        
        if (dw != 7 && dw != 1) {
            if (time == 0) {
                return "log-error";
            }
            
            if (time < 8) {
                return "log-alert";
            }
        }
        
        return "";
    }
}
