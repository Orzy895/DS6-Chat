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
import com.example.parcial2.Models.Mensaje;
import com.example.parcial2.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MensajeListViewAdapter extends ArrayAdapter<Mensaje> {

    private List<Mensaje> mensajes;
    private Context context;
    private int currentUserID;
    public MensajeListViewAdapter(Context context, List<Mensaje> datos, int currentUserID){
        super(context, R.layout.chat_enviar, datos);
        mensajes = datos;
        this.context = context;
        this.currentUserID = currentUserID;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item;

        Mensaje mensaje = mensajes.get(position);
        if(mensaje.getSender() == currentUserID) {
            item = inflater.inflate(R.layout.chat_enviar, null);
        }else{
            item = inflater.inflate(R.layout.chat_recibir, null);
        }

        TextView lblChatBubbleSender = item.findViewById(R.id.lblChatBubbleSender);
        lblChatBubbleSender.setText(mensaje.getMensaje());

        TextView lblChatBubbleSenderTime = item.findViewById(R.id.lblChatBubbleSenderTime);
        Date date = new Date(mensaje.getTime());
        SimpleDateFormat df = new SimpleDateFormat("HH:mm a", Locale.getDefault());
        String formattedTime = df.format(date);
        lblChatBubbleSenderTime.setText(formattedTime);

        return (item);
    }
}
