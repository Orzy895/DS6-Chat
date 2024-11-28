package com.example.parcial2.Models;

import android.os.Bundle;

public class Mensaje {
    private String mensaje;
    private int recipiente, sender, id;
    private long time;

    public Mensaje(){}

    public Mensaje(int id,int recipiente, String mensaje, int sender, long time) {
        this.id = id;
        this.recipiente = recipiente;
        this.mensaje = mensaje;
        this.sender = sender;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public int getRecipiente() {
        return recipiente;
    }

    public void setRecipiente(int recipiente) {
        this.recipiente = recipiente;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String formatearMensaje() {
        return id + "," + recipiente + "," + sender + "," + mensaje + "," + time;
    }

    public static Mensaje deformatearMensaje(String contactoFormateado) {
        String[] parts = contactoFormateado.split(",");
        int id = Integer.parseInt(parts[0]);
        int recipiente = Integer.parseInt(parts[1]);
        int sender = Integer.parseInt(parts[2]);
        String mensaje = parts[3];
        long time = Long.parseLong(parts[4]);
        return new Mensaje(id, recipiente, mensaje, sender, time);
    }

    public Bundle toBundle(){
        Bundle b = new Bundle();
        b.putInt("id", getId());
        b.putInt("recipiente", getRecipiente());
        b.putInt("sender", getSender());
        b.putString("mensaje", getMensaje());
        b.putLong("time", getTime());
        return b;
    }

    public Mensaje toClass(Bundle b){
        return new Mensaje(
                b.getInt("id"),
                b.getInt("recipiente"),
                b.getString("mensaje"),
                b.getInt("sender"),
                b.getLong("time")
        );
    }
}
