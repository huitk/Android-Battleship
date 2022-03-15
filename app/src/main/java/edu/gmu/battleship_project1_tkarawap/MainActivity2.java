package edu.gmu.battleship_project1_tkarawap;

import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.app.NativeActivity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity2 extends AppCompatActivity {
    private Button[][] buttonsPlayer;
    private Button[][] buttonsComputer;
    private Button[][] buttonInstruction;
    private int[][] boardPlayer;
    private int[][] boardComputer;
    private Ship[] playerShips;
    private Ship[] computerShips;
    private int[] shipNums;
    private int[] shipSizes;
    private BattleShip bGame;
    private TextView status;
    private Random rand = new Random();
    private int play = 1;
    private int hitsPlayer = 0;
    private int hitsComputer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main2);
        bGame = new BattleShip();
        buildGuiByCode();
    }

    public void buildGuiByCode() {
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int w = size.x / (BattleShip.SIDE + 4);
        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setColumnCount(BattleShip.SIDE);
        gridLayout.setRowCount(BattleShip.SIDE + 1 + BattleShip.SIDE);  // space for a message
        buttonsPlayer = new Button[BattleShip.SIDE][BattleShip.SIDE];
        buttonsComputer = new Button[BattleShip.SIDE][BattleShip.SIDE];
        buttonInstruction = new Button[1][BattleShip.SIDE];
        boardPlayer = new int[BattleShip.SIDE][BattleShip.SIDE];
        boardComputer = new int[BattleShip.SIDE][BattleShip.SIDE];
        int[] shipNums = new int[]{1,2,3,4,5};
        int[] shipSizes = new int[]{5,4,3,3,2};
        ButtonHandler bh = new ButtonHandler();
        Ship[] playerShips = new Ship[5];
        Ship[] computerShips = new Ship[5];

        for (int i = 0; i < playerShips.length; i++){
            playerShips[i] = new Ship(shipSizes[i],shipNums[i]);
        }

        for (int i = 0; i < computerShips.length; i++){
            computerShips[i] = new Ship(shipSizes[i],shipNums[i]);
        }

        initialize_boards();

        // Add ships to player boards

        for (int i = 0; i < playerShips.length; i++){
            boardPlayer = playerShips[i].place_ship(boardPlayer);
        }

        for (int i = 0; i < computerShips.length; i++){
            boardComputer = computerShips[i].place_ship(boardComputer);
        }

        for (int i = 0; i < BattleShip.SIDE; i++)
            for (int j = 0; j < BattleShip.SIDE; j++) {
                buttonsComputer[i][j] = new Button(this);
                buttonsComputer[i][j].setTextSize((float) ((int) w * .1));
                buttonsComputer[i][j].setBackgroundColor(Color.CYAN);
                buttonsComputer[i][j].setOnClickListener(bh);
                gridLayout.addView(buttonsComputer[i][j], w, w);
            }
        status = new TextView(this);
        GridLayout.Spec rowSpec = GridLayout.spec(BattleShip.SIDE, 1);
        GridLayout.Spec columnSpec = GridLayout.spec(0, BattleShip.SIDE);
        GridLayout.LayoutParams lpStatus =
                new GridLayout.LayoutParams(rowSpec, columnSpec);
        status.setLayoutParams(lpStatus);
        status.setWidth(BattleShip.SIDE * w);
        status.setHeight(w);
        status.setGravity(Gravity.CENTER);
        status.setBackgroundColor(Color.GREEN);
        status.setTextSize((int) (w * .3));
        status.setText("Choose a square");
        // status.setText(bGame.result());
        gridLayout.addView(status);

        for (int i = 0; i < BattleShip.SIDE; i++)
            for (int j = 0; j < BattleShip.SIDE; j++) {
                buttonsPlayer[i][j] = new Button(this);
                buttonsPlayer[i][j].setTextSize((float) ((int) w * .1));
                buttonsPlayer[i][j].setText("x");
                buttonsPlayer[i][j].setBackgroundColor(Color.CYAN);
                buttonsPlayer[i][j].setOnClickListener(bh);
                gridLayout.addView(buttonsPlayer[i][j], w, w);
            }

        plot_ships();
        setContentView(gridLayout);
    }
    private class ButtonHandler implements View.OnClickListener {
        public void onClick(View v) {
            for (int row = 0; row < BattleShip.SIDE; row++) {
                for (int column = 0; column < BattleShip.SIDE; column++) {
                    if (v == buttonsPlayer[row][column]) {
                        if (hitsPlayer < BattleShip.GAMEOVER && hitsComputer < BattleShip.GAMEOVER){
                            updatePlayer(row, column);
                            updateComputer();
                        }
                    }
                }
            }
        }
    }

    public void initialize_boards(){
        for (int row = 0; row < BattleShip.SIDE; row++) {
            for (int column = 0; column < BattleShip.SIDE; column++) {
                boardPlayer[row][column] = 0;
                boardComputer[row][column] = 0;
            }
        }
    }

    public void plot_ships(){
        for (int row = 0; row < BattleShip.SIDE; row++) {
            for (int column = 0; column < BattleShip.SIDE; column++) {
                /*
                if (boardPlayer[row][column] != 0) {
                    buttonsPlayer[row][column].setBackgroundColor(Color.RED);
                }
                 */
                if (boardComputer[row][column] != 0) {
                    buttonsComputer[row][column].setBackgroundColor(Color.LTGRAY);
                    buttonsComputer[row][column].setText("+");
                }
            }
        }
    }

    public int[] get_box_indices(int[][] board){
        int[] indices = new int[2];
        int r = rand.nextInt(BattleShip.SIDE);
        int c = rand.nextInt(BattleShip.SIDE);;
        while (board[r][c] == -1) {
            r = rand.nextInt(BattleShip.SIDE);
            c = rand.nextInt(BattleShip.SIDE);
        }
        indices[0] = r;
        indices[1] = c;

        return indices;
    }
    public void updatePlayer(int row, int col) {
        if (play == 1) {
            buttonsPlayer[row][col].setText("");
            if (boardPlayer[row][col] > 0) {
                buttonsPlayer[row][col].setBackgroundColor(Color.RED);
                hitsPlayer++;
            }
            if (hitsPlayer == BattleShip.GAMEOVER) {
                status.setText("YOU WIN!");
                Toast.makeText(this,"YOU WIN!.", Toast.LENGTH_LONG).show();
                showNewGameDialog();
            } else {
                play = bGame.startPlay(row, col, boardPlayer, play);
            }
        }
    }

    public void updateComputer(){
        int row;
        int col;
        int computerIndices[] = new int[2];
        computerIndices = get_box_indices(boardComputer);
        row = computerIndices[0];
        col = computerIndices[1];
        if (play == 2) {
            buttonsComputer[row][col].setText("");
            if (boardComputer[row][col] > 0) {
                buttonsComputer[row][col].setBackgroundColor(Color.RED);
                hitsComputer++;
            } else {
                buttonsComputer[row][col].setBackgroundColor(Color.YELLOW);
            }
            if (hitsComputer == BattleShip.GAMEOVER) {
                status.setText("YOU LOSE!");
                Toast.makeText(this,"YOU LOSE!.", Toast.LENGTH_LONG).show();
                showNewGameDialog();
                //status.setBackgroundColor(Color.BLUE);
            } else {
                play = bGame.startPlay(row, col, boardComputer, play);
            }
        }
    }

    public void showNewGameDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        //alert.setTitle("This is fun");
        alert.setMessage("Do you want to play again");
        PlayDialog playAgain = new PlayDialog();
        alert.setPositiveButton("Yes", playAgain);
        alert.setNegativeButton("No",playAgain);
        alert.show();

    }

    private class PlayDialog implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int id) {
            if (id == -1) {  // yes
                bGame.resetGame();
                enableButtons(true);
                resetButtons();
                status.setBackgroundColor(Color.GREEN);
                status.setText("Choose a square");
            } else if (id == -2)  // no
                MainActivity2.this.finish();
        }
    }

    public void enableButtons(boolean enabled) {
        for (int row=0;row<BattleShip.SIDE;row++)
            for (int col=0;col<BattleShip.SIDE;col++) {
                buttonsPlayer[row][col].setEnabled(enabled);
                buttonsComputer[row][col].setEnabled(enabled);
            }
         }
    public void resetButtons() {
        buildGuiByCode();
        hitsPlayer = 0;
        hitsComputer = 0;
    }
}












