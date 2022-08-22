package entity;

import java.math.BigDecimal;

public class Plant {
    private String common;
    private String botanical;
    private int zone;
    private String light;
    private BigDecimal price;
    private int availability;


    public Plant() {
    }

    public String getCommon() {
        return common;
    }

    public String getBotanical() {
        return botanical;
    }

    public int getZone() {
        return zone;
    }

    public String getLight() {
        return light;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getAvailability() {
        return availability;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    public void setBotanical(String botanical) {
        this.botanical = botanical;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    public void setLight(String light) {
        this.light = light;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }
}
