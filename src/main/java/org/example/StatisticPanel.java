package org.example;

import javax.swing.*;
import java.awt.*;

public class StatisticPanel extends JPanel {

    private int sumOfRequest;
    private int sumOfUsers;
    private String theMostActiveUser;
    private String mostPopularRequest;

    StatisticPanel(){

    }

    public void paintComponent(Graphics graphics){

        super.paintComponent(graphics);

        graphics.setFont(new Font(null, Font.PLAIN, 15));

        graphics.drawString("sum of request: " + sumOfRequest + "", 10, 30);
        graphics.drawString("sum of user: " + sumOfUsers + "", 10, 50);
        graphics.drawString("most active user: " + theMostActiveUser + "", 10, 70);
        graphics.drawString("most popular request:", 10, 90);
        graphics.drawString(mostPopularRequest + "", 10, 110);

        this.setBackground(Color.gray);
        this.setVisible(true);

        repaint();
    }

    public int getSumOfRequest() {
        return sumOfRequest;
    }

    public void setSumOfRequest(int sumOfRequest) {
        this.sumOfRequest = sumOfRequest;
    }

    public int getSumOfUsers() {
        return sumOfUsers;
    }

    public void setSumOfUsers(int sumOfUsers) {
        this.sumOfUsers = sumOfUsers;
    }

    public String getTheMostActiveUser() {
        return theMostActiveUser;
    }

    public void setTheMostActiveUser(String theMostActiveUser) {
        this.theMostActiveUser = theMostActiveUser;
    }

    public String getMostPopularRequest() {
        return mostPopularRequest;
    }

    public void setMostPopularRequest(String mostPopularRequest) {
        this.mostPopularRequest = mostPopularRequest;
    }
}
