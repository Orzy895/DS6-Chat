package com.example.parcial2;

import static android.app.PendingIntent.getActivity;
import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.parcial2.CustomDialogs.SeleccionarFoto;
import com.example.parcial2.Models.Contacto;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EditarPerfil extends AppCompatActivity implements SeleccionarFoto.PhotoSelectionListener{

    TextView lblPerfilTelefono;
    EditText txtPerfilNombre, txtPerfilApellido;
    ImageView imgPerfilFoto;
    Contacto contacto;
    int usuarioActual, foto;
    String nombre, apellido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        InicializarControles();
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Do nothing to disable the back button
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    public void InicializarControles(){
        lblPerfilTelefono = (TextView)findViewById(R.id.lblPerfilTelefono);
        txtPerfilApellido = (EditText)findViewById(R.id.txtPerfilApellido);
        txtPerfilNombre = (EditText)findViewById(R.id.txtPerfilNombre);
        imgPerfilFoto = (ImageView)findViewById(R.id.ImgPerfilFoto);

        Bundle datos = getIntent().getExtras();
        if(datos!=null){
            usuarioActual = datos.getInt("usuarioActual");
            contacto = new Contacto(
                    datos.getString("nombre"),
                    datos.getString("apellido"),
                    datos.getInt("foto"),
                    datos.getInt("telefono"),
                    datos.getInt("id")
            );
            lblPerfilTelefono.setText(String.valueOf(contacto.getNumeroTelefono()));
            txtPerfilNombre.setText(contacto.getNombre());
            txtPerfilApellido.setText(contacto.getApellido());
            String src = "foto" + contacto.getFoto();
            int fotoSrc = getResources().getIdentifier(src, "drawable", getPackageName());
            imgPerfilFoto.setImageResource(fotoSrc);
            foto = contacto.getFoto();
        }
    }

    public void cambiarFoto(View view) {
        SeleccionarFoto seleccionarFoto = new SeleccionarFoto();
        seleccionarFoto.setPhotoSelectionListener(this);
        seleccionarFoto.show(getSupportFragmentManager(), "Seleccionar Nueva Foto");
    }

    public void retornar(View view) {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("usuarioActual", usuarioActual);
        startActivity(i);
    }

    @Override
    public void onPhotoSelected(int photoId) {
        Log.i(TAG, "onPhotoSelected: "+photoId);
        foto = photoId;
        String src = "foto" + foto;
        int fotoSrc = getResources().getIdentifier(src, "drawable", getPackageName());
        imgPerfilFoto.setImageResource(fotoSrc);
    }

    public void actualizarPerfil(View view) {
        nombre = txtPerfilNombre.getText().toString();
        apellido = txtPerfilApellido.getText().toString();
        if(verificarCampos(nombre, apellido)){
            int contactIdToUpdate = contacto.getId();
            SharedPreferences pref = getSharedPreferences("Contactos", MODE_PRIVATE);
            Set<String> listaContactos = pref.getStringSet("listaContactos", new HashSet<>());
            List<Contacto> updatedContactos = new ArrayList<>();
            for (String contactoStr : listaContactos) {
                Contacto existingContacto = Contacto.deformatearContacto(contactoStr);
                if (existingContacto.getId() == contactIdToUpdate) {
                    existingContacto.setNombre(nombre);
                    existingContacto.setApellido(apellido);
                    existingContacto.setFoto(foto);
                }
                updatedContactos.add(existingContacto);
            }
            SharedPreferences.Editor editor = pref.edit();
            Set<String> updatedListaContactos = new HashSet<>();
            for (Contacto contacto : updatedContactos) {
                updatedListaContactos.add(contacto.formatearContacto());
            }
            editor.putStringSet("listaContactos", updatedListaContactos);
            editor.apply();
        }
    }

    public boolean verificarCampos(String nombre, String apellido){
        if(TextUtils.isEmpty(nombre)){
            txtPerfilNombre.setError("Introduzca el nombre");
            return false;
        }if(TextUtils.isEmpty(apellido)){
            txtPerfilApellido.setError("Introduzca el apellido");
            return false;
        }return true;
    }
}