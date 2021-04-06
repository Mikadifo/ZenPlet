package com.mikadifo.zenplet.API.model;

public class LostPet {

    private LostPetId id = new LostPetId();
    private Pet pet;
    private Owner owner;
    private String lostPetAdditionalInfo;

    public LostPet() {
    }

    public LostPet(LostPetId id, Pet pet, Owner owner, String lostPetAdditionalInfo) {
        this.id = id;
        this.pet = pet;
        this.owner = owner;
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

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public void setLostPetAdditionalInfo(String lostPetAdditionalInfo) {
        this.lostPetAdditionalInfo = lostPetAdditionalInfo;
    }

    public Pet getPet() {
        return pet;
    }

    public LostPetId getId() {
        return id;
    }

    public void setId(LostPetId id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "LostPet{" +
                "pet=" + pet.getPetName() +
                ", lostPetId=" +
                ", owner=" + owner.getOwnerName() +
                ", lostPetAdditionalInfo='" + lostPetAdditionalInfo + '\'' +
                '}';
    }
}
