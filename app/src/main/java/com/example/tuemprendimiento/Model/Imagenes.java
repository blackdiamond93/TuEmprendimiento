package com.example.tuemprendimiento.Model;

import java.util.ArrayList;

public class Imagenes {
    private  String Imagen;
    private  int idImagenes;

    public Imagenes() {

    }

    public Imagenes(String imagen) {
        Imagen = imagen;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    public int getIdImagenes() {
        return idImagenes;
    }

    public void setIdImagenes(int idImagenes) {
        this.idImagenes = idImagenes;
    }

}
