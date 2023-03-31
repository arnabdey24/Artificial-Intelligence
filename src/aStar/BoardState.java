package aStar;

import java.util.Arrays;

public class BoardState implements Comparable<Object>{
    private int[] board;
    private int g;
    private int h;
    private int x;
    private int y;
    private BoardState parent;

    public BoardState(int[] board, int g, int h, BoardState parent) {
        this.board=new int[9];
        for (int i = 0; i < 9; i++) {
            this.board[i]=board[i];
            if(board[i]==0){
                x=i/3;
                y=i%3;
            }
        }
        this.g = g;
        this.h = h;
        this.parent=parent;
    }

    public int[] getBoard() {
        return board;
    }

    public int getG() {
        return g;
    }

    public int getH() {
        return h;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BoardState getParent() {
        return parent;
    }

    @Override
    public int compareTo(Object o) {
        BoardState other=(BoardState) o;
        return Integer.compare(h + g, other.h + other.g);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder=new StringBuilder();
        for (int i = 0; i < 9; i++) {
            if(i%3==0)stringBuilder.append("\n");
            stringBuilder.append(board[i]).append(" ");
        }

        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoardState)) return false;
        BoardState that = (BoardState) o;
        return Arrays.equals(board, that.board);
    }
}
