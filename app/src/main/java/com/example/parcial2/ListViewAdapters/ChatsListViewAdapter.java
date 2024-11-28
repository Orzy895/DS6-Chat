package com.example.parcial2.ListViewAdapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.parcial2.MainActivity;
import com.example.parcial2.Models.Contacto;
import com.example.parcial2.R;


import java.util.List;

public class ChatsListViewAdapter extends ArrayAdapter<Contacto> {

    private List<Contacto> contactos;
    private Context context;

    public ChatsListViewAdapter(Context context, List<Contacto> datos){
        super(context, R.layout.home_listview, datos);
        contactos = datos;
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        RecyclerView.ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.home_listview, null);

        TextView txtNombre = (TextView)item.findViewById(R.id.homeChatTxtName);
        txtNombre.setText(contactos.get(position).getNombre());

        ImageView imgFoto = (ImageView)item.findViewById(R.id.homeChatImgUser);
        String srcString = "foto"+contactos.get(position).getFoto();
        int srcInt = context.getResources().getIdentifier(srcString, "drawable", context.getPackageName());
        imgFoto.setImageResource(srcInt);

        final int idUsuarioSeleccionado = contactos.get(position).getId();

        item.setOnClickListener(view -> ((MainActivity)context).irChat(idUsuarioSeleccionado));

        return (item);
    }
}
