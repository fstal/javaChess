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
            selectedTile = tile;
            System.out.println("SelectedTile is now: " + selectedTile.getXPos() + " " + selectedTile.getYPos());
            System.out.println("With the piece: " + selectedTile.getPiece().getName());
        }
        //has select tile,
        else if (selectedTile != null) {
            //selected piece
            ChessPiece selectPiece = selectedTile.getPiece();
            // move for piece to clicked tile is ok
            // includes check for ff and click on self
            if (selectPiece.isMoveOk(selectedTile, tile)) {
                movePiece(selectedTile, tile);
            }
            selectedTile = null;
            System.out.println("SelectedTile is now reset (null)");
        }
    }

    void setBoard(Board board){this.board = board;}

    void endTurn() {
        isWhiteTurn = !isWhiteTurn;
        board.setTurnLabel(isWhiteTurn);
    }

    void movePiece(Tile t1, Tile t2) {
        System.out.println("Moved " + t1.getXPos() + " " + t1.getYPos() + " to => " + t2.getXPos() + " " + t2.getYPos());

        ChessPiece piece = t1.getPiece();
        if(piece.getName().equals("pawn")) {
            handlePawnMove((Pawn) piece);
        }
        t2.setPiece(t1.getPiece()); // Replace or move piece
        t1.setPiece(null);          // Remove tile reference from to piece t1
        t1.updateIcon();            // Update Icons
        t2.updateIcon();
        endTurn();
    }

    void handlePawnMove(Pawn piece) {
        piece.setIsFirstMove();
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
