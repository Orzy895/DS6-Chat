package com.example.parcial2.CustomDialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.parcial2.ListViewAdapters.CambiarContactoListViewAdapter;
import com.example.parcial2.MainActivity;
import com.example.parcial2.Models.Contacto;
import com.example.parcial2.R;

import java.util.List;

public class CambiarUsuario extends AppCompatDialogFragment {

    TextView txtContacto;
    private MainActivity mainActivity;

    List<Contacto> contactos;

    public CambiarUsuario(MainActivity mainActivity) {
    }

    public interface CambiarUsuarioListener {
        void onContactoSeleccionado(int idContacto);
    }

    private CambiarUsuarioListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstance){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.cambiar_usuario, null);
        ListView lsv = view.findViewById(R.id.CambiarUsuarioLsv);
        CambiarContactoListViewAdapter adapter = new CambiarContactoListViewAdapter(getContext(), contactos);
        lsv.setAdapter(adapter);
        lsv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contacto selectedContacto = (Contacto) parent.getItemAtPosition(position);
                if (selectedContacto != null) {
                    int selectedContactoId = selectedContacto.getId();
                    listener.onContactoSeleccionado(selectedContactoId);
                    dismiss();
                }
            }
        });
        builder.setView(view).setTitle("Cambiar Usuario")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });

        return builder.create();
    }

    public void setCambiarUsuarioListener(CambiarUsuarioListener listener, List<Contacto> contactos) {
        this.listener = listener;
        this.contactos = contactos;
    }

}
