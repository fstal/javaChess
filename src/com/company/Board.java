package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Board extends JFrame implements ActionListener{
    Tile[][] tiles;
    private int size = 8;
    Game game;

    Board(Game game) {
        this.tiles = new Tile[size][size];
        this.game = game;

        for(int y = 0; y < size; y++){
            for(int x = 0; x < size; x++){
                tiles[x][y] = new Tile(x, y);
                //tiles[x][y].setActionCommand("Button " + x + " " + y);
                tiles[x][y].addActionListener(this);
                this.add(tiles[x][y]);
            }
        }

        setLayout(new GridLayout(size, size));
        getContentPane().setBackground(Color.lightGray);
        setVisible(true);
        setSize(900,900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        Tile tile = (Tile) source;
        game.selectTile(tile);
        //btn.getPos();
    }

}


class Tile extends JButton{
    int posX;
    int posY;
    String imgPath = new File("").getAbsolutePath() + "/images/"; //Move to chessPiece class


    Tile(int posX, int posY){
        this.posX  = posX;
        this.posY = posY;
        setColor();

        this.setIcon(new ImageIcon(imgPath + "knightWhite.png")); //Move to chessPiece class


    }

    void getPos(){
        System.out.println(this.posX + " " + this.posY);
        //getX getY?
        //[][] with pos?

        /*
        Change method to?:

        ChessPiece getPiece(){
            // Return chess piece if there is a piece
            // Return False if Tile is empty

        }*/



    }

    public int getXPos(){
        return posX;
    }

    public int getYPos(){
        return posY;
    }


    void setColor(){
        //Can probably be made better, but it works for now
        if((posY % 2) == 0 ){
            if((posX % 2) != 0 ){
                this.setBackground(Color.GRAY);
                this.setOpaque(true);
            }
            else{
                this.setBackground(Color.WHITE);
                this.setOpaque(true);
            }
        }
        else{
            if((posX % 2) == 0 ){
                this.setBackground(Color.GRAY);
                this.setOpaque(true);
            }
            else{
                this.setBackground(Color.WHITE);
                this.setOpaque(true);
            }
        }
    }

}