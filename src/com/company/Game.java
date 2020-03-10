package com.company;

import javax.swing.*;

public class Game {

    private Boolean isWhiteTurn = true;
    private Tile selectedTile = null;
    private Board board = null;

    Game() {
        setTheme();
    }

    void setBoard(Board board) {
        this.board = board;
    }

    void endTurn() {
        isWhiteTurn = !isWhiteTurn;
        board.setTurnLabel(isWhiteTurn);
        board.checkForCheck(isWhiteTurn);
    }

    void clickTile(Tile tile) {
        informationMessage("");
        //no select tile, chosen tile not empty, piece on tile match player turn
        if (selectedTile == null && tile.piece != null && tile.getPiece().getIsWhite() == isWhiteTurn) {
            updateSelectedTile(tile);
        }
        //has a selected tile
        else if (selectedTile != null) {
            if (obstructedMove(selectedTile, tile)) {
                //Oddly worded? obstructedMove currently returns true if not obstructed
                movePiece(selectedTile, tile);
            }
            else{
                informationMessage("WrongMove");
            }
            updateSelectedTile(null);
        }
    }

    void movePiece(Tile t1, Tile t2) {
        ChessPiece piece = t1.getPiece();
        ChessPiece piece2 = t2.getPiece();

        // Storing pieces in order to revert move in case of check
        String p1Name = piece.getName();
        String p2Name = t2.getPiece() == null ? "null" : t2.getPiece().getName(); // its just a "null" string for a moment, as createPieceFromName expects a string

        /*
        // Special case for preserving pawn's isFirstMove through reverting moves
        boolean pawn1FirstMove = true;
        boolean pawn2FirstMove = true;
        if(piece.getName().equals("pawn")) {
            Pawn pawn = (Pawn) piece;
            pawn1FirstMove = pawn.getIsFirstMove();
        }
        else if (piece2.getName().equals("pawn")) {
            Pawn pawn = (Pawn) piece;
            pawn2FirstMove = pawn.getIsFirstMove();
        }
         */

        // Special interactions for kings and pawns
        // Could split into own function
        if (piece.getName().equals("pawn")) {
            handlePawnMove(t2, (Pawn) piece);
        } else {
            t2.setPiece(t1.getPiece());
            if (piece.getName().equals("king")) {
                board.updateKingTile(t2);
            }
        }
        t1.setPiece(null);

        // Check if current player puts him/her-self in check, if true, reverse last move
        if (board.checkForCheck(isWhiteTurn)) { // checks for check for whoever's turn it currently is
            t1.setPiece(ChessPiece.createPieceFromName(p1Name, isWhiteTurn));
            t2.setPiece(ChessPiece.createPieceFromName(p2Name, !isWhiteTurn));
            informationMessage("MoveToCheck");
        } else {    // Else, update icons and end turn
            t1.updateIcon();
            t2.updateIcon();
            endTurn();
        }
    }

    void handlePawnMove(Tile t2, Pawn piece) {
        //  setIsFirstMove Could be called in isMoveOk for Pawn, would result in multiple lines of code tho *shrug*
        piece.setIsFirstMove();
        if (t2.getYPos() == 0 || t2.getYPos() == 7) {   // Pawn promotion
            promotePawn(piece, t2);
        } else {
            t2.setPiece(piece);
        }
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
        // Logic for marking possible moves for a selected piece
        for (int y = 0; y < board.size; y++) {
            for (int x = 0; x < board.size; x++) {
                Tile toTile = board.tiles[x][y];
                if (b) {
                    ChessPiece piece = t.getPiece();
                    if (piece.isMoveOk(t, toTile) && !isFriendly(t, toTile) && obstructedMove(t, toTile)) {
                        toTile.markPossibleMove();
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
        if (isFriendly(t1, t2)) return false;  //Early return for optimization *shrug*

        int x1 = t1.getXPos();
        int x2 = t2.getXPos();
        int y1 = t1.getYPos();
        int y2 = t2.getYPos();
        ChessPiece piece = t1.getPiece();
        Tile enemy = null;

        int diffHor = (t2.getXPos() - t1.getXPos());
        int diffVert = (t2.getYPos() - t1.getYPos());

        if (x1 == x2) {
            //Check N
            for (int i = y2; i < y1; i++) {
                Tile checkTile = board.tiles[x1][i];
                if (!piece.isMoveOk(t1, checkTile) || isFriendly(t1, checkTile)) {      //From here
                    return false;                   // Code between is same for every case
                }                                   // Can we split this logic to a function?
                if (isEnemy(t1, checkTile)) {        // Would reduce a lot of code
                    enemy = checkTile;               // Still need to be able to set enemy tho
                }                                                                       // To here

            }

            //Check S
            for (int i = y2; i > y1; i--) {
                Tile checkTile = board.tiles[x1][i];
                if (!piece.isMoveOk(t1, checkTile) || isFriendly(t1, checkTile)) {
                    return false;
                }
                if (isEnemy(t1, checkTile)) {
                    enemy = checkTile;
                }
            }
        }

        if (y1 == y2) {
            //Check W
            for (int i = x2; i < x1; i++) {
                Tile checkTile = board.tiles[i][y1];
                if (!piece.isMoveOk(t1, checkTile) || isFriendly(t1, checkTile)) { // move not okay, return false
                    return false;
                }
                if (isEnemy(t1, checkTile)) {
                    enemy = checkTile;
                }
            }
            //Check E
            for (int i = x2; i > x1; i--) {
                Tile checkTile = board.tiles[i][y1];
                if (!piece.isMoveOk(t1, checkTile) || isFriendly(t1, checkTile)) { // move not okay, return false
                    return false;
                }
                if (isEnemy(t1, checkTile)) {
                    enemy = checkTile;
                }
            }
        }

        if (diffVert > 0 && diffHor > 0) {
            //Check SE
            for (int i = x2, j = y2; i > x1 && j > y1; i--, j--) {
                Tile checkTile = board.tiles[i][j];
                if (!piece.isMoveOk(t1, checkTile) || isFriendly(t1, checkTile)) { // move not okay, return false
                    return false;
                }
                if (isEnemy(t1, checkTile)) {
                    enemy = checkTile;
                }
            }
        }

        if (diffHor < 0 && diffVert < 0) {
            //Check NW
            for (int i = x2, j = y2; i < x1 && j < y1; i++, j++) {
                Tile checkTile = board.tiles[i][j];
                if (!piece.isMoveOk(t1, checkTile) || isFriendly(t1, checkTile)) { // move not okay, return false
                    return false;
                }
                if (isEnemy(t1, checkTile)) {
                    enemy = checkTile;
                }
            }
        }

        if (diffHor > 0 && diffVert < 0) {
            //Check NE
            for (int i = x2, j = y2; i > x1 && j < y1; i--, j++) {
                Tile checkTile = board.tiles[i][j];
                if (!piece.isMoveOk(t1, checkTile) || isFriendly(t1, checkTile)) { // move not okay, return false
                    return false;
                }
                if (isEnemy(t1, checkTile)) {
                    enemy = checkTile;
                }
            }
        }

        if (diffHor < 0 && diffVert > 0) {
            //Check SW
            for (int i = x2, j = y2; i < x1 && j > y1; i++, j--) {
                Tile checkTile = board.tiles[i][j];
                if (!piece.isMoveOk(t1, checkTile) || isFriendly(t1, checkTile)) { // move not okay, return false
                    return false;
                }
                if (isEnemy(t1, checkTile)) {
                    enemy = checkTile;
                }
            }
        }

        if (enemy != null) { // If we have an enemy, and the enemy is not on the move-to tile, return false.
            return enemy.getYPos() == t2.getYPos() && enemy.getXPos() == t2.getXPos();
        }
        return true;
    }

    public static boolean isFriendly(Tile t1, Tile t2) {        // Both isFriendly() and isEnemy() necessary as it can also be null. isFriendly() != isEnemy()
        if (t2.getPiece() != null) {
            return (t1.getPiece().getIsWhite() == t2.getPiece().getIsWhite());
        }
        return false;
    }

    boolean isEnemy(Tile t1, Tile checkTile) {
        if (checkTile.getPiece() != null) {
            return checkTile.getPiece().getIsWhite() != t1.getPiece().getIsWhite();
        }
        return false;
    }

    void promotePawn(Pawn piece, Tile t2) {
        t2.setPiece(new Queen(piece.getIsWhite()));
        informationMessage("Promote", piece.getIsWhite());
    }

    void setTheme() {
        //From the Java documentation. Setting the theme of the program.
        //Loops through all themes, if it finds Nimbus it selects it.
        //UIManager requires try - except
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Nimbus theme not available");
        }
    }

    void informationMessage(String messageCase){
        switch (messageCase){
            case "WrongMove":
                board.setMessageLabel("That move is not allowed. Try Again!");
                break;
            case "Checked":
                board.setMessageLabel("You are in check! Move yourself out of check.");
                break;
            case "MoveToCheck":
                board.setMessageLabel("Invalid Move! That move puts your king in check!");
                break;
            default:
                board.setMessageLabel("");
        }

    }

    void informationMessage(String messageCase, Boolean isWhite){
        String color = (isWhite ? "White": "Black");
        switch (messageCase){
            case "Check":
                board.setMessageLabel(color + " king is in check!");
                break;
            case "Promote":
                board.setMessageLabel(color + " pawn is promoted to Queen!");
                break;
        }
    }


}
