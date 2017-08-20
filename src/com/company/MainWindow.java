package com.company;

import javax.swing.*;

public class MainWindow extends JFrame {

    public  static  final double VERSION = 0.2;

    public MainWindow() {

        setTitle("Space war ver " + VERSION);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setLocation(500,0);
        add(new GameField()); // it extends JPanel
        setVisible(true); // ДОЛЖЕН БЫТЬ ПОСЛЕДНЕЙ СТРОЧКОЙ ! ИНАЧЕ НИЧЕГО НЕ ОТРИСУЕТСЯ
    }


    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();

    }
}
