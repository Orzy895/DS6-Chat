package com.example.parcial2.CustomDialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.parcial2.R;

public class SeleccionarFoto extends AppCompatDialogFragment {

    int fotoSeleccionado;

    public interface PhotoSelectionListener {
        void onPhotoSelected(int foto);
    }
    private PhotoSelectionListener photoSelectionListener;
    public void setPhotoSelectionListener(PhotoSelectionListener listener) {
        this.photoSelectionListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstance){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.foto_grid, null);

        ImageView foto1 = view.findViewById(R.id.foto1);
        ImageView foto2 = view.findViewById(R.id.foto2);
        ImageView foto3 = view.findViewById(R.id.foto3);
        ImageView foto4 = view.findViewById(R.id.foto4);
        ImageView foto5 = view.findViewById(R.id.foto5);
        ImageView foto6 = view.findViewById(R.id.foto6);

        foto1.setOnClickListener(v -> {
            fotoSeleccionado = 1;
            Toast.makeText(getActivity(), "Imagen Seleccionado", Toast.LENGTH_LONG).show();
        });

        foto2.setOnClickListener(v -> {
            fotoSeleccionado = 2;
            Toast.makeText(getActivity(), "Imagen Seleccionado", Toast.LENGTH_LONG).show();
        });

        foto3.setOnClickListener(v -> {
            fotoSeleccionado = 3;
            Toast.makeText(getActivity(), "Imagen Seleccionado", Toast.LENGTH_LONG).show();
        });

        foto4.setOnClickListener(v -> {
            fotoSeleccionado = 4;
            Toast.makeText(getActivity(), "Imagen Seleccionado", Toast.LENGTH_LONG).show();
        });

        foto5.setOnClickListener(v -> {
            fotoSeleccionado = 5;
            Toast.makeText(getActivity(), "Imagen Seleccionado", Toast.LENGTH_LONG).show();
        });

        foto6.setOnClickListener(v -> {
            fotoSeleccionado = 6;
            Toast.makeText(getActivity(), "Imagen Seleccionado", Toast.LENGTH_LONG).show();
        });

        builder.setView(view).setTitle("Seleccionar Nueva Foto")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (photoSelectionListener != null) {
                            photoSelectionListener.onPhotoSelected(fotoSeleccionado);
                        }
                    }
                });

        return builder.create();
    }

}
