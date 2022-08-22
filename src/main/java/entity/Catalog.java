package entity;

import java.time.LocalDate;

public class Catalog {
    private String company;
    private LocalDate date;
    private String uuid;


    public Catalog() {

    }

    public String getCompany() {
        return company;
    }

    public String getUuid() {
        return uuid;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


}
