package com.mikadifo.zenplet.API.model;

public class LostPet {

    private Long lostPetId;

    private Owner owner;

    private Pet pet;

    private String lostPetAdditionalInfo;

    public LostPet() {
    }

    public LostPet(Long lostPetId,String lostPetAdditionalInfo) {
        this.lostPetId= lostPetId;
        this.lostPetAdditionalInfo = lostPetAdditionalInfo;
    }

    public LostPet(Owner owner, Pet pet, String lostPetAdditionalInfo) {
        this.owner = owner;
        this.pet = pet;
        this.lostPetAdditionalInfo = lostPetAdditionalInfo;
    }

    public String getLostPetAdditionalInfo() {
        return lostPetAdditionalInfo;
    }

    public Long getLostPetId() {
        return lostPetId;
    }

    public void setLostPetId(Long lostPetId) {
        this.lostPetId = lostPetId;
    }

    public void setLostPetAdditionalInfo(String lostPetAdditionalInfo) {
        this.lostPetAdditionalInfo = lostPetAdditionalInfo;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    @Override
    public String toString() {
        return "LostPet{" +
                "lostPetId=" + lostPetId +
                ", lostPetAdditionalInfo='" + lostPetAdditionalInfo + '\'' +
                '}';
    }
}
