package com.mikadifo.zenplet.API.model;

public class LostPetId {
    private Long petId;
    private Long ownerId;

    public LostPetId() {
    }

    public LostPetId(Long petId, Long ownerId) {
        this.petId = petId;
        this.ownerId = ownerId;
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
