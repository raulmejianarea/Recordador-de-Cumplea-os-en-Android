package com.example.alumnos.peticioneswebtarde;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Agregar_cumple extends AppCompatActivity implements View.OnClickListener {

    Retrofit retrofit;
    TonteriasApi tonteriasApi;

    DatePicker calendario;
    EditText edit_nombre;
    ImageView imagen;
    ImageView imagen1;
    ImageView imagen2;
    ImageView imagen3;
    ImageView imagen4;
    ImageView imagenElegida;
    long date;




    Button buttonañadir_imagen;
    Button button_cancelar;
    Button buutton_guardar;

    String image;
    String imageAmigos = "http://docs.google.com/uc?export=open&id=17vlrZbE7z5vc-MAK-C8SBNQsNLhOcoRe";
    String imageGato = "http://docs.google.com/uc?export=open&id=1x8yH36yVP22_C7TX-w28-EOG4KuzKWTn";
    String imageBananas = "http://docs.google.com/uc?export=open&id=1UJLP6L_7NnjSA4vA-_7knyofc-M9FWwX";
    String imageGente = "http://docs.google.com/uc?export=open&id=1_vV36NlPnEgIaJLxVPnOiQzxZtROLljE";
    String imagePerros = "http://docs.google.com/uc?export=open&id=1NUEe6F52qnkBc7lFUXubCbLZdtj2h_Qi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_cumple);


        edit_nombre = findViewById(R.id.name);
        imagen = findViewById(R.id.image);
        imagen1 = findViewById(R.id.imagen1);
        imagen2 = findViewById(R.id.imagen2);
        imagen3 = findViewById(R.id.imagen3);
        imagen4 = findViewById(R.id.imagen4);
        imagenElegida = findViewById(R.id.imagenseleccionada);
        calendario = findViewById(R.id.dateDP);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://tonterias.herokuapp.com/api/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        tonteriasApi = retrofit.create(TonteriasApi.class);



        buttonañadir_imagen = findViewById(R.id.añadir_imagen);
        buutton_guardar = findViewById(R.id.guardar);
        button_cancelar = findViewById(R.id.cancelar);



        final Calendar calendar1 = Calendar.getInstance();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            calendario.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
                        calendar1.set(Calendar.YEAR, year);

                        calendar1.set(Calendar.MONTH, month);

                        calendar1.set(Calendar.DAY_OF_MONTH, day);


                        date = calendar1.getTimeInMillis();
                }
            });
        }

        Picasso.get().load(imageAmigos).into(imagen);
        Picasso.get().load(imageGato).into(imagen1);
        Picasso.get().load(imageBananas).into(imagen2);
        Picasso.get().load(imageGente).into(imagen3);
        Picasso.get().load(imagePerros).into(imagen4);

        imagen.setOnClickListener(this);
        imagen1.setOnClickListener(this);
        imagen2.setOnClickListener(this);
        imagen3.setOnClickListener(this);
        imagen4.setOnClickListener(this);




        button_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.image:
                image = imageAmigos;
                Picasso.get().load(imageAmigos).into(imagenElegida);
                break;

            case R.id.imagen1:
                image = imageGato;
                Picasso.get().load(imageGato).into(imagenElegida);
                break;

            case R.id.imagen2:
                image = imageBananas;
                Picasso.get().load(imageBananas).into(imagenElegida);
                break;

            case R.id.imagen3:
                image = imageGente;
                Picasso.get().load(imageGente).into(imagenElegida);
                break;

            case R.id.imagen4:
                image = imagePerros;
                Picasso.get().load(imagePerros).into(imagenElegida);
                break;

        }
    }

    public void enviarinfo (View view) {
        String name = edit_nombre.getText().toString();


            if (!name.isEmpty() && !image.isEmpty()) {
                Call<String> call = tonteriasApi.postBirthday(name, image , date);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String state = response.body();
                        Toast.makeText(Agregar_cumple.this, state, Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(Agregar_cumple.this, "Error de conexión", Toast.LENGTH_SHORT).show();

                    }
                });


            }


    }



}
