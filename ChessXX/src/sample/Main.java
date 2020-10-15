package sample;

import ChessPieces.*;
import java.util.*;

public class Main {

    public static char[] FileRankX = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'}; //the X-axis file rank
    public static char[] FileRankY = {'8','7','6','5','4','3','2','1'}; //the Y-axis file rank
    public static ArrayList<ChessPiece> takenWhite; //stores the white chesspieces that were taken
    public static ArrayList<ChessPiece> takenBlack; //stores the black chesspieces that were taken
    public static ChessSquare[][] board;

    public static ChessSquare[][] generateBoard() //manually generates a new board: an 8*8 Matrix of type ChessSquares which hold the value of their color and the piece that is on them
    {
        ChessSquare[][] board =
                {
                        {new ChessSquare("  ",new Rook("bR","##",0)), new ChessSquare("##",new Knight("bN","##",0)), new ChessSquare("  ",new Bishop("bB","##",0)),new ChessSquare("##",new Queen("bQ","##",0)),new ChessSquare("  ",new King("bK","##",0)),new ChessSquare("##",new Bishop("bB","##",0)),new ChessSquare("  ",new Knight("bN","##",0)),new ChessSquare("##",new Rook("bR","##",0))},
                        {new ChessSquare("##",new Pawn("bp","##",0)), new ChessSquare("  ",new Pawn("bp","##",0)),new ChessSquare("##",new Pawn("bp","##",0)), new ChessSquare("  ",new Pawn("bp","##",0)),new ChessSquare("##",new Pawn("bp","##",0)), new ChessSquare("  ",new Pawn("bp","##",0)),new ChessSquare("##",new Pawn("bp","##",0)), new ChessSquare("  ",new Pawn("bp","##",0))},
                        {new ChessSquare("  ",null),new ChessSquare("##",null),new ChessSquare("  ",null),new ChessSquare("##",null),new ChessSquare("  ",null),new ChessSquare("##",null),new ChessSquare("  ",null),new ChessSquare("##",null)},
                        {new ChessSquare("##",null),new ChessSquare("  ",null),new ChessSquare("##",null),new ChessSquare("  ",null),new ChessSquare("##",null),new ChessSquare("  ",null),new ChessSquare("##",null),new ChessSquare("  ",null)},
                        {new ChessSquare("  ",null),new ChessSquare("##",null),new ChessSquare("  ",null),new ChessSquare("##",null),new ChessSquare("  ",null),new ChessSquare("##",null),new ChessSquare("  ",null),new ChessSquare("##",null)},
                        {new ChessSquare("##",null),new ChessSquare("  ",null),new ChessSquare("##",null),new ChessSquare("  ",null),new ChessSquare("##",null),new ChessSquare("  ",null),new ChessSquare("##",null),new ChessSquare("  ",null)},
                        {new ChessSquare("  ",new Pawn("wp","  ",0)), new ChessSquare("##",new Pawn("wp","  ",0)),new ChessSquare("  ",new Pawn("wp","  ",0)), new ChessSquare("##",new Pawn("wp","  ",0)),new ChessSquare("  ",new Pawn("wp","  ",0)), new ChessSquare("##",new Pawn("wp","  ",0)),new ChessSquare("  ",new Pawn("wp","  ",0)), new ChessSquare("##",new Pawn("wp","  ",0))},
                        {new ChessSquare("##",new Rook("wR","  ",0)), new ChessSquare("  ",new Knight("wN","  ",0)), new ChessSquare("##",new Bishop("wB","  ",0)),new ChessSquare("  ",new Queen("wQ","  ",0)),new ChessSquare("##",new King("wK","  ",0)),new ChessSquare("  ",new Bishop("wB","  ",0)),new ChessSquare("##",new Knight("wN","  ",0)),new ChessSquare("  ",new Rook("wR","  ",0))}
                };
        return board;
    }

    public static int[] getCoords(String coord){ //takes in filerank coordinates and returns them in integer coordinates

        int i = coord.charAt(0)-97;
        int j = Math.abs(coord.charAt(1)-'0'-8);
        return new int[]{j, i};
    }
    public static void printBoard(){ //print's out the chess board

            for(int i =0;i<8;i++) {
                for (ChessSquare c : board[i]) {
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
    public static int move(String where, String to, String colorturn){ //returns 1 if the move was successful, and 0 if there was an error
        //get the actual integer coordinates on the chessboard
        int i,j,i2,j2;
        int[] coords = getCoords(where);
        int[] coords2 = getCoords(to);
        i=coords[0];j=coords[1];
        i2=coords2[0];j2=coords2[1];
        ChessPiece A = board[i][j].piece;
        ChessPiece B = board[i2][j2].piece;
        //check if there's a piece on where
        if(A == null) {
            System.out.print("There is no chess piece to move.");
            return 0;
        }
        //check if the piece of where matches the color of whose turn it is
        if(!A.color.equals(colorturn)){
            System.out.println("That piece is not your color");
            return 0;
        }
        //check if that piece can make that move
        switch(A.getClass().toString()){
            case "class ChessPieces.King" :
                break;
            case "class ChessPieces.Queen" :
                break;
            case "class ChessPieces.Bishop" :
                break;
            case "class ChessPieces.Knight" :
                if(A.checkPath(i,j,i2,j2,board)){
                    break;
                }
                else {
                    System.out.println("Knight cannot move there");
                    return 0;
                }
            case "class ChessPieces.Rook" :
                if(A.checkPath(i,j,i2,j2,board)){
                    break;
                }
                else{
                    System.out.println("Rook cannot move there");
                    return 0;
            }

            case "class ChessPieces.Pawn" :
                //pawn can only move up 1, or 2 at the very begining and can only move sideways forward if an enemy chessPiece is there

                break;
        }
        System.out.println(i2+","+j2);
        //check if there's a piece on to
        if(B!=null)
        {   //check if the player is trying to put a piece on place already occupied by one of his own
            //unless you are successfully swapping king and rook, it should fail
            if(B.color.equals(A.color)){
                if(checkKRswap(i,j,i2,j2,A,B)){ //if you can do the swap

                }
                else {
                    System.out.println("The place you are trying to move to is already occupied by one of your own");
                    return 0;
                }
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

        }
        board[i2][j2].piece = A;
        //check if player's king is in check
        if(checkCheck(colorturn)) {
            System.out.println("You are in check");
            //return B back to its place
            board[i][j].piece = A;
            board[i2][j2].piece = B;
            return 0;
        }
        //There is how nothing on space A, so it becomes null
        board[i][j].piece = null;
        return 1;
    }
    //checks to see if king is in check
    public static boolean checkCheck(String colorturn)
    {
        return false;
    }
    //checks to see if you can succesfully swap king and rook
    public static boolean checkKRswap(int i,int j,int i2,int j2,ChessPiece A,ChessPiece B){

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
    public static void main(String[] args) {
        //generate a new board on startup
        board = generateBoard();

        printBoard();
        move("g1","h3","  "); printBoard();
        System.out.println();
        int [] a = getCoords("c7");
        System.out.println(a[0]+","+a[1]);

    }
}
