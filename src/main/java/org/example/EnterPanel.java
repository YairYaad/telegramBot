package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnterPanel extends JPanel implements ActionListener {

    private JButton[] buttons;
    private boolean[] isOptional;
    private int sum;

    EnterPanel(){

        isOptional = new boolean[5];
        sum = 0;

        buttons = new JButton[5];

        buttons[0] = new JButton("random joke");
        buttons[0].addActionListener(this);
        buttons[1] = new JButton("cat fact");
        buttons[1].addActionListener(this);
        buttons[2] = new JButton("numbers fact");
        buttons[2].addActionListener(this);
        buttons[3] = new JButton("random quote");
        buttons[3].addActionListener(this);
        buttons[4] = new JButton("country");
        buttons[4].addActionListener(this);

        for (JButton button : buttons) {
            this.add(button);
        }
        this.setBackground(Color.gray);
        this.setVisible(true);
    }

    public void paintComponent(Graphics graphics){

        super.paintComponent(graphics);

        int buttonsX = 10;
        int buttonsY = 10;
        for (JButton button : buttons) {
            button.setBounds(buttonsX, buttonsY, 150, 30);
            buttonsY += 40;
        }

        if (sum == 3){
            for (int i = 0; i < buttons.length; i++) {
                if (!isOptional[i]){
                    buttons[i].setEnabled(false);
                }
            }
        }else {
            for (JButton button : buttons) {
                button.setEnabled(true);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for (int i = 0; i < buttons.length; i++) {
            if (e.getSource() == buttons[i]){

                if (isOptional[i]){
                    isOptional[i] = false;
                    buttons[i].setBackground(Color.white);
                    sum--;
                }else {
                    isOptional[i] = true;
                    buttons[i].setBackground(Color.green);
                    sum++;
                }
            }
        }
    }

    public JButton[] getButtons() {
        return buttons;
    }

    public void setButtons(JButton[] buttons) {
        this.buttons = buttons;
    }

    public boolean[] getIsOptional() {
        return isOptional;
    }

    public void setIsOptional(boolean[] isOptional) {
        this.isOptional = isOptional;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
