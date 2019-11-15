package com.example.alumnos.peticioneswebtarde;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    Retrofit retrofit;
    Retrofit retrofit2;
    TonteriasApi tonteriasApi;
    TextView answerTV;
    TextView answerTV2;
    ImageView imageView;

    RaspberryApi raspberryApi;





    Button button_añadirCumple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        answerTV = findViewById(R.id.answerTV);
        answerTV2 = findViewById(R.id.answerTV2);



        imageView = findViewById(R.id.imageView);
        button_añadirCumple = findViewById(R.id.añadircumple);


        retrofit = new Retrofit.Builder()
                .baseUrl("https://tonterias.herokuapp.com/api/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        tonteriasApi = retrofit.create(TonteriasApi.class);

        retrofit2 = new Retrofit.Builder()
                .baseUrl("http://192.168.4.102/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        raspberryApi = retrofit2.create(RaspberryApi.class);



        button_añadirCumple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Agregar_cumple.class);
                startActivityForResult(intent,1);
            }
        });

        cumpleañosGET();
    }





    public void cumpleañosGET (){
        Call<ArrayList<Tonteria>> call = tonteriasApi.cumpleañosGET();
        call.enqueue(new Callback<ArrayList<Tonteria>>() {
            @Override
            public void onResponse(Call<ArrayList<Tonteria>> call, Response<ArrayList<Tonteria>> response) {

                ArrayList<Tonteria> tonterias = response.body();

                int ultimo = tonterias.size()-1;
                Tonteria tonteria =  tonterias.get(ultimo);


                    Log.d("*******", tonteria.name);

                    answerTV.setText(tonteria.name);
                    long fecha =tonteria.date;
                    getTimeStamp(fecha);
                    Picasso.get().load(tonteria.image).into(imageView);

                encender();

            }

            @Override
            public void onFailure(Call<ArrayList<Tonteria>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public String getTimeStamp(long timeinMillies) {

            String date = null;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            date = formatter.format(new Date(timeinMillies));
            System.out.println("Today is " + date);
            answerTV2.setText(date);
            return date;

    }
    public void encender (){
        Call<String> call = raspberryApi.encender();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String resultado = response.body();


            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
