package com.wmjulio.projetointandroid;

import com.wmjulio.projetointandroid.service.PersonService;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitConfig {

    private final Retrofit retrofit;

    public RetrofitConfig() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://wmjulio.herokuapp.com/api/v1.0/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public PersonService getPersonService() {
        return this.retrofit.create(PersonService.class);
    }
}
