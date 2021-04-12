package com.mikadifo.zenplet.API.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;

public interface PetsFoundService {
    @GET("/api/pets-found")
    Call<Long> getPetsFound();

    @PUT("api/add-pet-found")
    Call<Long> addPetsFound();

    @PUT("api/sub-pet-found")
    Call<Long> subPetsFound();
}
