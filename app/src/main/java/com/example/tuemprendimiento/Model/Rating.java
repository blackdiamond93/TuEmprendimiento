package com.example.tuemprendimiento.Model;

public class Rating {
    private String Nombre;
    private  int RateRating;

    public Rating() {
    }

    public Rating(String nombre, int rateRating) {
        Nombre = nombre;
        RateRating = rateRating;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getRateRating() {
        return RateRating;
    }

    public void setRateRating(int rateRating) {
        RateRating = rateRating;
    }

}
