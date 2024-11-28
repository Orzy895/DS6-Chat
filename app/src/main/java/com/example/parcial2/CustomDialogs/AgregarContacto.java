package com.example.parcial2.CustomDialogs;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.parcial2.Models.Contacto;
import com.example.parcial2.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AgregarContacto extends AppCompatDialogFragment {

    EditText txtNombre, txtApellido, txtNumero, txtFoto;
    int numero, foto, cantContactos;
    String nombre, apellido;

    List<Contacto> contactos;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstance){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.agregar_contacto, null);

        txtNombre = view.findViewById(R.id.txtNombre);
        txtApellido = view.findViewById(R.id.txtApellido);
        txtNumero = view.findViewById(R.id.txtTelefono);
        txtFoto = view.findViewById(R.id.txtFoto);

        builder.setView(view).setTitle("Agregar nuevo contacto")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Crear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Crear(view);
                    }
                });

        return builder.create();
    }

    public void guardarContactoNuevo(){
        SharedPreferences pref = getActivity().getSharedPreferences("Contactos", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        Set<String> listaContactos = pref.getStringSet("listaContactos", new HashSet<>());
        List<Contacto> contactosExistentes = new ArrayList<>();
        for (String contacto: listaContactos){
            contactosExistentes.add(Contacto.deformatearContacto(contacto));
        }
        if(contactos!= null && !contactos.isEmpty()){
            contactosExistentes.addAll(contactos);
        }
        Set<String> listaActualizado = new HashSet<>();
        for(Contacto contacto : contactosExistentes){
            listaActualizado.add(contacto.formatearContacto());
        }
        editor.putStringSet("listaContactos", listaActualizado);
        editor.apply();
    }


    public void Crear(View view) {
        nombre = txtNombre.getText().toString();
        apellido = txtApellido.getText().toString();
        numero = Integer.parseInt(txtNumero.getText().toString());
        foto = Integer.parseInt(txtFoto.getText().toString());
        if (verificarCampos(nombre, apellido, numero, foto)) {
            Contacto contacto = new Contacto(nombre, apellido, foto, numero, cantContactos+1);
            contactos.add(contacto);
            guardarContactoNuevo();
            Log.i(TAG, "Crear: Contacto");
            Toast.makeText(getActivity(),"Usuario Creado", Toast.LENGTH_LONG).show();
        }
    }

    public boolean verificarCampos(String nombre, String apellido, int numero, int foto){
        if(TextUtils.isEmpty(nombre)){
            txtNombre.setError("Introduzca el nombre");
            return false;
        }if(TextUtils.isEmpty(apellido)){
            txtApellido.setError("Introduzca el apellido");
            return false;
        }if(numero == 0){
            txtNumero.setError("Introduzca el numero");
            return false;
        }if(foto == 0){
            txtFoto.setError("Introduzca el foto");
            return false;
        }if(foto <0 || foto >6){
            txtFoto.setError("La foto debe ser entre 1 a 6");
            return false;
        }
        return true;
    }

    public void setContactos(List<Contacto> contactos, int cantContactos) {
        this.contactos = contactos;
        this.cantContactos = cantContactos;
    }

}
