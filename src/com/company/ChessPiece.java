package com.company;

import java.io.Serializable;

public abstract class ChessPiece implements Serializable {
    private boolean white;
    private String name;

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

    public abstract boolean isMoveOk(Tile t1, Tile t2);

    public static ChessPiece createPieceFromName(String name, boolean isWhite) {
        // Well aware this should probably be made with the factory pattern within its constructors instead
        // Will have to do for now for solving reverting last move
        switch(name) {
            case "rook": return new Rook(isWhite);
            case "bishop": return new Bishop(isWhite);
            case "knight": return new Knight(isWhite);
            case "queen": return new Queen(isWhite);
            case "king": return new King(isWhite);
            case "pawn": return new Pawn(isWhite);
            default:
                return null;
        }
    }
}

