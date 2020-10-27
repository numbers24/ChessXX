package ChessPieces;

import App.ChessSquare;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class King extends ChessPiece{
    public King(String name, String color, int moveCount) {
        super(name, color, moveCount);
    }

    public static ArrayList<int []> checklist; //contains the locations of the pieces that are in check with the king
    public static String startcolor; //holds the starting color for checkmate

    public boolean checkPath(int i, int j, int i2, int j2, ChessSquare[][] board) {
        if(Math.abs(i-i2)==1 || Math.abs(j-j2)==1) return true;
        return false;
    }
    //find location of colored king
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

    //checks to see if king is in check and checkmate

    public static boolean check(String colorturn, ChessSquare[][] board, int[] loc)
    {
        int i=loc[0]; int j = loc[1];
        //if we're checking the king, initialze the checklist
        try {
            if(board[i][j].piece instanceof King && board[i][j].piece.color.equals(colorturn)) checklist = new ArrayList<int []>();
            return  //check vertical and horizontal directions
                    checkDirection(i, j, 1, 0, board, colorturn) ||
                    checkDirection(i, j, -1, 0, board, colorturn) ||
                    checkDirection(i, j, 0, 1, board, colorturn) ||
                    checkDirection(i, j, 0, -1, board, colorturn) ||
                    //check diagnal directions
                    checkDirection(i, j, 1, 1, board, colorturn) ||
                    checkDirection(i, j, 1, -1, board, colorturn) ||
                    checkDirection(i, j, -1, 1, board, colorturn) ||
                    checkDirection(i, j, -1, -1, board, colorturn) ||
                    //check for Knights
                    checkKnights(i + 1, j + 2, board, colorturn) ||
                    checkKnights(i + 1, j - 2, board, colorturn) ||
                    checkKnights(i - 1, j + 2, board, colorturn) ||
                    checkKnights(i - 1, j - 2, board, colorturn) ||
                    checkKnights(i + 2, j + 1, board, colorturn) ||
                    checkKnights(i + 2, j - 1, board, colorturn) ||
                    checkKnights(i - 2, j + 1, board, colorturn) ||
                    checkKnights(i - 2, j - 1, board, colorturn)
                    ; //this compiles every position that the king can be under threat by
        } catch(ArrayIndexOutOfBoundsException e) {return true;} //out of bounds can only happen when examining the squares around the king, returns true because the king cannot move there/is in check from there
    }
    //check for Knights
    public static boolean checkKnights(int i, int j,ChessSquare[][] board, String colorturn){
        try {
            if (board[i][j].piece instanceof Knight && !board[i][j].piece.color.equals(colorturn)){
                checklist.add(new int[] {i,j});
                return true;
            }
            else return false;
        } catch (IndexOutOfBoundsException e) {return false;} //no Knight to reach
    }
    //check in anyDirection
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
                                    if ((j == y && (i == x + 1 || i == x - 1))){
                                        checklist.add(new int[] {i,j});
                                        return true;
                                    } else return false;

                                case "  ":
                                    if ((i == x && (j == y + 1 || j == y - 1))){
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
            //check if all squares around the king are still compromised
            if(     //check vertical and horizontal directions
                    check(colorturn,board,new int[]{i+1,j+1}) &&
                    check(colorturn,board,new int[]{i+1,j-1}) &&
                    check(colorturn,board,new int[]{i-1,j+1}) &&
                    check(colorturn,board,new int[]{i-1,j-1}) &&
                    check(colorturn,board,new int[]{i,j+1}) &&
                    check(colorturn,board,new int[]{i,j-1}) &&
                    check(colorturn,board,new int[]{i+1,j}) &&
                    check(colorturn,board,new int[]{i-1,j})
                      ){
                board = ChessSquare.reverse(hidden,board);
                return 2; //checkmate
            }
            else{
                board = ChessSquare.reverse(hidden,board); return 1; //just a regular check
            }
        }
        return 0; //the king is not in danger at all
    }

}

