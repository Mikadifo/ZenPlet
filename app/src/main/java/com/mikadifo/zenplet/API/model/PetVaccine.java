package com.mikadifo.zenplet.API.model;

public class PetVaccine {

    private Long PetVaccinesId ;

    private String petVaccineDate;

    private String petVaccineNext;

    public Long getPetVaccinesId() {
        return PetVaccinesId;
    }

    public void setPetVaccinesId(Long petVaccinesId) {
        PetVaccinesId = petVaccinesId;
    }

    public String getPetVaccineDate() {
        return petVaccineDate;
    }

    public void setPetVaccineDate(String petVaccineDate) {
        this.petVaccineDate = petVaccineDate;
    }

    public String getPetVaccineNext() {
        return petVaccineNext;
    }

    public void setPetVaccineNext(String petVaccineNext) {
        this.petVaccineNext = petVaccineNext;
    }

    public PetVaccine(String petVaccineDate) {
        this.petVaccineDate = petVaccineDate;
    }

    public PetVaccine(Long petVaccinesId, String petVaccineDate, String petVaccineNext) {
        PetVaccinesId = petVaccinesId;
        this.petVaccineDate = petVaccineDate;
        this.petVaccineNext = petVaccineNext;

    }

    @Override
    public String toString() {
        return "PetVaccine{" +
                "PetVaccinesId=" + PetVaccinesId +
                ", petVaccineDate='" + petVaccineDate + '\'' +
                ", petVaccineNext='" + petVaccineNext + '\'' +
                '}';
    }
}
