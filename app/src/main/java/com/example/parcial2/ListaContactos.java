package com.example.parcial2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.parcial2.ListViewAdapters.ChatsListViewAdapter;
import com.example.parcial2.ListViewAdapters.ContactoListViewAdapter;
import com.example.parcial2.Models.Contacto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListaContactos extends AppCompatActivity {
    int usuarioActual;
    Contacto contacto, usuario;
    List<Contacto> contactos;
    ListView lsvListaContactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contactos);
        InicializarControladores();
        cargarContactos();
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Do nothing to disable the back button
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    public void InicializarControladores(){
        lsvListaContactos = (ListView)findViewById(R.id.lsvListaContactos);
        Bundle datos = getIntent().getExtras();
        if (datos != null) {
            usuarioActual = datos.getInt("usuarioActual");
        }
    }

    public void cargarContactos() {
        SharedPreferences pref = getSharedPreferences("Contactos", MODE_PRIVATE);
        Set<String> listaContactos = pref.getStringSet("listaContactos", new HashSet<>());
        contactos = new ArrayList<>();
        for (String contacto : listaContactos) {
            Contacto contact = Contacto.deformatearContacto(contacto);
            if (contact.getId() != usuarioActual) {
                contactos.add(contact);
            }
        }
        ContactoListViewAdapter adapter = new ContactoListViewAdapter(this, contactos);
        lsvListaContactos.setAdapter(adapter);
    }

    public void irChat(int idContactoDestino) {
        for(Contacto contacto : contactos){
            if(contacto.getId() == idContactoDestino){
                usuario = contacto;
            }
        }
        if (usuario != null) {
            Intent i = new Intent(this, Chat.class);
            i.putExtras(usuario.toBundle());
            i.putExtra("usuarioActual", usuarioActual);
            i.putExtra("usuarioDestino", idContactoDestino);
            startActivity(i);
        }
    }

    public void retornar(View view) {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("usuarioActual", usuarioActual);
        startActivity(i);
    }
}