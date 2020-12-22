package App;

import ChessPieces.*;
import java.util.Scanner;


/**main method runs the chess game holds the data for the board
 *
 */
public class Main {
    /**the X-axis file rank
     *
     */
    public static char[] FileRankX = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
    /**the Y-axis file rank
     *
     */
    public static char[] FileRankY = {'8','7','6','5','4','3','2','1'};
    /**the chess board structure
     *
     */
    public static App.ChessSquare[][] board;
    /** global data for promotion use
     *
     */
    public static String promotion;

    /**manually generates a new board: an 8*8 Matrix of type ChessSquares which hold the value of their color and the piece that is on them
     *
     * @return
     */
    public static App.ChessSquare[][] generateBoard()
    {
        App.ChessSquare[][] board =
                {
                        {new App.ChessSquare("  ",new Rook("bR","##",0)), new App.ChessSquare("##",new Knight("bN","##",0)), new App.ChessSquare("  ",new Bishop("bB","##",0)),new App.ChessSquare("##",new Queen("bQ","##",0)),new App.ChessSquare("  ",new King("bK","##",0)),new App.ChessSquare("##",new Bishop("bB","##",0)),new App.ChessSquare("  ",new Knight("bN","##",0)),new App.ChessSquare("##",new Rook("bR","##",0))},
                        {new App.ChessSquare("##",new Pawn("bp","##",0)), new App.ChessSquare("  ",new Pawn("bp","##",0)),new App.ChessSquare("##",new Pawn("bp","##",0)), new App.ChessSquare("  ",new Pawn("bp","##",0)),new App.ChessSquare("##",new Pawn("bp","##",0)), new App.ChessSquare("  ",new Pawn("bp","##",0)),new App.ChessSquare("##",new Pawn("bp","##",0)), new App.ChessSquare("  ",new Pawn("bp","##",0))},
                        {new App.ChessSquare("  ",null),new App.ChessSquare("##",null),new App.ChessSquare("  ",null),new App.ChessSquare("##",null),new App.ChessSquare("  ",null),new App.ChessSquare("##",null),new App.ChessSquare("  ",null),new App.ChessSquare("##",null)},
                        {new App.ChessSquare("##",null),new App.ChessSquare("  ",null),new App.ChessSquare("##",null),new App.ChessSquare("  ",null),new App.ChessSquare("##",null),new App.ChessSquare("  ",null),new App.ChessSquare("##",null),new App.ChessSquare("  ",null)},
                        {new App.ChessSquare("  ",null),new App.ChessSquare("##",null),new App.ChessSquare("  ",null),new App.ChessSquare("##",null),new App.ChessSquare("  ",null),new App.ChessSquare("##",null),new App.ChessSquare("  ",null),new App.ChessSquare("##",null)},
                        {new App.ChessSquare("##",null),new App.ChessSquare("  ",null),new App.ChessSquare("##",null),new App.ChessSquare("  ",null),new App.ChessSquare("##",null),new App.ChessSquare("  ",null),new App.ChessSquare("##",null),new App.ChessSquare("  ",null)},
                        {new App.ChessSquare("  ",new Pawn("wp","  ",0)), new App.ChessSquare("##",new Pawn("wp","  ",0)),new App.ChessSquare("  ",new Pawn("wp","  ",0)), new App.ChessSquare("##",new Pawn("wp","  ",0)),new App.ChessSquare("  ",new Pawn("wp","  ",0)), new App.ChessSquare("##",new Pawn("wp","  ",0)),new App.ChessSquare("  ",new Pawn("wp","  ",0)), new App.ChessSquare("##",new Pawn("wp","  ",0))},
                        {new App.ChessSquare("##",new Rook("wR","  ",0)), new App.ChessSquare("  ",new Knight("wN","  ",0)), new App.ChessSquare("##",new Bishop("wB","  ",0)),new App.ChessSquare("  ",new Queen("wQ","  ",0)),new App.ChessSquare("##",new King("wK","  ",0)),new App.ChessSquare("  ",new Bishop("wB","  ",0)),new App.ChessSquare("##",new Knight("wN","  ",0)),new App.ChessSquare("  ",new Rook("wR","  ",0))}
                };
        return board;
    }

    /**thrown when an illegal move is detected, returns false to prevent the game from going forward
     *
     * @return
     */
    public static boolean throwIllegalMove(){
        System.out.println("Illegal move, try again");
        return false;
    }

    /**thrown when a chessmate or surrender is called
     *
     * @param colorturn
     */
    public static void throwVictory(String colorturn){
        switch (colorturn){
            case "  " : System.out.println("White wins!"); return;
            case "##" : System.out.println("Black wins!"); return;
        }
    }

    /**end turns switches colors to the next turn
     *
     * @param colorturn
     * @return
     */
    public static String endTurn(String colorturn) {
        switch (colorturn) {
            case "  ":
                return "##";
            case "##":
                return "  ";
        }
        return null;
    }

    /**preforms en passant
     * @param i
     * @param j
     * @param k
     * @param colorturn
     * @return
     */
    public static boolean enpassant(int i, int j, int k, String colorturn){
        //an en passant can only happen on the user's 5th respectivley
        switch (colorturn){
            case "##" : if(i==5) break; else return false;
            case "  " : if(i==2) break; else return false;
        }
        board[i-k][j].piece = null;
        return true;
    }

    /**preforms a castle
     *
     * @param i
     * @param j
     * @param j2
     * @param k
     */
    public static void castle(int i, int j, int j2, int k){
        board[i][j].piece.moveCount++;
        board[i][j2].piece.moveCount++;
        board[i][j+(2*k)].piece = board[i][j].piece;
        board[i][j+k].piece = board[i][j2].piece;
        board[i][j2].piece = null;
    }

    /**takes in filerank coordinates and returns them in integer coordinates
     *
     * @param coord
     * @return
     */
    public static int[] getCoords(String coord){

        int i = coord.charAt(0)-97;
        int j = Math.abs(coord.charAt(1)-'0'-8);
        return new int[]{j, i};
    }

    /**prints out the chess board
     *
     */
    public static void printBoard(){

            for(int i =0;i<8;i++) {
                for (App.ChessSquare c : board[i]) {
                    if(c.piece==null)
                        //when the chess square is blank, it will print out its own color
                        System.out.print(c.color);
                    else
                        //when there is a chesspiece on the square
                        System.out.print(c.piece.name);
                    System.out.print(" ");
                }
                //after every square is printed, print the Y-axis file rank coord
                System.out.print(FileRankY[i]);
                System.out.println();
            }
            //After the loop is done, print every X-axis coord
            for(char x : FileRankX)
            {
                System.out.print(x + "  ");
            }
    }

    /**returns true if the move was successful, and false if there was an error
     *
     * @param where
     * @param to
     * @param colorturn
     * @return
     */
    public static boolean move(String where, String to, String colorturn){
        try {
            //cast board onto hidden if failure to move
            ChessPiece hidden[][] = ChessSquare.cast(board);
            //get the actual integer coordinates on the chessboard
            int i, j, i2, j2;
            int[] coords = getCoords(where);
            int[] coords2 = getCoords(to);
            i = coords[0];
            j = coords[1];
            i2 = coords2[0];
            j2 = coords2[1];
            ChessPiece A = board[i][j].piece;
            ChessPiece B = board[i2][j2].piece;

            //check if there's a piece on where
            if (A == null) return throwIllegalMove();
            //check if the piece of where matches the color of whose turn it is
            if (!A.color.equals(colorturn)) return throwIllegalMove();

            //check if that piece can make that move
            switch (A.getClass().toString()) {
                case "class ChessPieces.King":
                    if (A.checkPath(i, j, i2, j2, board)) {
                        if (board[i][j].piece instanceof Rook) { //this will trigger when a castle was successful and the current spot now contains a rook
                            if (King.check(colorturn, board, King.getLoc(colorturn, board))) {
                                //return everything back to its place
                                board = ChessSquare.reverse(hidden, board);
                                return throwIllegalMove();
                            } else return true;
                        } else break;
                    } else return throwIllegalMove();
                case "class ChessPieces.Queen":
                    if (A.checkPath(i, j, i2, j2, board)) break;
                    else return throwIllegalMove();
                case "class ChessPieces.Bishop":
                    if (A.checkPath(i, j, i2, j2, board)) break;
                    else return throwIllegalMove();
                case "class ChessPieces.Knight":
                    if (A.checkPath(i, j, i2, j2, board)) break;
                    else return throwIllegalMove();
                case "class ChessPieces.Rook":
                    if (A.checkPath(i, j, i2, j2, board)) break;
                    else return throwIllegalMove();
                case "class ChessPieces.Pawn":
                    if (A.checkPath(i, j, i2, j2, board)) break;

                    else return throwIllegalMove();


            }

            //check if there's a piece on to
            if (B != null) {   //check if the player is trying to put a piece on place already occupied by one of his own
                //unless you are successfully swapping king and rook, it should fail
                if (B.color.equals(A.color)) return throwIllegalMove();
                if (A instanceof Pawn) //reverse en passant
                {
                    board = ChessSquare.reverse(hidden, board);
                    //since a pawn can never go backwards it doesn't matter what color it is when a pawn reaches the edge
                    //if en passant didn't happen, nothing up to this point would have moved anyway
                    if(!(Math.abs(i-i2)==1) || !(Math.abs(j-j2)==1)) return throwIllegalMove();
                }

            }
            if(A instanceof Pawn)
                if (i2 == 0 || i2 == 7) {//promotion timee1 f3
                    try {
                        switch (promotion) {
                            case "Q":
                                A = new Queen(A.name.charAt(0) + "Q", A.color, A.moveCount);
                                break;
                            case "R":
                                A = new Rook(A.name.charAt(0) + "R", A.color, A.moveCount);
                                break;
                            case "B":
                                A = new Bishop(A.name.charAt(0) + "B", A.color, A.moveCount);
                                break;
                            case "K":
                                A = new Knight(A.name.charAt(0) + "K", A.color, A.moveCount);
                                break;
                        }
                    } catch (Exception e) {
                        A = new Queen(A.name.charAt(0) + "Q", A.color, A.moveCount);
                    }//default promotion queen
                }
            board[i2][j2].piece = A;
            board[i][j].piece = null;
            //check if player's king is in check
            if (King.check(colorturn, board, King.getLoc(colorturn, board))) {
                //return everything back to its place
                board = ChessSquare.reverse(hidden, board);
                return throwIllegalMove();
            }
            board[i2][j2].piece.moveCount++;
            return true;
        }catch (ArrayIndexOutOfBoundsException e){return throwIllegalMove();}
    }

    /**runs the game
     *
     * @param args
     */
    public static void main(String[] args) {
        //generate a new board on startup
        board = generateBoard();
        printBoard(); System.out.println("");
        Scanner s = new Scanner(System.in);
        String colorturn = "  ";
        while (true) {
            promotion = "Q";
            String[] t = s.nextLine().split(" ");
            if (t[0].equals("resign")) {
                throwVictory(endTurn(colorturn));
                break;
            }
            //see if draw
            try{
                switch (t[2]){
                    case "draw?": if(s.nextLine().equals("draw")) return; //throw draw
                        else break;
                    case "Q": promotion = "Q"; break;
                    case "R": promotion = "R"; break;
                    case "B": promotion = "B"; break;
                    case "N": promotion = "N"; break;
                }
                if (t[3].equals("draw?")) //for the case you promote and draw at the same time
                    if(s.nextLine().equals("draw")) return; //throw draw

            } catch (ArrayIndexOutOfBoundsException e){}
            try {
                if (!move(t[0], t[1], colorturn)) continue;
                else
                    printBoard(); System.out.println("");
                    switch (King.checkmate(endTurn(colorturn), board)) {
                        case 0:
                            colorturn = endTurn(colorturn);
                            continue;
                        case 1:
                            System.out.println("Check");
                            colorturn = endTurn(colorturn);
                            continue;
                        case 2:
                            System.out.println("Checkmate");
                            throwVictory(colorturn);
                            break;
                    }
            }catch (Exception e) {throwIllegalMove(); continue;}
        }
    }
}
