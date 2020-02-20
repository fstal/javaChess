package com.company;

public abstract class ChessPiece {
    private boolean alive = true;
    private boolean white = true;
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

    public boolean getIsAlive() {
        return this.alive;
    }

    public void setIsAlive() {
        this.alive = !this.alive;
    }

    private boolean friendlyFire(Tile t1, Tile t2) {
        if (t1.getPiece() != null &&  t2.getPiece() != null) {
            return (t1.getPiece().getIsWhite() == t2.getPiece().getIsWhite()); // if same color > friendlyFire => true
        }
        return false;
    }

    private boolean sameTile (Tile t1, Tile t2) {
        return (t1.getXPos() == t2.getXPos() && t1.getYPos() == t2.getYPos());
    }

    public boolean resetMove (Tile t1, Tile t2) {
        return (friendlyFire(t1, t2) || sameTile(t1, t2));
    }

    public abstract boolean isMoveOk(Tile t1, Tile t2);

}

