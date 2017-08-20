package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameField extends JPanel implements ActionListener {

    private final int SPEED = 5;
    private int shipX, shipY;
    private int asteroidX, asteroidY;
    private Image ship;
    private Image asteroid;
    private Image shot;


    public GameField(){
        setBackground(Color.black);
        loadImages();
        createShip();
        addKeyListener(new FieldKeyListener());
        setFocusable(true); // for key listener
    }

    public void loadImages(){

        ImageIcon shipIcon = new ImageIcon("ship.png");
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
        shipX = 500;
        shipY = 500;
    }



    @Override  // почему-то не хочет вызываться паинт компонент вначале...и пока он не вызывается например от ресайза - ниче не пашет!
    public void paintComponent(Graphics g) { // LOOOOOL. в нотсы 100%! было  paintComponents с буквой s на конце и оно не работало... но ошибку не давало
        super.paintComponent(g);
        g.drawImage(ship,shipX, shipY, this); // observer - "this", not null !!!
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        int id = e.getID();

        if (id == KeyEvent.KEY_TYPED){
            shipY += 50;
        }

        repaint();
    }

    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_W) shipY -= SPEED;

            repaint();
        }

    }

}
