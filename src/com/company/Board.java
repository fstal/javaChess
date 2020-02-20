package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Board extends JFrame implements ActionListener{
    Tile[][] tiles;
    private int size = 8;

    Board() {
        this.tiles = new Tile[size][size];

        for(int y = 0; y < size; y++){
            for(int x = 0; x < size; x++){
                tiles[x][y] = new Tile(x, y);
                tiles[x][y].setActionCommand("Button " + x + " " + y);
                tiles[x][y].addActionListener(this);
                this.add(tiles[x][y]);
            }
        }

        setLayout(new GridLayout(size, size));

        getContentPane().setBackground(Color.lightGray);
        setVisible(true);
        setSize(400,200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        Tile btn = (Tile) source;
        btn.getPos();

    }

}


class Tile extends JButton{
    int posX;
    int posY;

    Tile(int posX, int posY){
        this.posX  = posX;
        this.posY = posY;
    }

    void getPos(){
        System.out.println(this.posX + " " + this.posY);
    }

    //getX getY?
    //[][] with pos?

    /*ChessPiece getPiece(){
        // Return chess piece if there is a piece
        // Return False if Tile is empty

    }*/
}