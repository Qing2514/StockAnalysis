package com.example.Observer;

import com.example.Chart.DrawLineChart;
import com.example.Subject.StockSubject;
import com.example.Subject.Subject;
import org.jfree.chart.ChartPanel;

import javax.swing.*;

public class TimeFigureObserver implements Observer {

    Subject subject;
    String type;

    public TimeFigureObserver(Subject subject) {
        this.subject = subject;
        this.type = "TimeFigure";
        subject.addObserver(this);
    }

    @Override
    public void drawFig() {
        /* 假设第一天的前一天的收盘价为每股100元，涨跌幅为10%；
      假设共有四支股票，模拟10天内的股票分时图走向；
      假设每天股票交易时间长度为2小时,时间段为9：00~11：00，用1200个数据来模拟每只股票的价格走向；
      假设最后10笔交易的平均值作为当天的收盘价，最初10笔加一的平均值为当天的开盘价；*/

        if (subject instanceof StockSubject) {
            String type = ((StockSubject) subject).getType();
            double[][] prices = ((StockSubject) subject).getPrices();
            int n = prices.length;
            int size = prices[0].length;
            //根据股票的价格变动和涨跌停情况调整每支股票的价格数组；
            if (type.equals(this.type)) {

                double last10TotalPrice = 1000;
                double yesterdayClosePrice = last10TotalPrice / 10.0;
                double limitUpPrice = yesterdayClosePrice * 1.1;
                double limitDownPrice = yesterdayClosePrice * 0.9;

                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < size; j++) {

                        //}//每分钟进行的交易额需要判断是否涨停或者跌停；
                        if (prices[i][j] <= limitUpPrice && prices[i][j] >= limitDownPrice) {
                            prices[i][j] = prices[i][j];
                        } else if (prices[i][j] > limitUpPrice) {
                            prices[i][j] = limitUpPrice;
                        } else {
                            prices[i][j] = limitDownPrice;
                        }
                        //统计最后10笔交易的总额；
                        if (j % 120 >= 110) {
                            last10TotalPrice += prices[i][j];
                        }
                    }
                }
                //得到新的价格数组后进行绘图；
                DrawLineChart drawline = new DrawLineChart(prices);
                ChartPanel frame1 = drawline.getChartPanel();
                JFrame frame = new JFrame("Java数据统计图");
                frame.add(frame1);
                frame.setBounds(0, 0, 800, 600);
                frame.setVisible(true);
            }
        }
    }
}