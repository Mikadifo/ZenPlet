package com.mikadifo.zenplet.API.service;

import com.mikadifo.zenplet.API.model.Vaccine;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface VaccineService {
    @GET("/api/vaccines")
    Call<List<Vaccine>> getVaccines();

    @POST("/api/save-vaccine")
    Call<Vaccine> saveVaccines(@Body Vaccine vaccine);

    @GET("/api/vaccine/id")
    Call<Vaccine> getVaccineById(@Query("id") long id);

//    @GET("/api/vaccine/name")
//    Call<Vaccine> getVaccineById(@Query("name") String name);

    @PUT("/api/edit-vaccine")
    Call<Vaccine> updateVaccine(@Query("id") long id, @Body Vaccine vaccine);

    @DELETE("/api/delete-vaccine")
    Call deleteVaccine(@Query("id") long id);
}
