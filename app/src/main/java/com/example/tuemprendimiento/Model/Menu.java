package com.example.tuemprendimiento.Model;

public class Menu {

    private int IdMenu;

    private  String Nombre;

    private  String Imagen;

    public Menu() {

    }

    public Menu(String nombre, String imagen) {
        this.Nombre = nombre;
        this.Imagen = imagen;
    }

    public int getIdMenu() {
        return IdMenu;
    }

    public void setIdMenu(int idMenu) {
        IdMenu = idMenu;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }
}
