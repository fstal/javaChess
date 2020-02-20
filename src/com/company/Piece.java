package com.company;


public class King extends ChessPiece {

    public King(boolean white) {
        super(white);
    }

    @Override
    public boolean isMoveOk(Tile t1, Tile t2) {
        if (resetMove(t1, t2)) return false; // or actually reset

        int x = Math.abs(t1.getXPos() - t2.getXPos());
        int y = Math.abs(t1.getYPos() - t2.getYPos());

        return (x < 2 && y < 2 && 0 < x + y && x + y < 3);
    }
}


public class Queen extends ChessPiece {

    public Queen(boolean white) {
        super(white);
    }

    @Override
    public boolean isMoveOk(Tile t1, Tile t2) {
        if (resetMove(t1, t2)) return false; // or actually reset

        int x1 = t1.getXPos();
        int x2 = t2.getXPos();
        int y1 = t1.getYPos();
        int y2 = t2.getYPos();

        int x = Math.abs(x1 - x2);
        int y = Math.abs(y1 - y2);

        if (x1 == x2 && y1 == y2) return false; // samma som start
        return ((x1 == x2 || y1 == y2) || x - y == 0);
    }
}


public class Knight extends ChessPiece {

    public Knight(boolean white) {
        super(white);
    }

    @Override
    public boolean isMoveOk(Tile t1, Tile t2) {
        if (resetMove(t1, t2)) return false; // or actually reset

        int x = Math.abs(t1.getXPos() - t2.getXPos());
        int y = Math.abs(t1.getYPos() - t2.getYPos());

        return (x * y == 2);
    }
}


public class Bishop extends ChessPiece {

    public Bishop(boolean white) {
        super(white);
    }

    @Override
    public boolean isMoveOk(Tile t1, Tile t2) {
        if (resetMove(t1, t2)) return false; // or actually reset

        int x = Math.abs(t1.getXPos() - t2.getXPos());
        int y = Math.abs(t1.getYPos() - t2.getYPos());

        return (x - y == 0);
    }
}

public class Rook extends ChessPiece {

    public Rook(boolean white) {
        super(white);
    }

    @Override
    public boolean isMoveOk(Tile t1, Tile t2) {
        if (resetMove(t1, t2)) return false; // or actually reset
        int x1 = t1.getXPos();
        int x2 = t2.getXPos();
        int y1 = t1.getYPos();
        int y2 = t2.getYPos();
        return (x1 == x2 || y1 == y2);
    }
}

public class Pawn extends ChessPiece {
    boolean isFirstMove = true;

    public Pawn(boolean white) {
        super(white);
    }

    @Override
    public boolean isMoveOk(Tile t1, Tile t2) {
        if (resetMove(t1, t2)) return false; // or actually reset

        int x1 = t1.getXPos();
        int x2 = t2.getXPos();
        int y1 = t1.getYPos();
        int y2 = t2.getYPos();

        int sign = (getIsWhite() ? -1 : 1);

        if (isFirstMove) {
            return (x1 == x2 && (y2 == y1 + sign || y2 == y1 + 2 * sign));
        }
        return false;

    }
}
