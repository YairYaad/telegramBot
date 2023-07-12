package org.example;

import javax.swing.*;
import java.awt.*;
import java.sql.Time;

public class HistoryPanel extends JPanel{

    private Id[] id;

    HistoryPanel(){

        id = new Id[10];

        for (int i = 0; i < id.length; i++) {
            id[i] = new Id(" ", new Time(System.currentTimeMillis()), " ");
        }

        this.setBackground(Color.gray);
    }

    public void paintComponent(Graphics graphics){

        super.paintComponent(graphics);

        graphics.setFont(new Font(null, Font.PLAIN, 15));

        for (int i = 0; i < id.length; i++) {

            if (id[i] == null){

                graphics.drawString(i + 1 + "  name: null, time: null", 10, (i + 1) * 30);
                graphics.drawString( "    last request: null", 10, (i + 1) * 30 + 15);
            }else {
                graphics.drawString(i + 1 + "  name: " + id[i].getName() + ", time: " + id[i].getLestRequestsTime(), 10, (i + 1) * 30);
                graphics.drawString( "    last request: " + id[i].getLestRequests(), 10, (i + 1) * 30 + 15);
            }
        }
    }

    public Id[] getId() {
        return id;
    }

    public void setId(Id[] id) {
        this.id = id;
    }
}
