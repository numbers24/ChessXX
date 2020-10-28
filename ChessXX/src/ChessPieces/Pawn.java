package ChessPieces;

import App.ChessSquare;
import App.Main;

/**pawn object
 *
 */
public class Pawn extends ChessPiece{
    public Pawn(String name, String color, int moveCount) { super(name, color, moveCount); }

    /**pawn can only move up 1, or 2 at the very begining and can only move sideways forward if an enemy chessPiece is there
     *
     * @param i
     * @param j
     * @param i2
     * @param j2
     * @param board
     * @return
     */
    public boolean checkPath(int i, int j, int i2, int j2, ChessSquare[][] board) {
        switch (board[i][j].piece.color){
            case "##" : return checkDirection(i,j,i2,j2,1,board,"##")>0;
            case "  " : return checkDirection(i,j,i2,j2,-1,board,"  ")>0;
        }
        return false;
    }

    /**checks for legal pawn movements
     *
     * @param i
     * @param j
     * @param i2
     * @param j2
     * @param k
     * @param board
     * @param colorturn
     * @return
     */
    public static int checkDirection(int i, int j, int i2, int j2, int k, ChessSquare[][] board, String colorturn){
        if(board[i][j].piece.moveCount == 0 && i2==i+(2*k) && j2==j){return 1;};
        if(i2==i+k && j2==j){
            if(board[i2][j2].piece != null) return 0; //pawn cannot take a piece in front of it
            else return 2;
        }
        if(i2==i+k && (j2==j+1 || j2 == j-1))
        {
            if(board[i2][j2].piece!=null){
                if (board[i2][j2].piece.color.equals(colorturn)) return 0;
                return 3; //regular taking is legal
            }
            if(board[i2-k][j2].piece!= null){
                if (board[i2-k][j2].piece.color.equals(colorturn)) return 0;
                if(!Main.enpassant(i2,j2,k,colorturn)) return 0;
                return 4; //en passant is legal
            }

        }
        return 0;
    }
}

