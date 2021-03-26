package com.mikadifo.zenplet.API.service;

import com.mikadifo.zenplet.API.model.LostPet;
import com.mikadifo.zenplet.API.model.Pet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LostPetService {

    @GET("/api/lost-pet")
    Call<List<Pet>> getLostPets();

    @POST("/api/save-lost-pet")
    Call<LostPet> saveLostPet(@Body LostPet lostpet);

    @PUT("/api/edit-lost-pet/{id}")
    Call<LostPet> updateLostPet(@Path("id") long id, @Body LostPet lostpet);

    @DELETE("/api/delete-lost-pet/{id}")
    Call deleteLostPet(@Path("id") long id);

}
