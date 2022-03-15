package edu.gmu.battleship_project1_tkarawap;

public class BattleShip {
    public static final int SIDE = 8;
    public static final int GAMEOVER = 17;
    private int turn;
    private int bGame[][];
    public BattleShip() {
        bGame = new int[SIDE][SIDE];
        //resetGame();
    }
    public int startPlay(int row, int column, int[][] board, int currentTurn) {
        if (row >= 0 && column >= 0 && row < SIDE && column < SIDE){
            if (board[row][column] != -1) {
                board[row][column] = -1;
                if (currentTurn == 1) turn = 2; else turn = 1;
                return turn; }
            else {
                return currentTurn;
            }
        } else {
            return currentTurn;
        }
    }

    public void resetGame() {
        for (int i=0;i<SIDE;i++)
            for (int j=0;j<SIDE;j++) bGame[i][j] = 0;
        turn = 1;
    }
}
