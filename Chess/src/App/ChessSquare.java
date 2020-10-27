package App;

import ChessPieces.ChessPiece;

public class ChessSquare {

    public String color;
    public ChessPiece piece;
    public ChessSquare(String color,ChessPiece piece)
    {
        this.color=color;
        this.piece=piece;
    }

    //create a hidden cast of the current board when move begins
    public static ChessPiece[][] cast(ChessSquare[][] board){
        ChessPiece [][] hidden = new ChessPiece[8][8];
        for(int i = 0;i<8;i++)
            for(int j = 0; j<8 ; j++)
                hidden[i][j] = board[i][j].piece;

            return hidden;
    }
    //will reverse the board's decisions in failure using the cast
    public static ChessSquare[][] reverse(ChessPiece[][] hidden, ChessSquare[][] board){
        for(int i = 0;i<8;i++)
            for(int j = 0; j<8 ; j++)
                board[i][j].piece = hidden[i][j];

            return board;
    }
}
