package ChessPieces;

public class ChessPiece {
    public String name;
    public String color;
    public int moveCount;
    public ChessPiece(String name,String color, int moveCount){
        this.name=name;
        this.color = color;
        this.moveCount=moveCount;
    }
}
