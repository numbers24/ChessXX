package ChessPieces;

import App.ChessSquare;

public class ChessPiece {
    public String name;
    public String color;
    public int moveCount;
    public ChessPiece(String name,String color, int moveCount){
        this.name=name;
        this.color = color;
        this.moveCount=moveCount;
    }
    public boolean checkPath(int i, int j, int i2, int j2, ChessSquare[][] board) {
        return true;
    }
}
