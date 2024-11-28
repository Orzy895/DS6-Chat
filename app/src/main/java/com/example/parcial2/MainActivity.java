package com.example.parcial2;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.parcial2.CustomDialogs.AgregarContacto;
import com.example.parcial2.CustomDialogs.CambiarUsuario;
import com.example.parcial2.ListViewAdapters.ChatsListViewAdapter;
import com.example.parcial2.Models.Contacto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements CambiarUsuario.CambiarUsuarioListener{

    List<Contacto> contactos;
    List<Contacto> todosContactos = new ArrayList<>();
    ListView lsvMensajes;
    int usuarioActual, cantContactos = 0;
    String titulo;
    Contacto usuario, usuarioAhora;
    boolean reloadNeeded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InicializarControles();
        cargarContactos();
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Do nothing to disable the back button
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    public void InicializarControles() {
        lsvMensajes = (ListView) findViewById(R.id.lsvMensajes);

        Intent intent = getIntent();
        if (intent != null) {
            usuarioActual = intent.getIntExtra("usuarioActual", 1);
        } else {
            usuarioActual = 1;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(reloadNeeded){
            cargarContactos();
            reloadNeeded = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setTitle(titulo);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.editarPerfil) {
            Intent i = new Intent(this, EditarPerfil.class);
            i.putExtras(usuarioAhora.toBundle());
            i.putExtra("usuarioActual", usuarioActual);
            startActivity(i);
            return true;
        }
        if (item.getItemId() == R.id.agregarContacto) {
            AgregarContacto agregarContacto = new AgregarContacto();
            agregarContacto.setContactos(contactos, cantContactos);
            agregarContacto.show(getSupportFragmentManager(), "Agregar Contacto");
            cantContactos++;
            reloadNeeded = true;
            return true;
        }
        if (item.getItemId() == R.id.cambiarUsuario) {
            CambiarUsuario cambiarUsuarioDialog = new CambiarUsuario(this);
            cambiarUsuarioDialog.setCambiarUsuarioListener(this, todosContactos);
            cambiarUsuarioDialog.show(getSupportFragmentManager(), "Cambiar contacto");
            return true;
        }
        return false;
    }

    @Override
    public void onContactoSeleccionado(int idContacto) {
        usuarioActual = idContacto;
        cargarContactos();
    }

    public void cargarContactos() {
        SharedPreferences pref = getSharedPreferences("Contactos", MODE_PRIVATE);
        Set<String> listaContactos = pref.getStringSet("listaContactos", new HashSet<>());
        contactos = new ArrayList<>();
        todosContactos.clear();
        for (String contacto : listaContactos) {
            Contacto contact = Contacto.deformatearContacto(contacto);
            if (contact.getId() != usuarioActual) {
                contactos.add(contact);
            } else {
                usuarioAhora = contact;
                titulo = contact.getNombre() + " " + contact.getApellido();
            }
            todosContactos.add(contact);
            cantContactos++;
        }
        ChatsListViewAdapter adapter = new ChatsListViewAdapter(this, contactos);
        lsvMensajes.setAdapter(adapter);
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

    public void irContactos(View view) {
        Intent i = new Intent(this, ListaContactos.class);
        i.putExtra("usuarioActual", usuarioActual);
        startActivity(i);
    }

}