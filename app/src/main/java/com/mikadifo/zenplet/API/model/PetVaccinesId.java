package com.mikadifo.zenplet.API.model;

public class PetVaccinesId {
    private Long petId;
    private Long vaccineId;

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public Long getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(Long vaccineId) {
        this.vaccineId = vaccineId;
    }

    public PetVaccinesId(Long petId, Long vaccineId) {
        this.petId = petId;
        this.vaccineId = vaccineId;
    }
    public PetVaccinesId() { }
}
