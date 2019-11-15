package com.example.alumnos.peticioneswebtarde;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TonteriasApi {

    @GET("tonterias")
    Call<ArrayList<Tonteria>> getTonterias();

    @FormUrlEncoded
    @POST("bday")
    Call<String> postBirthday(@Field("name") String name, @Field("image") String image, @Field("date") long date);
    @GET("bdays")
    Call<ArrayList<Tonteria>> cumpleañosGET();
}
