package com.mikadifo.zenplet.API.service;

import com.mikadifo.zenplet.API.model.PetVaccine;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PetVaccineService {

    @GET("/api/pet-vaccines")
    Call<List<PetVaccine>> getPetVaccines();

    @POST("/api/save-pet-vacines")
    Call<PetVaccine> savePetVaccines(@Body PetVaccine petVaccine);

    @PUT("/api/edit-pet-vaccines/{petId}/{vaccineId}")
    Call<PetVaccine> updatePetVaccines(@Path("petId") long petId, @Path("vaccineId") long vaccineId, @Body PetVaccine petVaccine);

    @DELETE("/api/delete-pet-vaccines/{id}")
    Call deletePetVaccines(@Path("id") long id);

}
