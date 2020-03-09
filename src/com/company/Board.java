package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Board extends JFrame implements ActionListener {
    Tile[][] tiles;
    int size = 8;
    private JLabel turnLabelWhite, turnLabelBlack;
    private Tile whiteKingTile;
    private Tile blackKingTile;

    Game game;

    Board(Game game) {
        this.tiles = new Tile[size][size];
        this.game = game;
        newGame();
        whiteKingTile = tiles[4][7];
        blackKingTile = tiles[4][0];
    }

    Board(Game game, Tile[][] tiles, Tile wkt, Tile bkt) {
        this.tiles = tiles;
        this.game = game;
        whiteKingTile = wkt;
        blackKingTile = bkt;
        game.setBoard(this);
    }

    public void newGame() {
        //Rooks
        tiles[0][0] = new Tile(0, 0, new Rook(false));
        tiles[7][0] = new Tile(7, 0, new Rook(false));
        tiles[0][7] = new Tile(0, 7, new Rook(true));
        tiles[7][7] = new Tile(7, 7, new Rook(true));

        //Bishops
        tiles[1][0] = new Tile(1, 0, new Bishop(false));
        tiles[6][0] = new Tile(6, 0, new Bishop(false));
        tiles[1][7] = new Tile(1, 7, new Bishop(true));
        tiles[6][7] = new Tile(6, 7, new Bishop(true));

        //Knights
        tiles[2][0] = new Tile(2, 0, new Knight(false));
        tiles[5][0] = new Tile(5, 0, new Knight(false));
        tiles[2][7] = new Tile(2, 7, new Knight(true));
        tiles[5][7] = new Tile(5, 7, new Knight(true));

        //Queens
        tiles[3][0] = new Tile(3, 0, new Queen(false));
        tiles[3][7] = new Tile(3, 7, new Queen(true));

        //Kings
        tiles[4][0] = new Tile(4, 0, new King(false));
        tiles[4][7] = new Tile(4, 7, new King(true));

        //Pawns
        for (int x = 0; x < size; x++) {
            tiles[x][1] = new Tile(x, 1, new Pawn(false));
        }
        for (int x = 0; x < size; x++) {
            tiles[x][6] = new Tile(x, 6, new Pawn(true));
        }

        //Empty tiles, add, listeners
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (y > 1 && y < 6) {
                    tiles[x][y] = new Tile(x, y, null);
                }
                this.add(tiles[x][y]);
                tiles[x][y].addActionListener(this);
            }
        }

        setupTurnLabels();
        setLayout(new GridLayout(size + 1, size));
        getContentPane().setBackground(Color.lightGray);
        setVisible(true);
        setSize(900, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Provide Board to Game
        game.setBoard(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        game.clickTile((Tile) e.getSource());
    }

    void updateKingTile(Tile t) {
        if (t.getPiece().getIsWhite()) {
            whiteKingTile = t;
        } else {
            blackKingTile = t;
        }
    }

    public Tile getWhiteKingTile() {return whiteKingTile;}
    public Tile getBlackKingTile() {return blackKingTile;}

    boolean checkForCheck(boolean isWhiteTurn) {
        // Checks for check for the provided color (true => white, false => black)
        Tile kingTile = isWhiteTurn ? whiteKingTile : blackKingTile;
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                Tile t = tiles[x][y];
                if (t.getPiece() != null && (t.getPiece().getIsWhite() != isWhiteTurn)) { // tile has piece and is enemy
                    if (t.getPiece().isMoveOk(t, kingTile) && game.obstructedMove(t, kingTile)) {
                        System.out.println("Your king is currently in check!");
                        return true;
                    }
                }

            }
        }
        return false;
    }

    void checkForCheckmate() {
        // To be implemented if necessary?
    }

    void setupTurnLabels() {
        turnLabelWhite = new JLabel("White", SwingConstants.CENTER);
        turnLabelWhite.setBackground(Color.WHITE);
        turnLabelWhite.setOpaque(true);
        turnLabelBlack = new JLabel("Black", SwingConstants.CENTER);
        turnLabelBlack.setBackground(Color.BLACK);
        turnLabelBlack.setForeground(Color.WHITE);
        turnLabelBlack.setOpaque(true);
        turnLabelBlack.setVisible(false);
        for (int i = 0; i < 3; i++) {
            //Adding empty labels for style, seriously, this seems to be the prefered way
            this.add(new JLabel());
        }
        this.add(turnLabelWhite);
        this.add(turnLabelBlack);
    }

    public void setTurnLabel(Boolean isWhiteTurn) {
        turnLabelWhite.setVisible(isWhiteTurn);
        turnLabelBlack.setVisible(!isWhiteTurn);
    }

    public Tile[][] deepCopyBoard() {
        // Should return a deep clone of the board
        // First implemented as an alternative way of checking for check
        Tile[][] tilesClone = new Tile[tiles.length][];
        for (int r = 0; r < tiles.length; r++) {
            tilesClone[r] = tiles[r].clone();
        }
        return tilesClone;
    }
}


class Tile extends JButton {
    int posX;
    int posY;
    ChessPiece piece;
    String imgPath = new File("").getAbsolutePath() + "/images/"; //Move to chessPiece class

    Tile(int posX, int posY, ChessPiece piece) {
        this.posX = posX;
        this.posY = posY;
        this.piece = piece;
        setDefaultColor();
        updateIcon();
    }

    void updateIcon() {
        if (this.piece != null) {
            this.setIcon(
                    new ImageIcon(
                            imgPath + piece.getName() + (piece.getIsWhite()
                                    ? "White"
                                    : "Black")
                                    + ".png"));
        } else this.setIcon(null);
    }

    void markPossibleMove() {
        this.setBackground(Color.GREEN);
    }

    void markSelected() {
        this.setBackground(Color.YELLOW);
    }

    void unmarkSelected() {
        this.setDefaultColor();
    }

    ChessPiece getPiece() {
        return piece;
    }

    void setPiece(ChessPiece p) {
        this.piece = p;
    }

    public int getXPos() {
        return posX;
    }

    public int getYPos() {
        return posY;
    }

    void setDefaultColor() { 
        if ((posY % 2) == 0) {
            this.setBackground(((posX % 2) != 0) ? Color.GRAY : Color.WHITE);
        } else {
            this.setBackground((posX % 2) == 0 ? Color.GRAY : Color.WHITE);
        }
        this.setOpaque(true);
    }
}