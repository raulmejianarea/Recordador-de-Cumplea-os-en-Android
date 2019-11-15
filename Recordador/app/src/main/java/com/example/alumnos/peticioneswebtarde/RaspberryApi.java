package com.example.alumnos.peticioneswebtarde;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RaspberryApi {
    @GET("today.php")
    Call<String> encender();

}
