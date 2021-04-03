package com.mikadifo.zenplet.API.model;

import android.widget.TextView;

import java.util.Arrays;

public class Pet {

    private long petId;
    private String petName;
    private String petImage;
    private String petBreed;
    private String petSize;
    private String petGenre;
    private String petBirthdate;
    private Owner petOwner;


    public Pet() {
    }

    public Pet(long petId, String petName, String petImage, String petBreed, String petSize, String petGenre, String petBirthdate, Owner petOwner) {
        this.petId = petId;
        this.petName = petName;
        this.petImage = petImage;
        this.petBreed = petBreed;
        this.petSize = petSize;
        this.petGenre = petGenre;
        this.petBirthdate = petBirthdate;
        this.petOwner = petOwner;
    }

    public long getPetId() {
        return petId;
    }

    public void setPetId(long petId) {
        this.petId = petId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetImage() {
        return petImage;
    }

    public void setPetImage(String petImage) {
        this.petImage = petImage;
    }

    public String getPetBreed() {
        return petBreed;
    }

    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }

    public String getPetSize() {
        return petSize;
    }

    public void setPetSize(String petSize) {
        this.petSize = petSize;
    }

    public String getPetGenre() {
        return petGenre;
    }

    public void setPetGenre(String petGenre) {
        this.petGenre = petGenre;
    }

    public Owner getPetOwner() {
        return petOwner;
    }

    public void setPetOwner(Owner petOwner) {
        this.petOwner = petOwner;
    }

    public String getPetBirthdate() {
        return petBirthdate;
    }

    public void setPetBirthdate(String petBirthdate) {
        this.petBirthdate = petBirthdate;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "petId=" + petId +
                ", petName='" + petName + '\'' +
                ", petBreed='" + petBreed + '\'' +
                ", petSize='" + petSize + '\'' +
                ", petGenre='" + petGenre + '\'' +
                ", petBirthdate='" + petBirthdate + '\'' +
                '}';
    }
}
