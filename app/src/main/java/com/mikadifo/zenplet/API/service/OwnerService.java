package com.mikadifo.zenplet.API.service;

import com.mikadifo.zenplet.API.model.Owner;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface OwnerService {
    @GET("/api/login-owner")
    Call<Owner> getLogin(@Query("login") String login, @Query("password")String password);

    @GET("/api/owners")
    Call<List<Owner>> getOnwers();

    @POST("/api/save-owner")
    Call<Owner> saveOwner(@Body Owner owner);

    @GET("/api/owner/id")
    Call<Owner> getOwnerById(@Query("id") long id);

    @GET("/api/owner/name")
    Call<List<Owner>> getOwnerByName(@Query("name") String name);

    @PUT("/api/edit-owner")
    Call<Owner> updateOwner(@Query("id") long id, @Body Owner owner);

    @DELETE("/api/delete-owner")
    Call deleteOwner(@Query("id") long id);
}
