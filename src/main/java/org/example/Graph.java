package org.example;

import io.quickchart.QuickChart;
public class Graph {

    QuickChart chart = new QuickChart();

    public Graph(int width, int height, int sumOfToday, int sumOfYesterday, int sumOf2daysEgo){
        chart.setWidth(width);
        chart.setHeight(height);
        chart.setConfig("{"
                + "    type: 'bar',"
                + "    data: {"
                + "        labels: ['2 days ego', 'yesterday', 'today'],"
                + "        datasets: [{"
                + "            label: 'Users',"
                + "            data: [" + sumOf2daysEgo + "," + sumOfYesterday + "," + sumOfToday + "]"
                + "        }]"
                + "    }"
                + "}"
        );
    }
}
