package com.mikadifo.zenplet.API.service;

import com.mikadifo.zenplet.API.model.Pet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PetService {
    @GET("/api/pets")
    Call<List<Pet>> getPets();

    @POST("/api/save-pet")
    Call<Pet> savePet(@Body Pet pet);

    @GET("/api/pet/id/{id}")
    Call<Pet> getPetById(@Path("id") long id);

    @GET("/api/pet/name/{name}")
    Call<List<Pet>> getPetByName(@Path("name") String name);

    @PUT("/api/edit-pet/{id}")
    Call<Pet> updatePet(@Path("id") long id, @Body Pet pet);

    @DELETE("/api/delete-pet/{id}")
    Call<Void> deletePet(@Path("id") long id);
}
