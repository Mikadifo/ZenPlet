package com.mikadifo.zenplet.API.service;

import com.mikadifo.zenplet.API.model.Vaccine;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface VaccineService {
    @GET("/api/vaccines")
    Call<List<Vaccine>> getVaccines();

    @POST("/api/save-vaccine")
    Call<Vaccine> saveVaccines(@Body Vaccine vaccine);

    @GET("/api/vaccine/id/{id}")
    Call<Vaccine> getVaccineById(@Path("id") long id);

//    @GET("/api/vaccine/name/{name}")
//    Call<Vaccine> getVaccineById(@Path("name") String name);

    @PUT("/api/edit-vaccine/{id}")
    Call<Vaccine> updateVaccine(@Path("id") long id, @Body Vaccine vaccine);

    @DELETE("/api/delete-vaccine/{id}")
    Call deleteVaccine(@Path("id") long id);
}
