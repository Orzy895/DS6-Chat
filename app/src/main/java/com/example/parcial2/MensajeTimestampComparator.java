package com.example.parcial2;

import com.example.parcial2.Models.Mensaje;

import java.util.Comparator;

public class MensajeTimestampComparator implements Comparator<Mensaje> {
    @Override
    public int compare(Mensaje mensaje1, Mensaje mensaje2) {
        // Compare timestamps in ascending order
        return Long.compare(mensaje1.getTime(), mensaje2.getTime());
    }
}
