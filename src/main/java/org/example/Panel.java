package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

public class Panel extends JPanel implements ActionListener {

    private EnterPanel enterPanel;
    private StatisticPanel statisticPanel;
    private HistoryPanel historyPanel;
    private JButton subButton = new JButton("active bot");
    private Bot bot;
    private boolean flag;
    private int sumOfToday = 0;
    private int sumOfYesterday = 0;
    private int sumOf2daysEgo = 0;
    private Graph graph;

    Panel() {


        historyPanel = new HistoryPanel();
        historyPanel.setVisible(false);

        statisticPanel = new StatisticPanel();
        statisticPanel.setVisible(false);

        enterPanel = new EnterPanel();

        subButton.addActionListener(this);

        this.add(subButton);
        this.add(statisticPanel);
        this.add(historyPanel);
        this.add(enterPanel);
        this.setVisible(true);

        repaint();
    }

    public void paintComponent(Graphics graphics) {

        super.paintComponent(graphics);

        historyPanel.setBounds(230, 0, 300, 320);
        statisticPanel.setBounds(10, 0, 210, 130);
        if (flag) {

            historyPanel.setId(bot.getId());

            statisticPanel.setSumOfRequest(bot.getSumOfRequests());
            statisticPanel.setMostPopularRequest(bot.getMostPopularRequest());
            statisticPanel.setSumOfUsers(bot.getSumOfUsers());
            statisticPanel.setTheMostActiveUser(bot.getMostActiveUser());

            sumOfToday = bot.getSumOfToday();
            sumOfYesterday = bot.getSumOfYesterday();
            sumOf2daysEgo = bot.getSumOf2daysEgo();

            graph = new Graph(500, 250, sumOfToday, sumOfYesterday, sumOf2daysEgo);


            try {
                URL url = new URL(graph.chart.getUrl());
                Image image = ImageIO.read(url);
                graphics.drawImage(image, 20, 320, null);
            } catch (IOException e){
            }
        }

        enterPanel.setBounds(0, 0, 600, 600);
        subButton.setBounds(250, 500, 90, 30);

        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == subButton) {
            bot = new Bot(enterPanel.getIsOptional());
            flag = true;
            TelegramBotsApi api = null;
            try {
                api = new TelegramBotsApi(DefaultBotSession.class);
                api.registerBot(bot);
            } catch (TelegramApiException ex) {
                throw new RuntimeException(ex);
            }

            for (int i = 0; i < enterPanel.getIsOptional().length; i++) {
                System.out.println(enterPanel.getIsOptional()[i]);
            }
            enterPanel.setVisible(false);
            subButton.setVisible(false);
            statisticPanel.setVisible(true);
            historyPanel.setVisible(true);
            repaint();
        }
    }
}