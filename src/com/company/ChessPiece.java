package com.company;

public abstract class ChessPiece extends Game {
    private boolean white;
    private String name;
    //private Game game;

    public ChessPiece(boolean white, String name) {
        this.white = white;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public boolean getIsWhite() {
        return this.white;
    }

//    public boolean isNullMove(Tile t1, Tile t2) {         // Also checks for null moves
//        if (t1.getPiece() != null && t2.getPiece() != null) {
//            return (t1.getPiece().getIsWhite() == t2.getPiece().getIsWhite()); // if same color => friendly fire/null move => true
//        }
//        return false;
//    }

    public abstract boolean isMoveOk(Tile t1, Tile t2);

}

