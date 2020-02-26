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
        //System.out.println("Clicked: " + tile.getXPos() + " " + tile.getYPos());

        //no select tile, chosen tile not empty, piece on tile match player turn
        if (selectedTile == null && tile.piece != null && tile.getPiece().getIsWhite() == isWhiteTurn) {
            updateSelectedTile(tile);
            //selectedTile = tile;
            //System.out.println("SelectedTile is now: " + selectedTile.getXPos() + " " + selectedTile.getYPos());
            //System.out.println("With the piece: " + selectedTile.getPiece().getName());
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
            //System.out.println("SelectedTile is now reset (null)");
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
        //System.out.println("Moved " + t1.getXPos() + " " + t1.getYPos() + " to => " + t2.getXPos() + " " + t2.getYPos());

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
                        if(obstructedMove(t, toTile)){
                            toTile.markPossibleMove();
                        }
                    }
                } else {
                    toTile.setDefaultColor();
                }
            }
        }
    }

    public boolean obstructedMove(Tile t1, Tile t2){
        int x1 = t1.getXPos();
        int x2 = t2.getXPos();
        int y1 = t1.getYPos();
        int y2 = t2.getYPos();
        ChessPiece piece = t1.getPiece();
        int enemyCount = 0; //If we have more than one enemy -> return false

        int diffHor = (t2.getXPos() - t1.getXPos());
        int diffVert = (t2.getYPos() - t1.getYPos());

        if(x1 == x2){
            //Check N
            for(int i = y2; i < y1; i++){
                Tile checkTile = board.tiles[x1][i];
                if(!piece.isMoveOk(t1, checkTile)){
                    return false;
                }
                if(countEnemy(t1, checkTile)){
                    enemyCount++;
                }
            }

            //Check S
            for(int i = y2; i > y1; i--){
                Tile checkTile = board.tiles[x1][i];
                if(!piece.isMoveOk(t1, checkTile)){
                    return false;
                }
                if(countEnemy(t1, checkTile)){
                    enemyCount++;
                }
            }
        }

        if(y1 == y2){
            //Check W
            for(int i = x2; i < x1; i++){
                Tile checkTile = board.tiles[i][y1];
                if(!piece.isMoveOk(t1, checkTile)){
                    return false;
                }
                if(countEnemy(t1, checkTile)){
                    enemyCount++;
                }
            }
            //Check E
            for(int i = x2; i > x1; i--){
                Tile checkTile = board.tiles[i][y1];
                if(!piece.isMoveOk(t1, checkTile)){
                    return false;
                }
                if(countEnemy(t1, checkTile)){
                    enemyCount++;
                }
            }
        }

        if(diffVert > 0 && diffHor > 0  ){
            //Check SE
            for(int i = x2, j = y2; i > x1 && j > y1; i--, j--){
                System.out.println("SE " + x2 + " " + y2 );
                Tile checkTile = board.tiles[i][j];
                if(!piece.isMoveOk(t1, checkTile)){
                    return false;
                }
                if(countEnemy(t1, checkTile)){
                    enemyCount++;
                }
            }
        }

        if(diffHor < 0 && diffVert < 0){
            //Check NW
            for(int i = x2, j = y2; i < x1 && j < y1; i++, j++){
                System.out.println("NW " + x2 + " " + y2 );
                Tile checkTile = board.tiles[i][j];
                if(!piece.isMoveOk(t1, checkTile)){
                    return false;
                }
                if(countEnemy(t1, checkTile)){
                    enemyCount++;
                }
            }
        }

        if(diffHor > 0 && diffVert < 0){
            //Check NE
            for(int i = x2, j = y2; i > x1 && j < y1; i--, j++){
                System.out.println("NW " + x2 + " " + y2 );
                Tile checkTile = board.tiles[i][j];
                if(!piece.isMoveOk(t1, checkTile)){
                    return false;
                }
                if(countEnemy(t1, checkTile)){
                    enemyCount++;
                }
            }
        }

        if(diffHor < 0 && diffVert > 0){
            //Check SW
            for(int i = x2, j = y2; i < x1 && j > y1; i++, j--){
                System.out.println("NW " + x2 + " " + y2 );
                Tile checkTile = board.tiles[i][j];
                if(!piece.isMoveOk(t1, checkTile)){
                    return false;
                }
                if(countEnemy(t1, checkTile)){
                    enemyCount++;
                }
            }
        }

        return enemyCount <= 1;  //if all else has gone through and enemycount <= 1 -> return True
    }

    boolean countEnemy(Tile t1, Tile checkTile){
        //obstructedMove helper-function
        if(checkTile.getPiece() != null) {
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
                // not worth my time
            }
        }
    }
}
