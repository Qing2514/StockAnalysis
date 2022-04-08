package com.example.Observer;

import com.example.Chart.DrawKlineChart;
import com.example.Subject.StockSubject;
import com.example.Subject.Subject;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.util.ArrayList;

public class KlineFigureObserver implements Observer {

    Subject subject;
    String type;

    public KlineFigureObserver(Subject subject) {
        this.subject = subject;
        type = "KlineFigure";
        subject.addObserver(this);
    }

    @Override
    public void drawFig() {
        if (subject instanceof StockSubject) {
            double[][] prices = ((StockSubject) subject).getPrices();
            int n = prices.length;
            int size = prices[0].length;
            String type = ((StockSubject) subject).getType();
            if (type.equals(this.type)) {
                //根据股票的价格变动和涨跌停情况调整每支股票的价格数组,并将每天的统计数据加入到List中；
                ArrayList<Klineobject> objects = new ArrayList<>();
                for (int i = 0; i < n; i++) {
                    double yesterdayClosePrice = 100;//前一天的收盘价；
                    double last10TotalPrice = 0;//当天最后成交的10笔交易总额；
                    double first10TotalPrice = 0;//当天前10笔成交总额；
                    double limitUpPrice = yesterdayClosePrice * 1.1;//涨停价；
                    double limitDownPrice = yesterdayClosePrice * 0.9;//跌停价；
                    double startPrice = 0;//开盘价；
                    double maxDealDay = Double.MIN_VALUE;//当天交易额最大
                    double minDealDay = Double.MAX_VALUE;//当天交易额最小
                    for (int j = 0; j < size; j++) {
                        if (j % 120 == 0 && j != 0) {
                            //到第二天最后10笔交易清空并改动涨跌价和昨天收盘价；
                            yesterdayClosePrice = last10TotalPrice / 10.0;
                            limitUpPrice = yesterdayClosePrice * 1.1;
                            limitDownPrice = yesterdayClosePrice * 0.9;
                            last10TotalPrice = 0;
                            first10TotalPrice = 0;
                        }
                        //每分钟进行的交易额需要判断是否涨停或者跌停；
                        if (prices[i][j] <= limitUpPrice && prices[i][j] >= limitDownPrice) {
                            prices[i][j] = prices[i][j];
                        } else if (prices[i][j] > limitUpPrice) {
                            prices[i][j] = limitUpPrice;
                        } else {
                            prices[i][j] = limitDownPrice;
                        }
                        //判断最高价和最低价
                        maxDealDay = Math.max(maxDealDay, prices[i][j]);
                        minDealDay = Math.min(minDealDay, prices[i][j]);
                        //得出开盘价：每天前10笔交易
                        if (j % 120 >= 0 && j % 120 <= 9) {
                            first10TotalPrice += prices[i][j];
                        }
                        //统计最后10笔交易的总额；
                        if (j % 120 >= 110 && j % 120 <= 119) {
                            last10TotalPrice += prices[i][j];
                        }
                        //统计当天的数据；
                        if (j % 120 == 119) {
                            Klineobject object = new Klineobject(first10TotalPrice / 10, last10TotalPrice / 10,
                                    maxDealDay, minDealDay);
                            objects.add(object);
                            //最大最小额重置
                            maxDealDay = Double.MIN_VALUE;
                            minDealDay = Double.MAX_VALUE;
                        }
                    }
                }
                DrawKlineChart drawkline = new DrawKlineChart(objects, size / 120);
                ChartPanel frame1 = drawkline.getChartPanel();
                JFrame frame = new JFrame("Java数据统计图");
                frame.add(frame1);
                frame.setBounds(0, 0, 800, 600);
                frame.setVisible(true);
            }
        }
    }
}