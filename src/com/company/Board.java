package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Board extends JFrame implements ActionListener {
    Tile[][] tiles;
    private int size = 8;
    Game game;

    Board(Game game) {
        this.tiles = new Tile[size][size];
        this.game = game;
        newGame();
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

        setLayout(new GridLayout(size, size));
        getContentPane().setBackground(Color.lightGray);
        setVisible(true);
        setSize(900, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        Tile tile = (Tile) source;
        game.clickTile(tile);
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
        setColor();
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


    void setColor() {
        if ((posY % 2) == 0) {
            this.setBackground(((posX % 2) != 0) ? Color.GRAY : Color.WHITE);
        } else {
            this.setBackground((posX % 2) == 0 ? Color.GRAY : Color.WHITE);
        }
        this.setOpaque(true);
    }
}