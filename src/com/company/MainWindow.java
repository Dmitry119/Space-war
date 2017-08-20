package com.company;

import javax.swing.*;

public class MainWindow extends JFrame {
    public  static  final double VERSION = 0.1;

    public MainWindow(){
        setTitle("Space war ver " + VERSION);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1024, 768);
        setVisible(true);
        add(new GameField()); // it extends JPanel.
    }

    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
    }
}
