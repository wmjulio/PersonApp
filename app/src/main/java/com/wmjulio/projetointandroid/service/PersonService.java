package com.wmjulio.projetointandroid.service;

import com.wmjulio.projetointandroid.model.Person;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface PersonService {
    @GET("persons")
    Call<List<Person>> findPersons();

    @GET("persons/{id}")
    Call<Person> findPerson(@Path("id") Long id);

    @POST("persons")
    Call<Person> addPerson(@Body Person person);

    @PUT("persons/{id}")
    Call<Person> attPerson(@Path("id") Long id, @Body Person person);

    @DELETE("persons/{id}")
    Call<ResponseBody> deletePerson(@Path("id") Long id);
}
