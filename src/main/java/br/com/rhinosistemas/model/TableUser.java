package br.com.rhinosistemas.model;

import java.io.Serializable;
import java.util.List;

public class TableUser implements Serializable {
    private static final long  serialVersionUID = 6517857019988410395L;

    private String             user;
    private List<WorklogHours> hours;

    public TableUser() {
        super();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<WorklogHours> getHours() {
        return hours;
    }

    public void setHours(List<WorklogHours> hours) {
        this.hours = hours;
    }
}
