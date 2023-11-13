package com.example.buscaminas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogoFin extends DialogFragment {

    onRespuestaI respuesta;
    boolean victoria;

    public DialogoFin(boolean victoria) {
        this.victoria = victoria;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder constructor = new AlertDialog.Builder(getActivity());
        if(!victoria){
            constructor.setTitle("Derrota :(");
        } else {
            constructor.setTitle("Victoria! :)");
        }
        constructor.setMessage("Quieres seguir jugando?");
        constructor.setPositiveButton("VOLVER A JUGAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                respuesta.onRespuesta(true);
            }
        });
        return constructor.create();
    }

    public interface onRespuestaI{
        public void onRespuesta(boolean respuesta);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        respuesta = (onRespuestaI) getActivity();
    }
}
