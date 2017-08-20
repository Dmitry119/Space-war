package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import static java.awt.event.KeyEvent.*;

public class GameField extends JPanel implements ActionListener{

    private final int SPEED = 5;
    private int shipX, shipY; // polar coordinates. x = R*cosT; y = R*sinT
    private double shipT; // shipT - teta - angle
    private int asteroidX, asteroidY;
    private Image ship;
    private Image asteroid;
    private Image shot;
    private Timer timer;
    private static  ArrayList<Integer> pressedKeys = new ArrayList<>(); // all pressed buttons; for multiple pressed works

    public GameField(){
        setBackground(Color.CYAN);

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

    public void initGame(){
        createShip();
        timer = new Timer(5, this); // activate "actionPerformed" every 250 ms
        timer.start();
    }

    public void createShip(){
        shipX = 500;
        shipY = 500;
        shipT = 0;
    }

    public void moveShip(){
        // такая система (таймер + actionPerfor from implements ActionListener + ArrayList<Integer> pressedKeys
        // + вот этот метод - позволяет запоминать нажатые и не отпущенные еще клавиши,
        // тем самым обрабатывается сразу нажатие 2+ клавиш. и все пашет)
        if (isPressed(VK_W)) {
            shipY -= (int) Math.round(SPEED * Math.cos(Math.toRadians(shipT)));
            shipX += (int) Math.round(SPEED * Math.sin(Math.toRadians(shipT)));
        }
        if (isPressed(VK_S)) {

        }
        if (isPressed(VK_A)) shipT -= 2;
        if (isPressed(VK_D)) shipT += 2;

        repaint();

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


    @Override  // почему-то не хочет вызываться паинт компонент вначале...и пока он не вызывается например от ресайза - ниче не пашет!
    public void paintComponent(Graphics g) { // LOOOOOL. в нотсы 100%! было  paintComponents с буквой s на конце и оно не работало... но ошибку не давало
        super.paintComponent(g);


        Graphics2D g2d = (Graphics2D)g;



        AffineTransform at = AffineTransform.getTranslateInstance(shipX, shipY); // без new!
        at.rotate(shipT/(180/Math.PI), ship.getWidth(this) / 2, ship.getHeight(this) /2 ); // angle in radianes!
        // at.rotate(shipT) --> rotate around left top corner
        // --> add anchorx/anchory: at.rotate(shipT, ship.getWidth(this) / 2, ship.getHeight(this) /2 );
        // now rotate around center
        // for scale use at.scale
        g2d.drawImage(ship, at, this); // observer - "this", not null !!!

        g2d.drawString("angle (degree) = "+ shipT + " angle in rad = " +shipT*180/Math.PI + "x = " + shipX
                + "y = " + shipY,50,50);



    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moveShip();
        System.out.println("actionPerformed + T / x / y " + shipT + " " + shipX + " " + shipY);
    }

//    @Override // use with implements ActionListener - for timer
//    public void actionPerformed(ActionEvent e) {
//        repaint();
//    }

    class FieldKeyListener extends KeyAdapter{

    // multi touch - https://www.youtube.com/watch?v=VnogOoOQZIE

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







    // true, если кнопка с кодом keyCode нажата
    public static boolean isPressed(int keyCode) {
        return pressedKeys.contains(keyCode);
    }

}
