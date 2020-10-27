package App;

import ChessPieces.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {

    public static char[] FileRankX = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'}; //the X-axis file rank
    public static char[] FileRankY = {'8','7','6','5','4','3','2','1'}; //the Y-axis file rank
    public static ArrayList<ChessPiece> takenWhite; //stores the white chesspieces that were taken
    public static ArrayList<ChessPiece> takenBlack; //stores the black chesspieces that were taken
    public static App.ChessSquare[][] board;

    public static App.ChessSquare[][] generateBoard() //manually generates a new board: an 8*8 Matrix of type ChessSquares which hold the value of their color and the piece that is on them
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
    //thrown when an illegal move is detected, returns false to prevent the game from going forward
    public static boolean throwIllegalMove(){
        System.out.println("illegal move, try again");
        return false;
    }
    //thrown when a chessmate or surrender is called
    public static void throwVictory(String colorturn){
        switch (colorturn){
            case "  " : System.out.println("White wins"); return;
            case "##" : System.out.println("Black wins"); return;
        }
    }
    //end turns switches colors to the next turn
    public static String endTurn(String colorturn) {
        switch (colorturn) {
            case "  ":
                return "##";
            case "##":
                return "  ";
        }
        return null;
    }
    //preforms en passant
    public static boolean enpassant(int i, int j, int k, String colorturn){
        //an en passant can only happen on the user's 5th respectivley
        switch (colorturn){
            case "##" : if(j==3){ takenBlack.add(board[i-k][j].piece); break;} else return false;
            case "  " : if(j==4) {takenWhite.add(board[i-k][j].piece); break;} else return false;
        }
        board[i-k][j].piece = null;
        return true;
    }
    public static int[] getCoords(String coord){ //takes in filerank coordinates and returns them in integer coordinates

        int i = coord.charAt(0)-97;
        int j = Math.abs(coord.charAt(1)-'0'-8);
        return new int[]{j, i};
    }
    public static void printBoard(){ //print's out the chess board

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
    public static boolean move(String where, String to, String colorturn){ //returns true if the move was successful, and false if there was an error
        //cast board onto hidden if failure to move
        ChessPiece hidden[][] = ChessSquare.cast(board);

        //get the actual integer coordinates on the chessboard
        int i,j,i2,j2;
        int[] coords = getCoords(where);
        int[] coords2 = getCoords(to);
        i=coords[0];j=coords[1];
        i2=coords2[0];j2=coords2[1];
        ChessPiece A = board[i][j].piece;
        ChessPiece B = board[i2][j2].piece;

        //check if there's a piece on where
        if(A == null) return throwIllegalMove();
        //check if the piece of where matches the color of whose turn it is
        if(!A.color.equals(colorturn)) return throwIllegalMove();

        //check if that piece can make that move
        switch(A.getClass().toString()){
            case "class ChessPieces.King" :
                if(A.checkPath(i,j,i2,j2,board)) break;
                else return throwIllegalMove();
            case "class ChessPieces.Queen" :
                if(A.checkPath(i,j,i2,j2,board)) break;
                else return throwIllegalMove();
            case "class ChessPieces.Bishop" :
                if(A.checkPath(i,j,i2,j2,board)) break;
                else return throwIllegalMove();
            case "class ChessPieces.Knight" :
                if(A.checkPath(i,j,i2,j2,board)) break;
                else return throwIllegalMove();
            case "class ChessPieces.Rook" :
                if(A.checkPath(i,j,i2,j2,board)) break;
                else return throwIllegalMove();
            case "class ChessPieces.Pawn" :
                if(A.checkPath(i,j,i2,j2,board)){
                    //check for en passant
                    break;
                }
                else return throwIllegalMove();


        }

        //check if there's a piece on to
        if(B!=null)
        {   //check if the player is trying to put a piece on place already occupied by one of his own
            //unless you are successfully swapping king and rook, it should fail
            if(B.color.equals(A.color)){
                if(Rook.checkCastle(i,j,i2,j2,A,B,board)){ //if you can do the swap

                }
                else return throwIllegalMove();
            }
            else{
                switch(B.color){
                    case "##" :
                        takenBlack.add(B);
                        break;
                    case " " :
                        takenWhite.add(B);
                }
            }
            if(A instanceof Pawn) //reverse en passant
            {
                //if en passant didn't happen, nothing up to this point would have moved anyway
                board = ChessSquare.reverse(hidden,board);
            }

        }
        board[i2][j2].piece = A;
        //check if player's king is in check
        if(King.check(colorturn,board,King.getLoc(colorturn,board))) {
            //return everything back to its place
            board = ChessSquare.reverse(hidden,board);
            return throwIllegalMove();
        }
        //There is how nothing on space A, so it becomes null
        board[i][j].piece = null;
        board[i2][j2].piece.moveCount ++;
        return true;
    }

    public static void main(String[] args) {
        //generate a new board on startup
        board = generateBoard();
        Scanner s = new Scanner(System.in);
        String colorturn = "  ";
        takenBlack = new ArrayList<ChessPiece>();
        takenWhite = new ArrayList<ChessPiece>();
        while (true) {
            printBoard(); System.out.println("");
            String[] t = s.nextLine().split(" ");
            if (t[0].equals("resign")) {
                throwVictory(endTurn(colorturn));
                break;
            }
            //see if draw
            try{
                if (t[2].equals("draw?"))
                    if(s.nextLine().equals("draw")) break; //throw draw
            } catch (ArrayIndexOutOfBoundsException e){}
            if (!move(t[0], t[1], colorturn)) continue;
            else
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

        }
    }
}
