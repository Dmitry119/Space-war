package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import static java.awt.event.KeyEvent.*;

public class GameField extends JPanel implements ActionListener{

    private final int SPEED = 5;
    private int shipX, shipY;
    private double shipT; // shipT, "teta" - angle
    private int asteroidX, asteroidY;
    private Image ship;
    private Image asteroid;
    private Image shot;
    private Timer timer;
    private static  ArrayList<Integer> pressedKeys = new ArrayList<>(); // all pressed buttons; for works
    // with 2+ pressed buttons at same time

    public GameField(){
        setBackground(Color.black);
        loadImages();
        createShip();
        addKeyListener(new FieldKeyListener());
        initGame();
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

    public void createShip(){ // draw ship at start position
        shipX = 500;
        shipY = 500;
        shipT = 0;
    }

    public void initGame(){
        createShip();
        timer = new Timer(5, this); // timer will be activate "actionPerformed" every 5 ms
        timer.start();
    }

    public void moveShip(){
        // такая система: (таймер + actionPerformed (from "implements ActionListener")
        // + ArrayList<Integer> pressedKeys
        // + вот этот метод - позволяет запоминать нажатые и не отпущенные еще клавиши,
        // тем самым обрабатывается сразу нажатие 2+ клавиш. и все пашет)
        if (isPressed(VK_W)) { // calculate in polar coordinates
            shipX += (int) Math.round(SPEED * Math.sin(Math.toRadians(shipT)));
            shipY -= (int) Math.round(SPEED * Math.cos(Math.toRadians(shipT)));
        }
        if (isPressed(VK_S)) { // calculate in polar coordinates
            shipX -= (int) Math.round(SPEED * Math.sin(Math.toRadians(shipT)));
            shipY += (int) Math.round(SPEED * Math.cos(Math.toRadians(shipT)));
        }
        if (isPressed(VK_A)) shipT -= 2;
        if (isPressed(VK_D)) shipT += 2;

        repaint(); // !!!

    }




    @Override
    public void paintComponent(Graphics g) { // SAVE NOTE: с буквой s (paintComponentS) не работает, но ошибку не показывает!
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D)g;

        AffineTransform at = AffineTransform.getTranslateInstance(shipX, shipY); // без new!
        at.rotate(shipT/(180/Math.PI), ship.getWidth(this) / 2, ship.getHeight(this) /2 ); // angle in radianes!
        // at.rotate --> rotate around LEFT TOP CORNER
        // add anchorx/anchory: at.rotate(..., ship.getWidth(this) / 2, ship.getHeight(this) /2 ) -->
        // --> rotate around center

        g2d.drawImage(ship, at, this); // observer - "this", not null !!!

    }

    @Override
    public void actionPerformed(ActionEvent e) { // activates by timer every X ms
        moveShip(); // look pressed keys and move in this direction
    }


    class FieldKeyListener extends KeyAdapter{
    // save pressed keys for work with 2+ pressed buttons via moveShip();

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            press(e.getKeyCode());
            System.out.println(e.getKeyCode());
        }

        @Override
        public void keyReleased(KeyEvent e) {
            super.keyPressed(e);
            reset(e.getKeyCode());

        }

    }

    // вызвать, как только была нажата кнопка с кодом keyCode
    public static void press(int keyCode) {
        if (!isPressed(keyCode)) {
            pressedKeys.add(keyCode);
        }
    }

    // вызвать, как только была отпущена кнопка с кодом keyCode
    public static void reset(int keyCode) {
        if (isPressed(keyCode)) {
            pressedKeys.remove(pressedKeys.indexOf(keyCode));
        }
    }

    // true, если кнопка с кодом keyCode нажата
    public static boolean isPressed(int keyCode) {
        return pressedKeys.contains(keyCode);
    }

}
