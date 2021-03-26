package com.mikadifo.zenplet.API.model;

public class Owner {
    private long ownerId;
    private String ownerName;
    private String ownerEmail;
    private String ownerPassword;
    private String ownerPhoneNumber;
    private String token;

    public Owner() {
    }

    public Owner(long ownerId, String ownerName, String ownerEmail, String ownerPassword, String ownerPhoneNumber, String token) {
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.ownerEmail = ownerEmail;
        this.ownerPassword = ownerPassword;
        this.ownerPhoneNumber = ownerPhoneNumber;
        this.token = token;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getOwnerPassword() {
        return ownerPassword;
    }

    public void setOwnerPassword(String ownerPassword) {
        this.ownerPassword = ownerPassword;
    }

    public String getOwnerPhoneNumber() {
        return ownerPhoneNumber;
    }

    public void setOwnerPhoneNumber(String ownerPhoneNumber) {
        this.ownerPhoneNumber = ownerPhoneNumber;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "ownerId=" + ownerId +
                ", ownerName='" + ownerName + '\'' +
                ", ownerEmail='" + ownerEmail + '\'' +
                ", ownerPassword='" + ownerPassword + '\'' +
                ", ownerPhoneNumber='" + ownerPhoneNumber + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
