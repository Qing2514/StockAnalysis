package com.example;

import com.example.Observer.KlineFigureObserver;
import com.example.Observer.RealTimeFigureObserver;
import com.example.Observer.TimeFigureObserver;
import com.example.Subject.StockSubject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class StockAnalysis extends JFrame implements ActionListener {

    final int SIZE1 = 120;
    // SIZE2/120 为天数
    final int SIZE2 = 1200;
    final int N1 = 2;
    final int N2 = 1;

    JButton TimeFigureBtn = new JButton("查看分时图");
    JButton RealTimeFigureBtn = new JButton("查看实时分时图");
    JButton KlineFigureBtn = new JButton("查看K线图");

    public StockAnalysis(String title) {
        super(title);//随机产生4支股票并随机产生1200个随机数来模拟10天股票价格变化，每天交易2小时；

        StockSubject stockSubject = new StockSubject();
        TimeFigureObserver timeFigure = new TimeFigureObserver(stockSubject);
        RealTimeFigureObserver realTimeFigure = new RealTimeFigureObserver(stockSubject);
        KlineFigureObserver klineFigure = new KlineFigureObserver(stockSubject);

        Container c = getContentPane();
        c.setLayout(new FlowLayout(FlowLayout.CENTER));

        TimeFigureBtn.setPreferredSize(new Dimension(100, 40));
        RealTimeFigureBtn.setPreferredSize(new Dimension(150, 40));
        KlineFigureBtn.setPreferredSize(new Dimension(100, 40));

        c.add(TimeFigureBtn);
        c.add(RealTimeFigureBtn);
        c.add(KlineFigureBtn);

        TimeFigureBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                stockSubject.setPriceAndType(getPrice(SIZE1, N1), "TimeFigure");
                stockSubject.notifyObservers();
            }
        });

        RealTimeFigureBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                stockSubject.setPriceAndType(getPrice(SIZE1, N1), "RealTimeFigure");
                stockSubject.notifyObservers();
            }
        });

        KlineFigureBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stockSubject.setPriceAndType(getPrice(SIZE2, N2), "KlineFigure");
                stockSubject.notifyObservers();
            }
        });

        setSize(700, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public double[][] getPrice(int size, int n) {

        double[][] price = new double[n][size];
        Random ran = new Random();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < size; j++) {
                price[i][j] = j == 0 ? ran.nextDouble() * 20 - 10 + 100 : ran.nextDouble() * 1 - 0.5 + price[i][j - 1];
            }
        }
        return price;
    }

    public static void main(String[] args) {
        StockAnalysis application = new StockAnalysis("分时图&实时分时图&K线图");
        application.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

