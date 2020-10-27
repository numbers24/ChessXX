package ChessPieces;

import App.ChessSquare;

public class Queen extends ChessPiece{
    public Queen(String name, String color, int moveCount) {
        super(name, color, moveCount);
    }

    public boolean checkPath(int i, int j, int i2, int j2, ChessSquare[][] board) {
        return //check vertical and horizontal directions
                checkDirection(i,j,i2,j2,1,0,board) ||
                checkDirection(i,j,i2,j2,-1,0,board) ||
                checkDirection(i,j,i2,j2,0,1,board) ||
                checkDirection(i,j,i2,j2,0,-1,board) ||
                //check diagnal directions
                checkDirection(i,j,i2,j2,1,1,board) ||
                checkDirection(i,j,i2,j2,1,-1,board) ||
                checkDirection(i,j,i2,j2,-1,1,board) ||
                checkDirection(i,j,i2,j2,-1,-1,board);
    }
    public static boolean checkDirection(int i, int j, int i2, int j2, int k, int q,ChessSquare[][] board){
        try {
            for (i = i + k, j = j + q; 8 > i && i >= 0 && 8 > j && j >= 0; i += k, j += q) {
                if (i == i2 && j == j2) return true;
                if (board[i][j].piece != null) return false;
            }
        } catch (IndexOutOfBoundsException e){ return false;}
        return false;
    }
}
