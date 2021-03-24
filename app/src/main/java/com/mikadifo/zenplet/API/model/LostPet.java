package com.mikadifo.zenplet.API.model;

public class LostPet {

    private Long lostPetId;

    private String lostPetAdditionalInfo;

    public LostPet() {
    }

    public LostPet(Long lostPetId,String lostPetAdditionalInfo) {
        this.lostPetId= lostPetId;
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

    @Override
    public String toString() {
        return "LostPet{" +
                "lostPetId=" + lostPetId +
                ", lostPetAdditionalInfo='" + lostPetAdditionalInfo + '\'' +
                '}';
    }
}
