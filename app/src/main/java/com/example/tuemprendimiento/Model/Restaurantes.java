package com.example.tuemprendimiento.Model;

import java.util.ArrayList;

public class Restaurantes {

  private int id ;

    private  String Nombre;

    private  String Ubicacion;

    private String Imagen;

    private int NumImagenes;


    private int Rating;

    public Restaurantes() {
    }

    public Restaurantes(String nombre, String ubicacion, String imagen, int numImagenes) {
        Nombre = nombre;
        Ubicacion = ubicacion;
        Imagen = imagen;
        NumImagenes = numImagenes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        Ubicacion = ubicacion;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    public int getRating() {
        return Rating;
    }

    public void setRating(int rating) {
        Rating = rating;
    }

    public int getNumImagenes() {
        return NumImagenes;
    }

    public void setNumImagenes(int numImagenes) {
        NumImagenes = numImagenes;
    }
}
