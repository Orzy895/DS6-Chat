package com.example.parcial2;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.parcial2.ListViewAdapters.ChatsListViewAdapter;
import com.example.parcial2.ListViewAdapters.MensajeListViewAdapter;
import com.example.parcial2.Models.Contacto;
import com.example.parcial2.Models.Mensaje;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class Chat extends AppCompatActivity {

    ImageView imgChatFoto;
    ListView lstChatHistorial;
    EditText txtChatEscribir;
    TextView lblChatNombre;
    int usuarioActual, usuarioDestino, totalMensajes = 0;
    Contacto contacto;
    List<Mensaje> mensajes;
    MensajeListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        InicializarControles();
        cargarMensajes();
    }

    public void InicializarControles() {
        imgChatFoto = (ImageView) findViewById(R.id.imgChatFoto);
        lstChatHistorial = (ListView) findViewById(R.id.lstChatHistorial);
        txtChatEscribir = (EditText) findViewById(R.id.txtChatEscribir);
        lblChatNombre = (TextView) findViewById(R.id.lblChatNombre);

        Bundle datos = getIntent().getExtras();
        if (datos != null) {
            usuarioActual = datos.getInt("usuarioActual");
            usuarioDestino = datos.getInt("usuarioDestino");
            contacto = new Contacto(
                    datos.getString("nombre"),
                    datos.getString("apellido"),
                    datos.getInt("foto"),
                    datos.getInt("telefono"),
                    datos.getInt("id")
            );
            lblChatNombre.setText(contacto.getNombre() + " " + contacto.getApellido());
            String src = "foto" + contacto.getFoto();
            int fotoSrc = getResources().getIdentifier(src, "drawable", getPackageName());
            imgChatFoto.setImageResource(fotoSrc);
        } else {
            Log.i(TAG, "COMO QUE NO HAY USUARIO D: QUE PUTA");
        }
    }

    public void cargarMensajes() {
        SharedPreferences pref = getSharedPreferences("Mensajes", MODE_PRIVATE);
        Set<String> listaContactos = pref.getStringSet("listaMensajes", new HashSet<>());
        mensajes = new ArrayList<>();
        for (String mensaje : listaContactos) {
            Mensaje msg = Mensaje.deformatearMensaje(mensaje);
            if (msg.getRecipiente() == usuarioActual || msg.getSender() == usuarioActual) {
                if (msg.getRecipiente() == usuarioDestino || msg.getSender() == usuarioDestino){
                    mensajes.add(msg);
                }
            }
            totalMensajes++;
        }
        Collections.sort(mensajes, new MensajeTimestampComparator());
        adapter = new MensajeListViewAdapter(this, mensajes, usuarioActual);
        lstChatHistorial.setAdapter(adapter);
    }

    public void retornar(View view) {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("usuarioActual", usuarioActual);
        startActivity(i);
    }

    public void enviarMensaje(View view) {
        String mensaje = txtChatEscribir.getText().toString();

        if (!mensaje.isEmpty()) {
            Mensaje nuevoMensaje = new Mensaje(totalMensajes, usuarioDestino, mensaje, usuarioActual, System.currentTimeMillis());
            SharedPreferences pref = getSharedPreferences("Mensajes", MODE_PRIVATE);
            Set<String> listaMensajes = pref.getStringSet("listaMensajes", new HashSet<>());
            Set<String> updatedMessages = new HashSet<>(listaMensajes);
            updatedMessages.add(nuevoMensaje.formatearMensaje());
            pref.edit().putStringSet("listaMensajes", updatedMessages).apply();
            mensajes.add(nuevoMensaje);
            Collections.sort(mensajes, new MensajeTimestampComparator());
            adapter.notifyDataSetChanged();
            txtChatEscribir.setText("");
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}