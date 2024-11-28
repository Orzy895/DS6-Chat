package com.example.parcial2.Models;

import android.os.Bundle;

public class Contacto {

    String nombre, apellido;
    int foto, numeroTelefono, id;

    public Contacto(){}

    public Contacto(String nombre, String apellido, int foto, int numeroTelefono, int id) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.foto = foto;
        this.numeroTelefono = numeroTelefono;
        this.id = id;
    }

    public int getNumeroTelefono() {
        return numeroTelefono;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String formatearContacto() {
        return id + "," + nombre + "," + apellido + "," + foto + "," + numeroTelefono;
    }

    public static Contacto deformatearContacto(String contactoFormateado) {
        String[] parts = contactoFormateado.split(",");
        int id = Integer.parseInt(parts[0]);
        String nombre = parts[1];
        String apellido = parts[2];
        int foto = Integer.parseInt(parts[3]);
        int numeroTelefono = Integer.parseInt(parts[4]);
        return new Contacto(nombre, apellido, foto, numeroTelefono, id);
    }

    public Bundle toBundle(){
        Bundle b = new Bundle();
        b.putString("nombre", getNombre());
        b.putString("apellido", getApellido());
        b.putInt("foto", getFoto());
        b.putInt("telefono", getNumeroTelefono());
        b.putInt("id", getId());
        return b;
    }

    public Contacto toClass(Bundle b){
        return new Contacto(
                b.getString("nombre"),
                b.getString("apellido"),
                b.getInt("foto"),
                b.getInt("telefono"),
                b.getInt("id")
        );
    }

}
