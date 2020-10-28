package ChessPieces;

import App.ChessSquare;

/**knight object
 *
 */
public class Knight extends ChessPiece{
    public Knight(String name, String color, int moveCount) {
        super(name, color, moveCount);
    }

    /**knight can only move to spots that are (i+-1,j+-2) or (i+-2.j+-1) apart
     *
     * @param i
     * @param j
     * @param i2
     * @param j2
     * @param board
     * @return
     */
    public boolean checkPath(int i, int j, int i2, int j2, ChessSquare[][] board) {
        if((i+1 == i2||i-1==i2)&&(j+2==j2||j-2==j2))
            return true;
        if((i+2 == i2||i-2==i2)&&(j+1==j2||j-1==j2))
            return true;
        return false;
    }
}
