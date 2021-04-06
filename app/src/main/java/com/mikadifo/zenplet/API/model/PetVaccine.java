package com.mikadifo.zenplet.API.model;

public class PetVaccine {



    private String petVaccineDate;

    private String petVaccineNext;

    private PetVaccinesId id;

    private Vaccine vaccine;

    private Pet pet;


    public void setPetVaccinesId(Long petVaccinesId) {
        id = id;
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

    public void setPetVaccinesId(com.mikadifo.zenplet.API.model.PetVaccinesId Id) {
        this.id = id;
    }

    public PetVaccine(String petVaccineDate, String petVaccineNext, PetVaccinesId id, Vaccine vaccine, Pet pet) {
        this.petVaccineDate = petVaccineDate;
        this.petVaccineNext = petVaccineNext;
        this.id = id;
        this.vaccine = vaccine;
        this.pet = pet;
    }

    public PetVaccinesId getId() {
        return id;
    }

    public void setId(PetVaccinesId id) {
        this.id = id;
    }

    public Vaccine getVaccine() {
        return vaccine;
    }

    public void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    @Override
    public String toString() {
        return "PetVaccine{" +
                "PetVaccinesId=" + id +
                ", petVaccineDate='" + petVaccineDate + '\'' +
                ", petVaccineNext='" + petVaccineNext + '\'' +
                '}';
    }
}
