package ChessPieces;

import sample.ChessSquare;

public class Rook extends ChessPiece{

    public Rook(String name, String color, int moveCount) {
        super(name, color, moveCount);
    }
    public boolean checkPath(int i, int j, int i2, int j2, ChessSquare[][] board) {
        //Rook can only move up/down/left/right for as long as it wants
        //check path forward to see if anything is in your way

        //for Y-axis paths
        if((i==i2&&j!=j2)){
            if(j>j2)
            {
                int temp = j;
                j=j2;
                j2=j;
            }
            for(j=j+1;j<j2;j++)
            {
                if(board[i][j].piece!=null)
                    return false;
            }
        }
        //for X-axis paths
        if(i!=i2&&j==j2){
            if(i>i2)
            {
                int temp = i;
                i=i2;
                i2=i;
            }
            for(i=i+1;i<i2;i++)
            {
                if(board[i][j].piece!=null)
                    return false;
            }
        }
        else return false;
        return true;
    }
}
