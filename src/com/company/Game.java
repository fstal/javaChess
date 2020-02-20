package com.company;

import javax.swing.*;

public class Game {

    private Boolean playerWhite = true;

    Game() {
        setTheme();
    }

    void selectTile(Tile tile) {
        tile.getPos();
        System.out.println(tile.getPiece());
        System.out.println(this.getClass().getName());
    }

    void endTurn() {
        playerWhite = !playerWhite;
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
