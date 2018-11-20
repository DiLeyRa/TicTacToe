package com.example.alidasanchez.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3]; //se crea la matriz bidimensional

    private boolean player1Turn = true; //se crea la variable del jugador 1 y se inicializa en true para que seal el primero que comienza el juego "X"

    private int roundCount;//contador para las rondas, el juego consta de solo 9 rondas

    private int player1Points;//contador para los puntos del jugador 1
    private int player2Points;//contador para los puntos del jugador 2

    private TextView textViewPlayer1;// variable donde se mostrara el puntaje del jugador 1
    private TextView textViewPlayer2;// variable donde se mostrara el puntaje del jugador 2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //creacion del primer metodo
        //se asocia al id del textView de el xml
        textViewPlayer1 = findViewById(R.id.text_p1);
        textViewPlayer2 = findViewById(R.id.text_p2);

        //acceder a los botones mediante un bucle anidado recorreremos todas las filas y columnas
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;//se crea una cadena de strings de nuestros botones
                /*
                00 01 02
                10 11 12
                20 21 22
                */
                //acceder a los recursos osea los botones mediante su identificador con un click
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);//se llamara al metodo onClick cuando se haga click en cualquiera de nuestros botones
            }
        }
        Button buttonReset = findViewById(R.id.button_reset); //boton de reseteo
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();//metodo para reinicial el juego
            }
        });
    }

    @Override
    public void onClick(View v) {//metdo de click para nuestros botones del juego
        if (!((Button) v).getText().toString().equals("")) {//verificar si el campo esta vacio y si no lo esta solo retorna
            return;
        }

        //esto si encuentra una cadena vacia o campo
        if (player1Turn) {//verifica si es turno del jugador 1
            ((Button) v).setText("X");//marcara con x el botton
        } else {
            ((Button) v).setText("O");//marcara con o el boton
        }
        roundCount++; //aumentamos el contador de las rondas

        //ver si hay victoria
        if (checkForWin()) {
            if (player1Turn) {
                player1Wins(); //gana jugador 1 se llama al metodo
            } else {
                player2Wins(); // gana jugador 2 se llama al metodo
            }
        } else if(roundCount == 9){
            draw();//empate se llama al metodo
        }else{
            player1Turn = !player1Turn;
        }
    }

    //creamos el metodo para verificar si hay ganador
    private boolean checkForWin() {
        String[][] field = new String [3][3];

        //generar todas nuestras filas y columnas
        for ( int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        //recorrer todas las filas, ve si estan llenos los campos que no hay campo vacio
        for ( int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }
        //recorrer todas las columnas, ve si estan llenos los campos que no hay campo vacio
        for ( int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }
        //recorrer en \, ve si estan llenos los campos que no hay campo vacio
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }
        //recorrer en /, ve si estan llenos los campos que no hay campo vacio
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }

    //creacion de los metodos ganador 1 ganador 2 u empate
    private void player1Wins() {
        player1Points++;//incrementar el puntaje del jugador 1
        Toast.makeText(this, "El jugador 1 GANA!", Toast.LENGTH_SHORT).show();//alerta de que gano
        updatePointsText();//metodo actualizacion de puntos
        resetBoard();//comenzar nueva ronda
    }

    private void player2Wins() {
        player2Points++;//incrementar el puntaje del jugador 2
        Toast.makeText(this, "El jugador 2 GANA!", Toast.LENGTH_SHORT).show();
        updatePointsText();//metodo actualizacion de puntos
        resetBoard();//comenzar nueva ronda
    }

    private void draw() {
        Toast.makeText(this, ":(", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    //metodo actualizacion de puntos en el text view del xml
    private void updatePointsText() {
        textViewPlayer1.setText("Jugador 1:" + player1Points);
        textViewPlayer2.setText("Jugador 2:" + player2Points);
    }

    //metodo comenzar ronda, reestablecer los botones a una cadena vacia
    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++){
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;//contador de ronda a 0
        player1Turn = true;// inia el jugador 1
    }
    private void resetGame(){
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
    }

    @Override//guardar actividad
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override//recuperar actividad
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}
