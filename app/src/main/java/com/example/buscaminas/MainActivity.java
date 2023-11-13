package com.example.buscaminas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements DialogoFin.onRespuestaI,DialogoDificultad.cambiarDificultadI {

    ConstraintLayout constraintLayout;
    GridLayout grid;
    int tamaño = 8;
    int numMinas = 10;
    Casilla[][] matriz;
    ArrayList<Casilla> minas;
    View.OnClickListener listenerMina;
    LinearLayout.LayoutParams layoutParams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        constraintLayout = findViewById(R.id.layout_principal);
        grid = findViewById(R.id.gridminas);
        constraintLayout.post(new Runnable() {
            @Override
            public void run() {
                iniciarJuego();
            }
        });
    }

    private void iniciarJuego() {
        matriz = new Casilla[tamaño][tamaño];
        minas = new ArrayList<>();

        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.setMargins(0,0,0,0);
        grid.setLayoutParams(params);

        grid.setColumnCount(tamaño);
        grid.setRowCount(tamaño);

        int alto = constraintLayout.getHeight();
        int ancho = constraintLayout.getWidth();
        layoutParams = new LinearLayout.LayoutParams(ancho/ tamaño,alto/ tamaño);
        layoutParams.setMargins(0,0,0,0);

        listenerMina = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageButton b = (ImageButton) v;
                b.setImageResource(R.drawable.bomba);
                b.setScaleType(ImageView.ScaleType.FIT_CENTER);
                gameOver(false);
            }
        };
        crearMatriz();
    }

    private void crearMatriz() {
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                matriz[i][j]=new Casilla();
            }
        }
        Random random = new Random();
        for (int i = 0; i < numMinas; i++) {
            int fila, columna;
            do {
                fila = random.nextInt(matriz.length);
                columna = random.nextInt(matriz.length);
            } while (matriz[fila][columna].getValor() != 0);

            matriz[fila][columna].setValor(-1);
            minas.add(matriz[fila][columna]);
        }

        rellenarMatriz();
    }

    private void rellenarMatriz() {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                if(matriz[i][j].getValor()==-1){
                    ImageButton b = new ImageButton(getApplicationContext());
                    b.setPadding(0,0,0,0);
                    b.setLayoutParams(layoutParams);
                    b.setOnLongClickListener(new ClickListenerLargo(i,j));
                    b.setOnClickListener(listenerMina);
                    matriz[i][j].setView(b);
                    grid.addView(b);
                } else {
                    matriz[i][j].setValor(cercanas(i, j));
                    Button b = new Button(getApplicationContext());
                    b.setOnLongClickListener(new ClickListenerLargo(i,j));
                    b.setOnClickListener(new ClickListener(i,j));
                    b.setLayoutParams(layoutParams);
                    matriz[i][j].setView(b);
                    grid.addView(b);
                }
            }

        }
    }

    @Override
    public void cambiarDificultad(int dificultad) {
        if(dificultad==DialogoDificultad.FACIL){
            tamaño=8;
            numMinas=10;
            grid.removeAllViews();
            iniciarJuego();
        } else if(dificultad==DialogoDificultad.NORMAL){
            tamaño=12;
            numMinas=30;
            grid.removeAllViews();
            iniciarJuego();
        } else if(dificultad==DialogoDificultad.DIFICIL){
            tamaño=16;
            numMinas=60;
            grid.removeAllViews();
            iniciarJuego();
        }
    }

    private class ClickListener implements View.OnClickListener {
        private int row;
        private int col;

        public ClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void onClick(View view) {
            mostrarCasilla(row,col);
        }
    }
    private void mostrarCasilla(int i, int j) {
        Button b = (Button) matriz[i][j].getView();
        b.setText(matriz[i][j].getValor()+"");
        matriz[i][j].setMarcada(true);
        if (matriz[i][j].getValor() == 0) {
            b.setBackgroundColor(Color.RED);
            for (int k = -1; k <= 1; k++) {
                for (int l = -1; l <= 1; l++) {
                    int nuevaFila = i + k;
                    int nuevaColumna = l + j;

                    if (esPosicionValida(nuevaFila, nuevaColumna) && !matriz[nuevaFila][nuevaColumna].isMarcada()) {
                        mostrarCasilla(nuevaFila,nuevaColumna);
                    }
                }
            }
        }
    }

    private void gameOver(boolean victoria) {
        DialogoFin dialogoFin=new DialogoFin(victoria);
        dialogoFin.show(getSupportFragmentManager(),"Dialogo derrota");
    }

    private int cercanas(int i, int j) {
        int cont=0;
        for (int k = -1; k <= 1; k++) {
            for (int l = -1; l <= 1; l++) {
                int nuevaFila = i + k;
                int nuevaColumna = l + j;

                if (esPosicionValida(nuevaFila, nuevaColumna)) {
                    if(matriz[nuevaFila][nuevaColumna].getValor()==-1)
                        cont ++;
                }
            }
        }
        return cont;
    }

    private boolean esPosicionValida(int fila, int columna) {
        return fila >= 0 && fila < matriz.length && columna >= 0 && columna < matriz.length;
    }

    @Override
    public void onRespuesta(boolean respuesta) {
        if(respuesta) {
            grid.removeAllViews();
            iniciarJuego();
        }
    }

    private class ClickListenerLargo implements View.OnLongClickListener {
        private int row;
        private int col;
        public ClickListenerLargo(int i, int j) {
            this.row = i;
            this.col = j;
        }

        @Override
        public boolean onLongClick(View v) {
            if(matriz[row][col].getValor()==-1){
                ImageButton b = (ImageButton) matriz[row][col].getView();
                b.setBackgroundResource(R.drawable.bandera);
                matriz[row][col].setMarcada(true);
                if(comprobarVictoria())
                    gameOver(true);
            } else {
                gameOver(false);
            }
            return true;
        }
    }

    private boolean comprobarVictoria() {
        for (Casilla m: minas) {
            if(!m.isMarcada())
                return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.instrucciones){
            DialogoInstrucciones instrucciones = new DialogoInstrucciones();
            instrucciones.show(getSupportFragmentManager(),"dialogo instrucciones");
        } else if (id==R.id.reiniciar) {
            grid.removeAllViews();
            iniciarJuego();
        } else if (id==R.id.dificultad) {
            DialogoDificultad dialogoDificultad = new DialogoDificultad();
            dialogoDificultad.show(getSupportFragmentManager(),"dialogo dificultad");
        }
        return super.onOptionsItemSelected(item);
    }
}