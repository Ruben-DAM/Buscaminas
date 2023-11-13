package com.example.buscaminas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogoDificultad extends DialogFragment {
    public static final int FACIL=1;
    public static final int NORMAL=2;
    public static final int DIFICIL=3;

    cambiarDificultadI cambiarDificultad;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder constructor = new AlertDialog.Builder(getActivity());
        constructor.setTitle("Dificultad");

        RadioGroup radio = new RadioGroup(getActivity());

        RadioButton botonFacil = new RadioButton(getActivity());
        botonFacil.setText("Facil");
        int idFacil=View.generateViewId();
        botonFacil.setId(idFacil);
        radio.addView(botonFacil);

        RadioButton botonNormal = new RadioButton(getActivity());
        botonFacil.setText("Normal");
        int idNormal=View.generateViewId();
        botonFacil.setId(idNormal);
        radio.addView(botonNormal);

        RadioButton botonDificil = new RadioButton(getActivity());
        botonFacil.setText("Dificil");
        int idDificil=View.generateViewId();
        botonFacil.setId(idDificil);
        radio.addView(botonDificil);

        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==idFacil){
                    cambiarDificultad.cambiarDificultad(FACIL);
                } else if (checkedId==idNormal) {
                    cambiarDificultad.cambiarDificultad(NORMAL);
                } else if (checkedId==idDificil) {
                    cambiarDificultad.cambiarDificultad(DIFICIL);
                }
            }
        });

        constructor.setView(radio);
        return constructor.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        cambiarDificultad = (DialogoDificultad.cambiarDificultadI) getActivity();
    }
    public interface cambiarDificultadI{
        public void cambiarDificultad(int dificultad);
    }
}
