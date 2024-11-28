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
import com.example.parcial2.Models.Contacto;
import com.example.parcial2.R;

import java.util.List;

public class CambiarContactoListViewAdapter extends ArrayAdapter<Contacto> {

    private List<Contacto> contactos;
    private Context context;

    public CambiarContactoListViewAdapter(Context context, List<Contacto> datos){
        super(context, R.layout.cambiar_usuario_listview, datos);
        contactos = datos;
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        RecyclerView.ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.cambiar_usuario_listview, null);

        TextView txtNombre = (TextView)item.findViewById(R.id.lblCambiarUsuario);
        txtNombre.setText(contactos.get(position).getNombre() + " "+contactos.get(position).getApellido());

        return (item);
    }

}
