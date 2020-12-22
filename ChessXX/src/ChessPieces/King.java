package ChessPieces;

import App.*;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**king object
 *
 */
public class King extends ChessPiece{
    public King(String name, String color, int moveCount) {
        super(name, color, moveCount);
    }

    /**contains the locations of the pieces that are in check with the king
     *
     */
    public static ArrayList<int []> checklist;
    /**holds the starting color for checkmate
     *
     */
    public static String startcolor;

    /**king can move one space in any direction unless he is castling, then he can move two but only under the condition to swap with the rook if both haven't moved
     *
     * @param i
     * @param j
     * @param i2
     * @param j2
     * @param board
     * @return
     */
    public boolean checkPath(int i, int j, int i2, int j2, ChessSquare[][] board) {
        if(Math.abs(i-i2)==1 && (Math.abs(j-j2)==0 || Math.abs(j-j2)==1) || (Math.abs(i-i2)==0 || Math.abs(i-i2)==1) && Math.abs(j-j2)==1) return true;
        if(Math.abs(j-j2)==2 && i==i2){
            int[] castleloc = checkCastle(i,j,j2,board[i][j].piece,board);
            if(castleloc !=null) {
                Main.castle(i, j, castleloc[0], castleloc[1]); //{y,k}
                return true;
            }
        }
        return false;
    }

    /**checks to see if you can succesfully castle
     *
     * @param i
     * @param j
     * @param j2
     * @param A
     * @param board
     * @return
     */
    public static int[] checkCastle(int i,int j,int j2,ChessPiece A, ChessSquare[][] board){
        //know which direction we are going
        if(A.moveCount!=0) return null; //if not the initial position
        int k = 1;
        if(j>j2){
            k=-1;
        }
        for(int y=j+k; y>=0 && y<8; y+=k){
            ChessPiece B = board[i][y].piece;
            if (B!=null)
                if (B instanceof Rook && B.moveCount == 0 && A.color.equals(B.color)) return new int[]{y, k};
                else return null;
            if(check(A.color,board,new int[]{i,y})) return null;
        } return null;
    }


    /**find location of colored king
     *
     * @param colorturn
     * @param board
     * @return
     */
    public static int[] getLoc(String colorturn, ChessSquare[][] board)
    {
        for(int i=0;i<8;i++)
            for(int j=0;j<8;j++)
            {
                if(board[i][j].piece instanceof King) //you've found king
                    if(board[i][j].piece.color.equals(colorturn)) //you've found king of the right color
                        return new int[]{i, j};
            }
        return null;
    }

    /**checks to see if king is in check
     *
     * @param colorturn
     * @param board
     * @param loc
     * @return
     */
    public static boolean check(String colorturn, ChessSquare[][] board, int[] loc)
    {
        int i=loc[0]; int j = loc[1];
        //if we're checking the king, initialze the checklist
        try {
            if(board[i][j].piece instanceof King && board[i][j].piece.color.equals(colorturn)) checklist = new ArrayList<int []>();
            //for adding all pieces in check with the king not just the first
            boolean [] checkDir = new boolean[] {
                    checkDirection(i, j, 1, 0, board, colorturn) ,
                            checkDirection(i, j, -1, 0, board, colorturn) ,
                            checkDirection(i, j, 0, 1, board, colorturn) ,
                            checkDirection(i, j, 0, -1, board, colorturn) ,
                            //check diagnal directions
                            checkDirection(i, j, 1, 1, board, colorturn) ,
                            checkDirection(i, j, 1, -1, board, colorturn) ,
                            checkDirection(i, j, -1, 1, board, colorturn) ,
                            checkDirection(i, j, -1, -1, board, colorturn) ,
            };
            boolean [] checkN = new boolean[]{
                    checkKnights(i + 1, j + 2, board, colorturn) ,
                            checkKnights(i + 1, j - 2, board, colorturn) ,
                            checkKnights(i - 1, j + 2, board, colorturn) ,
                            checkKnights(i - 1, j - 2, board, colorturn) ,
                            checkKnights(i + 2, j + 1, board, colorturn) ,
                            checkKnights(i + 2, j - 1, board, colorturn) ,
                            checkKnights(i - 2, j + 1, board, colorturn) ,
                            checkKnights(i - 2, j - 1, board, colorturn)
            };
            //alternative to a large or conditional
            for(boolean b : checkDir) if(b) return true;
            for(boolean b : checkN) if(b) return true;
            //this compiles every position that the king can be under threat by
            return false;
        } catch(ArrayIndexOutOfBoundsException e) {return true;} //out of bounds can only happen when examining the squares around the king, returns true because the king cannot move there/is in check from there
    }

    /**check for knights
     *
     * @param i
     * @param j
     * @param board
     * @param colorturn
     * @return
     */
    public static boolean checkKnights(int i, int j,ChessSquare[][] board, String colorturn){
        try {
            if (board[i][j].piece instanceof Knight && !board[i][j].piece.color.equals(colorturn)){
                checklist.add(new int[] {i,j});
                return true;
            }
            else return false;
        } catch (IndexOutOfBoundsException e) {return false;} //no Knight to reach
    }

    /**checks a certain direction for enemies
     *
     * @param x
     * @param y
     * @param a
     * @param b
     * @param board
     * @param colorturn
     * @return
     */
    public static boolean checkDirection(int x,int y,int a,int b, ChessSquare[][] board,String colorturn){
        try {
            //unless we aren't checking king
            if (board[x][y].piece != null){
                try {
                    if (!(board[x][y].piece instanceof King) && board[x][y].piece.color.equals(startcolor)) return true;
                } catch (ExceptionInInitializerError e) {
                    //the king cannot move here because it is already occupied
                    if (!(board[x][y].piece instanceof King) && board[x][y].piece.color.equals(colorturn)) return true;
                }
        }
            //ping the chessboard in all directions
            for (int i = x+a, j = y+b, k = a, q = b; 8 > i && i >= 0 && 8 > j && j >= 0; i += k, j += q) {

                if (board[i][j].piece != null) {//when king hits a chess piece

                    if (board[i][j].piece.color.equals(colorturn)) return false; //the first piece in this direction was black
                    else
                    switch (board[i][j].piece.getClass().toString()) {
                        case "class ChessPieces.King": //if the difference in moves between x,y and i,j is 1 ie if King is right next to you
                            if (Math.abs(i-x)==1 || Math.abs(j-y)==1){
                                try{
                                if(board[i][j].piece.color.equals(startcolor)) return false;} catch (Exception e) {}
                                checklist.add(new int[] {i,j});
                                return true;
                            } else return false;
                        case "class ChessPieces.Queen": //Queen can be a danger from any direction
                            checklist.add(new int[] {i,j});
                            return true;
                        case "class ChessPieces.Bishop": //Diagnal direction check
                            if (i!=x && j!=y){
                                checklist.add(new int[] {i,j});
                                return true;
                            } else return false;
                        case "class ChessPieces.Rook": //Horizontal/Vertical Direction check
                            if (i == x || j == y){
                                checklist.add(new int[] {i,j});
                                return true;
                            } else return false;
                        case "class ChessPieces.Pawn": //check colorturn to find which way is forward
                            switch (colorturn) {
                                case "##":
                                    if ((i == x+1 && (j == y + 1 || j == y - 1))){
                                        checklist.add(new int[] {i,j});
                                        return true;
                                    } else return false;

                                case "  ":
                                    if ((i == x-1 && (j == y + 1 || j == y - 1))){
                                        checklist.add(new int[] {i,j});
                                        return true;
                                    } else return false;
                            }
                    }
                    return false; //if none of the conditions returned true, then the chesspiece will not be in check with the king
                }
            }
        } catch (IndexOutOfBoundsException e) { return false;} //out of bounds means the piece cannot move there
        return false;
    }

    /**checks for checkmate exhaustivly
     *
     * @param colorturn
     * @param board
     * @return
     */
    public static int checkmate(String colorturn,ChessSquare[][] board){
        ChessPiece[][] hidden = ChessSquare.cast(board);
        startcolor = colorturn;
        int [] loc = getLoc(colorturn,board);
        int i=loc[0]; int j = loc[1];
        if (check(colorturn,board,loc)){
            //we know the king is in check, but for it to be a checkmate, the king cannot have any way out
            try {
                for (int[] c : checklist) {
                    //if the piece checking the king can be taken down, ignore it
                    if (check(board[c[0]][c[1]].piece.color, board, c)) {
                        board[c[0]][c[1]].piece = null;
                    }
                }
            } catch (ConcurrentModificationException e){}
            checklist = new ArrayList<int[]>();
            //check if all squares around the king are still compromised
            if(
                    check(colorturn,board,new int[]{i+1,j+1}) &&
                    check(colorturn,board,new int[]{i+1,j-1}) &&
                    check(colorturn,board,new int[]{i-1,j+1}) &&
                    check(colorturn,board,new int[]{i-1,j-1}) &&
                    check(colorturn,board,new int[]{i,j+1}) &&
                    check(colorturn,board,new int[]{i,j-1}) &&
                    check(colorturn,board,new int[]{i+1,j}) &&
                    check(colorturn,board,new int[]{i-1,j})
                      ){
                //check if the paths of those checking can be blocked
                for(int[]c : checklist){
                    for(int [] path : getPath(i,j,c[0],c[1],board)){
                        if(check(Main.endTurn(colorturn),board,path)) return 1; //it will throw a check if one of the members on your team can rach this block
                    }
                }
                    board = ChessSquare.reverse(hidden, board);
                    return 2; //checkmate
            }
            else{
                board = ChessSquare.reverse(hidden,board); return 1; //just a regular check
            }
        }
        return 0; //the king is not in danger at all
    }

    /** returns path between king and piece checking it
     *
     * @param i
     * @param j
     * @param i2
     * @param j2
     * @param board
     * @return
     */
    public static ArrayList<int []> getPath(int i, int j, int i2, int j2,ChessSquare[][] board){
        ArrayList<int[]> path = new ArrayList<int[]>();
        int k = 0;
        int q = 0;
        if(i==i2){//the path is verticle or horizontal
                if(j>j2) k=-1;
                else k = 1;
                for(int y = j+k; y!=j2; y+=k)
                    path.add(new int[]{i,y});
        }
        if(j==j2){
            if(i>i2) k=-1;
            else k = 1;
            for(int x=i+k; x!=i2; x+=k)
                path.add(new int[]{x,j});
        }
        if(i+j == i2+j2 || i-j == i2-j2) {//the path is diagonal
            if(i>i2) k=-1;
            else k = 1;
            if(j>j2) q=-1;
            else q= 1;
            for(int x=i+k,y=j+q; x!=i2 && y!=j2; x+=k,y+=q)
                path.add(new int[]{x,y});
        }
        return path;
    }
}

