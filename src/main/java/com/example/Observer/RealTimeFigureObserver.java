package com.example.Observer;

import com.example.Subject.StockSubject;
import com.example.Subject.Subject;
import org.jfree.chart.ChartFactory;

import org.jfree.chart.ChartPanel;

import org.jfree.chart.JFreeChart;

import org.jfree.chart.axis.ValueAxis;

import org.jfree.chart.plot.XYPlot;

import org.jfree.data.time.Millisecond;

import org.jfree.data.time.TimeSeries;

import org.jfree.data.time.TimeSeriesCollection;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

public class RealTimeFigureObserver extends ChartPanel implements Observer, Runnable, ActionListener {

    private static final long serialVersionUID = 1L;
    static JLabel show;//显示随机数
    private static TimeSeries timeSeries;
    Subject subject;
    String type;

    public RealTimeFigureObserver(Subject subject) {
        super(createChart("Stock", "RealTimeFigure", "Price"));
        this.subject = subject;
        this.type = "RealTimeFigure";
        subject.addObserver(this);
    }

    public RealTimeFigureObserver(String chartContent, String title, String yaxisName) {
        //构造函数，第一个参数为图表内容，第二个参数为图表标题,第三个参数为纵坐标标题
        super(createChart(chartContent, title, yaxisName)); //调用超类的方法createChart
    }

    private static JFreeChart createChart(String chartContent, String title, String yaxisName) {

        //创建时序图对象
        timeSeries = new TimeSeries(chartContent, Millisecond.class);
        TimeSeriesCollection timeseriescollection = new TimeSeriesCollection(timeSeries);
        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(title, "Time", yaxisName, timeseriescollection,
                true, true, false);
        XYPlot xyplot = jfreechart.getXYPlot(); //设定坐标系
        //横坐标设定
        ValueAxis valueaxis = xyplot.getDomainAxis();
        //横坐标自动设置数据轴数据范围
        valueaxis.setAutoRange(true);
        //横坐标固定数据范围 30s
        valueaxis.setFixedAutoRange(30000D);
        //纵坐标设定
        valueaxis = xyplot.getRangeAxis();
        return jfreechart;
    }

    @Override
    public void run() {
        while (true) {
            try {
                //第一个参数(当前时间)为横坐标的值
                timeSeries.add(new Millisecond(), randomNum());
                //每隔一秒产生一个随机数
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            } //异常处理
        }
    }

    double yesterdayClosePrice = 100;
    double limitUpPrice = yesterdayClosePrice * 1.1;
    double limitDownPrice = yesterdayClosePrice * 0.9;

    private double randomNum() //产生随机数
    {
        double price = Math.random() * 20 + 90;
        if(price < limitDownPrice){
            price = limitDownPrice;
        }
        else if(price > limitUpPrice){
            price = limitUpPrice;
        }

        yesterdayClosePrice = price;
        limitUpPrice = yesterdayClosePrice * 1.1;
        limitDownPrice = yesterdayClosePrice * 0.9;

        show.setText("随机数：" + price);
        return price; //返回随机数
    }

    @Override
    public void drawFig() {
        if (subject instanceof StockSubject) {
            String type = ((StockSubject) subject).getType();
            if (type.equals(this.type)) {
                JFrame frame = new JFrame("实时分时图");
                RealTimeFigureObserver rtcp = new RealTimeFigureObserver("Stock", "RealTimeFigure", "Price");
                frame.getContentPane().add(rtcp, BorderLayout.CENTER);
                show = new JLabel();
                frame.getContentPane().add(show, BorderLayout.SOUTH);
                frame.pack();//窗口大小可随意变化
                frame.setVisible(true);
                (new Thread(rtcp)).start(); //启动线程，并自动执行run()函数
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
}
