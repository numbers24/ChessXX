package ChessPieces;

import App.ChessSquare;

/**abstract object for the chess pieces
 *
 */
public abstract class ChessPiece {
    public String name;
    public String color;
    public int moveCount;
    public ChessPiece(String name,String color, int moveCount){
        this.name=name;
        this.color = color;
        this.moveCount=moveCount;
    }

    /**master checkPath function
     *
     * @param i
     * @param j
     * @param i2
     * @param j2
     * @param board
     * @return
     */
    public boolean checkPath(int i, int j, int i2, int j2, ChessSquare[][] board) {
        return true;
    }
}
