package com.company;

import javax.swing.*;

public class Game {

    private Boolean isWhiteTurn = true;
    private Tile selectedTile = null;
    private Board board = null;

    Game() {
        setTheme();
    }

    void clickTile(Tile tile) {
        System.out.println("Clicked: " + tile.getXPos() + " " + tile.getYPos());

        //no select tile, chosen tile not empty, piece on tile match player turn
        if (selectedTile == null && tile.piece != null && tile.getPiece().getIsWhite() == isWhiteTurn) {
            updateSelectedTile(tile);
            //selectedTile = tile;
            System.out.println("SelectedTile is now: " + selectedTile.getXPos() + " " + selectedTile.getYPos());
            System.out.println("With the piece: " + selectedTile.getPiece().getName());
        }
        //has select tile
        else if (selectedTile != null) {
            // get selected piece
            ChessPiece selectPiece = selectedTile.getPiece();
            // move for piece to clicked tile is ok
            // includes check for ff and click on self
            if (selectPiece.isMoveOk(selectedTile, tile)) {
                movePiece(selectedTile, tile);
            }
            updateSelectedTile(null);
            //selectedTile = null;
            System.out.println("SelectedTile is now reset (null)");
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
        System.out.println("Moved " + t1.getXPos() + " " + t1.getYPos() + " to => " + t2.getXPos() + " " + t2.getYPos());

        ChessPiece piece = t1.getPiece();
        if (piece.getName().equals("pawn")) {
            handlePawnMove((Pawn) piece);
        }
        if(piece.getName().equals("pawn") && (t2.getYPos() == 0 || t2.getYPos() == 7)){
            promotePawn((Pawn) piece, t2);
        }
        else{
            t2.setPiece(t1.getPiece()); // Replace or move piece
        }
        t1.setPiece(null);          // Remove tile reference from to piece t1
        t1.updateIcon();            // Update Icons
        t2.updateIcon();
        endTurn();
    }

    public static boolean isNullMove(Tile t1, Tile t2) {         // Also checks for null moves
        if (t1.getPiece() != null && t2.getPiece() != null) {
            return (t1.getPiece().getIsWhite() == t2.getPiece().getIsWhite()); // if same color => friendly fire/null move => true
        }
        return false;
    }

    void handlePawnMove(Pawn piece) {
        piece.setIsFirstMove();
    }

    void promotePawn(Pawn piece, Tile t2){
        t2.setPiece(new Queen(piece.getIsWhite()));
    }

    void updateSelectedTile(Tile t) {
        if (t != null) {
            t.markSelected();
            updatePossibleMoves(t, true);
        } else {
            selectedTile.unmarkSelected();
            updatePossibleMoves(t, false);
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
                        toTile.markPossibleMove();
                    }
                } else {
                    toTile.setDefaultColor();
                }
            }
        }
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
                // not worth my time
            }
        }
    }
}
