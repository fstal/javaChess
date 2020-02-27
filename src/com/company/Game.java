package com.company;

import javax.swing.*;

public class Game {

    private Boolean isWhiteTurn = true;
    private Tile selectedTile = null;
    private Board board = null;

    Game() { setTheme(); }

    void clickTile(Tile tile) {
        //no select tile, chosen tile not empty, piece on tile match player turn
        if (selectedTile == null && tile.piece != null && tile.getPiece().getIsWhite() == isWhiteTurn) {
            updateSelectedTile(tile);
            System.out.println(selectedTile.getPiece());
        }
        //has select tile
        else if (selectedTile != null) {
            if (obstructedMove(selectedTile, tile)) {
                movePiece(selectedTile, tile);
            }
            updateSelectedTile(null);
        }
    }

    void setBoard(Board board) {
        this.board = board;
    }

    void endTurn() {
        isWhiteTurn = !isWhiteTurn;
        board.setTurnLabel(isWhiteTurn);
    }

    void movePiece(Tile t1, Tile t2) {
        ChessPiece piece = t1.getPiece();
        if (piece.getName().equals("pawn")) {
            handlePawnMove(t2, (Pawn) piece);
        }
        t2.setPiece(t1.getPiece()); // Replace or move piece
        t1.setPiece(null);          // Remove tile reference from to piece t1
        t1.updateIcon();            // Update Icons
        t2.updateIcon();
        endTurn();
    }

    public static boolean isFriendly(Tile t1, Tile t2) {
        if (t2.getPiece() != null) {
            return (t1.getPiece().getIsWhite() == t2.getPiece().getIsWhite());
        }
        return false;
    }

    void handlePawnMove(Tile t2,Pawn piece) {
        piece.setIsFirstMove();
        if (t2.getYPos() == 0 || t2.getYPos() == 7) {
            promotePawn(piece, t2);
        }
    }

    void promotePawn(Pawn piece, Tile t2) {
        t2.setPiece(new Queen(piece.getIsWhite()));
    }

    void updateSelectedTile(Tile t) {
        if (t != null) {
            t.markSelected();
            updatePossibleMoves(t, true);
        } else {
            selectedTile.unmarkSelected();
            updatePossibleMoves(null, false);
        }
        selectedTile = t;
    }

    void updatePossibleMoves(Tile t, boolean b) {
        for (int y = 0; y < board.size; y++) {
            for (int x = 0; x < board.size; x++) {
                Tile toTile = board.tiles[x][y];
                if (b) {
                    ChessPiece piece = t.getPiece();
                    if (piece.isMoveOk(t, toTile)) {
                        if (obstructedMove(t, toTile)) {
                            toTile.markPossibleMove();
                        }
                    }
                } else {
                    toTile.setDefaultColor();
                }
            }
        }
    }

    public boolean obstructedMove(Tile t1, Tile t2) {
        //Checks if move is obstructed. Always starts at t2 and checks all Tiles towards t1
        //Knight will always get true from obstructedMove since it doesn't fit if-conditions
        int x1 = t1.getXPos();
        int x2 = t2.getXPos();
        int y1 = t1.getYPos();
        int y2 = t2.getYPos();
        ChessPiece piece = t1.getPiece();
        Tile enemy = null;

        int diffHor = (t2.getXPos() - t1.getXPos());
        int diffVert = (t2.getYPos() - t1.getYPos());

        if (isFriendly(t1, t2)) return false; //Checking for null-move here instead of in isMoveOk()

        if (x1 == x2) {
            //Check N
            for (int i = y2; i < y1; i++) {
                Tile checkTile = board.tiles[x1][i];
                if (!piece.isMoveOk(t1, checkTile)) {
                    return false;
                }
                if (checkEnemy(t1, checkTile)) {
                    enemy = checkTile;
                }
            }

            //Check S
            for (int i = y2; i > y1; i--) {
                Tile checkTile = board.tiles[x1][i];
                if (!piece.isMoveOk(t1, checkTile)) {
                    return false;
                }
                if (checkEnemy(t1, checkTile)) {
                    enemy = checkTile;
                }
            }
        }

        if (y1 == y2) {
            //Check W
            for (int i = x2; i < x1; i++) {
                Tile checkTile = board.tiles[i][y1];
                if (!piece.isMoveOk(t1, checkTile)) {
                    return false;
                }
                if (checkEnemy(t1, checkTile)) {
                    enemy = checkTile;
                }
            }
            //Check E
            for (int i = x2; i > x1; i--) {
                Tile checkTile = board.tiles[i][y1];
                if (!piece.isMoveOk(t1, checkTile)) {
                    return false;
                }
                if (checkEnemy(t1, checkTile)) {
                    enemy = checkTile;
                }
            }
        }

        if (diffVert > 0 && diffHor > 0) {
            //Check SE
            for (int i = x2, j = y2; i > x1 && j > y1; i--, j--) {
                Tile checkTile = board.tiles[i][j];
                if (!piece.isMoveOk(t1, checkTile)) {
                    return false;
                }
                if (checkEnemy(t1, checkTile)) {
                    enemy = checkTile;
                }
            }
        }

        if (diffHor < 0 && diffVert < 0) {
            //Check NW
            for (int i = x2, j = y2; i < x1 && j < y1; i++, j++) {
                Tile checkTile = board.tiles[i][j];
                if (!piece.isMoveOk(t1, checkTile)) {
                    return false;
                }
                if (checkEnemy(t1, checkTile)) {
                    enemy = checkTile;
                }
            }
        }

        if (diffHor > 0 && diffVert < 0) {
            //Check NE
            for (int i = x2, j = y2; i > x1 && j < y1; i--, j++) {
                Tile checkTile = board.tiles[i][j];
                if (!piece.isMoveOk(t1, checkTile)) {
                    return false;
                }
                if (checkEnemy(t1, checkTile)) {
                    enemy = checkTile;
                }
            }
        }

        if (diffHor < 0 && diffVert > 0) {
            //Check SW
            for (int i = x2, j = y2; i < x1 && j > y1; i++, j--) {
                Tile checkTile = board.tiles[i][j];
                if (!piece.isMoveOk(t1, checkTile)) {
                    return false;
                }
                if (checkEnemy(t1, checkTile)) {
                    enemy = checkTile;
                }
            }
        }

        if (enemy != null) { //If we have an enemy, and the enemy is not on the move-to tile, return false.
            return enemy.getYPos() == t2.getYPos() && enemy.getXPos() == t2.getXPos();
        }
        return true;
    }

    boolean checkEnemy(Tile t1, Tile checkTile) {
        //ObstructedMove helper-function. Checks if Tile is an enemy
        //Could probably use "isNullMove()", but that might be hard to understand as reader
        if (checkTile.getPiece() != null) {
            return checkTile.getPiece().getIsWhite() != t1.getPiece().getIsWhite();
        }
        return false;
    }

    void setTheme() {
        //Copied from StackOverflow
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, fall back to cross-platform
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }
}
