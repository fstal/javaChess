package com.company;

public abstract class ChessPiece {
    private boolean alive = true;
    private boolean white = true;

    public ChessPiece(boolean white) {
        this.white = white;
    }

    public void setWhite(boolean isWhite) {
        this.white = isWhite;
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
        return(t1.getPiece().isWhite == t2.getPiece().isWhite); // if same color > friendlyFire => true
    }

    private boolean sameTile (Tile t1, Tile t2) {
        return (t1.getXPos() == t2.getXPos() && t1.getYPos() == t2.getYPos());
    }

    public boolean resetMove (Tile t1, Tile t2) {
        return (friendlyFire(t1, t2) || sameTile(t1, t2));
    }

    public abstract boolean isMoveOk(Tile t1, Tile t2);

}

