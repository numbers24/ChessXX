package ChessPieces;

import App.ChessSquare;

/**rook object
 *
 */
public class Rook extends ChessPiece{

    public Rook(String name, String color, int moveCount) {
        super(name, color, moveCount);
    }

    /**Rook can only move up/down/left/right for as long as it wants
     *
     * @param i
     * @param j
     * @param i2
     * @param j2
     * @param board
     * @return
     */
    public boolean checkPath(int i, int j, int i2, int j2, ChessSquare[][] board) {
        //check path forward to see if anything is in your way
        return //check vertical and horizontal directions
                checkDirection(i,j,i2,j2,1,0,board) ||
                        checkDirection(i,j,i2,j2,-1,0,board) ||
                        checkDirection(i,j,i2,j2,0,1,board) ||
                        checkDirection(i,j,i2,j2,0,-1,board);
    }

    /**checks directions for rook
     * 
     * @param i
     * @param j
     * @param i2
     * @param j2
     * @param k
     * @param q
     * @param board
     * @return
     */
    public static boolean checkDirection(int i, int j, int i2, int j2, int k, int q,ChessSquare[][] board){
        try {
            for (i = i + k, j = j + q; 8 > i && i >= 0 && 8 > j && j >= 0; i += k, j += q) {
                if (i == i2 && j == j2) return true;
                if (board[i][j].piece != null) return false;
            }
        }catch (IndexOutOfBoundsException e) { return false;}
        return false;
    }
}
