package com.example.buscaminas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogoInstrucciones extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder constructor = new AlertDialog.Builder(getActivity());
        constructor.setTitle("Instrucciones");
        constructor.setMessage("Cuando pulsas en una casilla sale un numero que identifica cuantas minas hay alrededor. " +
                "Si pulsas una casilla que tenga una mina escondida perderas. Si crees o tienes la certeza de que hay" +
                "una mina, haz una pulsacion larga para marcarla. No hagas una pulsacion larga donde no hay una mina" +
                "porque perderas. Ganas cuando hayas encontrado todas las minas");
        constructor.setNeutralButton("Volver", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return constructor.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
