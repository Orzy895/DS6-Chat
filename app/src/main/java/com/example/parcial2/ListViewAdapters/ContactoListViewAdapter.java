package com.example.parcial2.ListViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.parcial2.ListaContactos;
import com.example.parcial2.MainActivity;
import com.example.parcial2.Models.Contacto;
import com.example.parcial2.R;

import java.util.List;

public class ContactoListViewAdapter extends ArrayAdapter<Contacto> {
    private List<Contacto> contactos;
    private Context context;

    public ContactoListViewAdapter(Context context, List<Contacto> datos){
        super(context, R.layout.home_listview, datos);
        contactos = datos;
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        RecyclerView.ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.contactos_listview, null);

        TextView txtNombre = (TextView)item.findViewById(R.id.lblContactoNombre);
        txtNombre.setText(contactos.get(position).getNombre() + " "+contactos.get(position).getApellido());

        TextView txtNumero = (TextView)item.findViewById(R.id.lblContactoNumero);
        txtNumero.setText(String.valueOf(contactos.get(position).getNumeroTelefono()));

        ImageView imgFoto = (ImageView)item.findViewById(R.id.imgContactoFoto);
        String srcString = "foto"+contactos.get(position).getFoto();
        int srcInt = context.getResources().getIdentifier(srcString, "drawable", context.getPackageName());
        imgFoto.setImageResource(srcInt);

        final int idUsuarioSeleccionado = contactos.get(position).getId();

        item.setOnClickListener(view -> ((ListaContactos)context).irChat(idUsuarioSeleccionado));

        return (item);
    }
}
