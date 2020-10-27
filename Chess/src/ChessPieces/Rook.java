package ChessPieces;

import App.ChessSquare;

public class Rook extends ChessPiece{

    public Rook(String name, String color, int moveCount) {
        super(name, color, moveCount);
    }
    public boolean checkPath(int i, int j, int i2, int j2, ChessSquare[][] board) {
        //Rook can only move up/down/left/right for as long as it wants
        //check path forward to see if anything is in your way

        return //check vertical and horizontal directions
                checkDirection(i,j,i2,j2,1,0,board) ||
                        checkDirection(i,j,i2,j2,-1,0,board) ||
                        checkDirection(i,j,i2,j2,0,1,board) ||
                        checkDirection(i,j,i2,j2,0,-1,board);
    }
    public static boolean checkDirection(int i, int j, int i2, int j2, int k, int q,ChessSquare[][] board){
        try {
            for (i = i + k, j = j + q; 8 > i && i >= 0 && 8 > j && j >= 0; i += k, j += q) {
                if (i == i2 && j == j2) return true;
                if (board[i][j].piece != null) return false;
            }
        }catch (IndexOutOfBoundsException e) { return false;}
        return false;
    }
    //checks to see if you can succesfully castle
    public static boolean checkCastle(int i,int j,int i2,int j2,ChessPiece A,ChessPiece B, ChessSquare[][] board){

        if(((A instanceof Rook && B instanceof King)||(A instanceof King && B instanceof Rook))&&(A.moveCount==0&&B.moveCount==0)) //if A and B are a Rook and King and haven't moved
        {
            if(j>j2)
            {
                int temp = j;
                j=j2;
                j2=temp;
            }
            //check all chess squares in between to make sure they're empty
            for(j=j+1;j<j2;j++)
            {
                if(board[i][j].piece!=null)
                    return false;
            }
            return true;
        }
        return false;
    }
}
