package com.example.alumnos.peticioneswebtarde;

public class Tonteria {
    String name;
    String image;
    long date;

    public Tonteria (String name, String image, long date){

        this.name = name;
        this.image = image;
        this.date = date;

    }

    public String getName(){
        return name;
    }
    public String getImage(){
        return image;
    }
    public long getDate(){
        return date;
    }
}
