package com.company;


class King extends ChessPiece {
    static String name = "king";

    public King(boolean white) {
        super(white, name);
    }

    @Override
    public boolean isMoveOk(Tile t1, Tile t2) {
        int x = Math.abs(t1.getXPos() - t2.getXPos());
        int y = Math.abs(t1.getYPos() - t2.getYPos());

        return (x < 2 && y < 2 && 0 < x + y);
    }
}


class Queen extends ChessPiece {
    static String name = "queen";

    public Queen(boolean white) {
        super(white, name);
    }

    @Override
    public boolean isMoveOk(Tile t1, Tile t2) {
        int x1 = t1.getXPos();
        int x2 = t2.getXPos();
        int y1 = t1.getYPos();
        int y2 = t2.getYPos();
        if (x1 == x2 && y1 == y2) return false; // samma som start

        int x = Math.abs(x1 - x2);
        int y = Math.abs(y1 - y2);

        return ((x1 == x2 || y1 == y2) || x - y == 0);
    }
}


class Knight extends ChessPiece {
    static String name = "knight";

    public Knight(boolean white) {
        super(white, name);
    }

    @Override
    public boolean isMoveOk(Tile t1, Tile t2) {
        int x = Math.abs(t1.getXPos() - t2.getXPos());
        int y = Math.abs(t1.getYPos() - t2.getYPos());

        return (x * y == 2);
    }
}


class Bishop extends ChessPiece {
    static String name = "bishop";

    public Bishop(boolean white) {
        super(white, name);
    }

    @Override
    public boolean isMoveOk(Tile t1, Tile t2) {
        int x = Math.abs(t1.getXPos() - t2.getXPos());
        int y = Math.abs(t1.getYPos() - t2.getYPos());

        return (x - y == 0);
    }
}

class Rook extends ChessPiece {
    static String name = "rook";

    public Rook(boolean white) {
        super(white, name);
    }

    @Override
    public boolean isMoveOk(Tile t1, Tile t2) {
        int x1 = t1.getXPos();
        int x2 = t2.getXPos();
        int y1 = t1.getYPos();
        int y2 = t2.getYPos();
        return (x1 == x2 || y1 == y2);
    }
}

class Pawn extends ChessPiece {
    static String name = "pawn";
    boolean isFirstMove = true;

    public Pawn(boolean white) {
        super(white, name);
    }

    @Override
    public boolean isMoveOk(Tile t1, Tile t2) {
        int x1 = t1.getXPos();
        int x2 = t2.getXPos();
        int y1 = t1.getYPos();
        int y2 = t2.getYPos();

        int sign = (getIsWhite() ? -1 : 1);

        if (t2.getPiece() == null) { // no enemy
            if (isFirstMove) { // move straight one or two steps in the correct direction
                return (x1 == x2 && (y2 == y1 + sign || y2 == y1 + 2 * sign));
            } // move straight one step
            else {
                return (x1 == x2 && y2 == y1 + sign);
            }
        }   // if attack, allow diagonal move
        else {
            return ((y2 == y1 + sign) && x2 == x1 - 1) || ((y2 == y1 + sign) && x2 == x1 + 1);
        }
    }

    public void setIsFirstMove() {
        this.isFirstMove = false;
    }
}