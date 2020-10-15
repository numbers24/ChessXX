package sample;

import ChessPieces.ChessPiece;

public class ChessSquare {

    public String color;
    public ChessPiece piece;
    public ChessSquare(String color,ChessPiece piece)
    {
        this.color=color;
        this.piece=piece;
    }
}
