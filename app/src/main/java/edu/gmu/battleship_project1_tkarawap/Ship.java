package edu.gmu.battleship_project1_tkarawap;

import java.util.Random;

public class Ship {
    public static final int HORIZONTAL = 1;
    public static final int ROWS = 8;
    public static final int COLS = 8;
    Boolean placed = false;
    private int num;
    private int direction;
    private int size;
    private int r;
    private int c;
    private int board[][];
    Random rand = new Random();

    public Ship(int size, int num) {
        this.size = size;
        this.direction = rand.nextInt(2);
        this.num = num;
    }

    public int[][] place_ship(int board[][]) {
        this.board = board;
        while (!placed) {
            if (direction == HORIZONTAL) {
                r = rand.nextInt(ROWS);
                c = rand.nextInt(COLS - size);
            } else {
                r = rand.nextInt(ROWS - size);
                c = rand.nextInt(COLS);
            }
            placed = true;
            // check to make sure all locations for the ship are free
            if (direction == HORIZONTAL) {
                for (int i = 0; i < size; i++)
                    if (this.board[r][c + i] != 0) placed = false;
            } else {
                for (int i = 0; i < size; i++)
                    if (this.board[r + i][c] != 0) placed = false;
            }
            // if all is ok, place the ship here.
            if (placed) {
                if (direction == HORIZONTAL) {
                    for (int i = 0; i < size; i++)
                        this.board[r][c + i] = this.num;
                } else {
                    for (int i = 0; i < size; i++)
                        this.board[r + i][c] = this.num;
                }
            }
        }
        return this.board;
    }
}