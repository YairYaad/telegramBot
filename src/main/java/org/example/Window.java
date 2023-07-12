package org.example;

import javax.swing.*;

public class Window extends JFrame {

    Panel panel;
    Window(){

        panel = new Panel();

        this.add(panel);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(600, 600);
        this.add(panel);
        this.setVisible(true);

    }
}
