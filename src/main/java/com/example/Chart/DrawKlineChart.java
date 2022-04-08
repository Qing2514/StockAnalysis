package com.example.Chart;


import com.example.Observer.Klineobject;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;
import org.jfree.data.xy.OHLCDataset;

import java.awt.*;
import java.util.ArrayList;

public class DrawKlineChart {
    ChartPanel frame1;

    public void showObject(ArrayList<Klineobject> object, int dayCount) {
        for (int i = 0; i < object.size(); i++) {
            System.out.println("第" + i / dayCount + "支股票第 " + i % dayCount + " 天: " + " 开盘价： " + object.get(i).getStartPrice() + " 收盘价：" + object.get(i).getClosePrice() + " 最高价：" + object.get(i).getMaxPrice() + " 最低价：" + object.get(i).getMinPrice());
        }
    }

    public DrawKlineChart(ArrayList<Klineobject> object, int dayCount) {

        //将数据集进行输入
        showObject(object, dayCount);

        //用得到的数据集开始画图
        OHLCDataset xydataset = createDataset(object, dayCount);

        //设置K线图的画图器
        CandlestickRenderer candlestickRender = new CandlestickRenderer();

        //设置是否使用自定义的边框线，程序自带的边框线的颜色不符合中国股票市场的习惯
        candlestickRender.setUseOutlinePaint(true);

        //设置如何对K线图的宽度进行设定
        candlestickRender.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_AVERAGE);

        //设置各个K线图之间的间隔
        candlestickRender.setAutoWidthGap(0.001);

        //设置股票上涨的K线图颜色
        candlestickRender.setUpPaint(Color.RED);

        //设置股票下跌的K线图颜色
        candlestickRender.setDownPaint(Color.GREEN);

        DateAxis x1Axis = new DateAxis("时间");

        NumberAxis y1Axis = new NumberAxis("交易价格");

        XYPlot plot1 = new XYPlot(xydataset, x1Axis, y1Axis, candlestickRender);

        JFreeChart jfreechart = new JFreeChart("股票交易模拟K线图", plot1);

        XYPlot xyplot = (XYPlot) jfreechart.getPlot();

        //设置图表的字体格式
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

    private OHLCDataset createDataset(ArrayList<Klineobject> object, int dayCount) {

        OHLCSeriesCollection seriescollection = new OHLCSeriesCollection();
        OHLCSeries series = new OHLCSeries("");
        for (int i = 0; i < object.size(); i++) {
            if (i % dayCount == 0) {
                int num = i / dayCount + 1;
                String name = "第" + num + "支股票";
                series = new OHLCSeries(name);
            }
            Day day = new Day(i % dayCount + 16, 11, 2021);
            //对应于开、高、低、收
            series.add(day, object.get(i).getStartPrice(), object.get(i).getMaxPrice(), object.get(i).getMinPrice(),
                    object.get(i).getClosePrice());
            //对同一支股票计算到最后一天然后加入collection中
            if (i % dayCount == dayCount - 1) {
                seriescollection.addSeries(series);
            }
        }
        return seriescollection;
    }

    public ChartPanel getChartPanel() {
        return frame1;
    }
}
