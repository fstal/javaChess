package com.company;

public abstract class ChessPiece {
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

}

