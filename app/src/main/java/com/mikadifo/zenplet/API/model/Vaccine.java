package com.mikadifo.zenplet.API.model;

public class Vaccine {

    private long vaccinesId;
    private String vaccinesName;
    private String vaccinesDescription;

    public Vaccine() {
    }

    public Vaccine(long vaccinesId, String vaccinesName, String vaccinesDescription) {
        this.vaccinesId = vaccinesId;
        this.vaccinesName = vaccinesName;
        this.vaccinesDescription = vaccinesDescription;
    }

    public String getVaccinesName() {
        return vaccinesName;
    }

    public void setVaccinesName(String vaccinesName) {
        this.vaccinesName = vaccinesName;
    }

    public String getVaccinesDescription() {
        return vaccinesDescription;
    }

    public void setVaccinesDescription(String vaccinesDescription) {
        this.vaccinesDescription = vaccinesDescription;
    }

    public long getVaccinesId() {
        return vaccinesId;
    }

    public void setVaccinesId(long vaccinesId) {
        this.vaccinesId = vaccinesId;
    }

    @Override
    public String toString() {
        return "Vaccine{" +
                "vaccinesId=" + vaccinesId +
                ", vaccinesName='" + vaccinesName + '\'' +
                ", vaccinesDescription='" + vaccinesDescription + '\'' +
                '}';
    }

}
