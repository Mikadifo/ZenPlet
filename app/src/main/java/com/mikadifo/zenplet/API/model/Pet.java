package com.mikadifo.zenplet.API.model;

import android.widget.TextView;

import java.util.Arrays;

public class Pet {

    private long petId;
    private String petName;
    private byte[] petImage;
    private String petBreed;
    private String petSize;
    private String petGenre;

    public Pet() {
    }

    public Pet(long petId, String petName, byte[] petImage, String petBreed, String petSize, String petGenre) {
        this.petId = petId;
        this.petName = petName;
        this.petImage = petImage;
        this.petBreed = petBreed;
        this.petSize = petSize;
        this.petGenre = petGenre;
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

    public byte[] getPetImage() {
        return petImage;
    }

    public void setPetImage(byte[] petImage) {
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

    @Override
    public String toString() {
        return "Pet{" +
                "petId=" + petId +
                ", petName='" + petName + '\'' +
                ", petImage=" + Arrays.toString(petImage) +
                ", petBreed='" + petBreed + '\'' +
                ", petSize='" + petSize + '\'' +
                ", petGenre='" + petGenre + '\'' +
                '}';
    }
}
