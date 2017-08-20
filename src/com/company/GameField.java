package com.company;

import javax.swing.*;
import java.awt.*;

public class GameField extends JPanel {

    // VARIABLES HERE =============================================
    private final int SPEED = 5;
    private int shipX, shipY;
    private int asteroidX, asteroidY;
    private Image ship;
    private Image asteroid;
    private Image shot;

    // ============================================================

    public GameField(){

        setBackground(Color.BLACK);

        // here: pains stars...white dots, randomly

        loadImages();

        createShip();

    }

    public void loadImages(){

        ImageIcon shipIcon = new ImageIcon("/ship.png");
        ship = shipIcon.getImage();

        ImageIcon asteroidIcon = new ImageIcon("asteroid.png");
        asteroid = asteroidIcon.getImage();

        ImageIcon shotIcon = new ImageIcon("shot.png");
        shot = shotIcon.getImage();

    }

    public void initGame(){
        createShip();
    }

    public void createShip(){
        shipX = this.getWidth() / 2;
        shipY = this.getHeight() / 2;

    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        g.drawImage(ship, shipX, shipY, null);
    }
}
