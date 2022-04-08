package com.example.Chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;

import java.awt.*;

public class DrawLineChart {
    ChartPanel frame1;

    public DrawLineChart(double[][] prices) {

        XYDataset xydataset = createDataset(prices);

        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart("股票交易模拟分时图", "时间", "交易价格", xydataset, true, true,
                true);

        XYPlot xyplot = (XYPlot) jfreechart.getPlot();

        frame1 = new ChartPanel(jfreechart, true);

        DateAxis dateaxis = (DateAxis) xyplot.getDomainAxis();

        //水平底部标题
        dateaxis.setLabelFont(new Font("黑体", Font.BOLD, 14));

        //垂直标题
        dateaxis.setTickLabelFont(new Font("宋体", Font.BOLD, 12));

        //获取柱状
        ValueAxis rangeAxis = xyplot.getRangeAxis();

        rangeAxis.setLabelFont(new Font("黑体", Font.BOLD, 15));

        jfreechart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 15));

        //设置标题字体
        jfreechart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));

    }

    private XYDataset createDataset(double[][] prices) {

        TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
        int n = prices.length;
        int size = prices[0].length;

        for (int i = 0; i < n; i++) {

            String name = "股票" + (i + 1);
            TimeSeries timeseries = new TimeSeries(name);

            Day day;
            Hour hour;
            for (int j = 0; j < size; j++) {

                day = new Day(2, 3, 2022);
                if (j % 120 < 60) {
                    hour = new Hour(9, day);
                } else {
                    hour = new Hour(10, day);
                }
                timeseries.add(new Minute(j % 60, hour), prices[i][j]);
            }
            timeseriescollection.addSeries(timeseries);
        }
        return timeseriescollection;
    }

    public ChartPanel getChartPanel() {
        return frame1;
    }
}
