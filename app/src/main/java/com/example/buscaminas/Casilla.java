package com.example.buscaminas;

import android.view.View;

public class Casilla {
    private int valor;
    private View view;
    private boolean marcada;

    public boolean isMarcada() {
        return marcada;
    }

    public void setMarcada(boolean marcada) {
        this.marcada = marcada;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Casilla() {
        this.valor = 0;
        this.view = null;
        this.marcada = false;
    }
}
