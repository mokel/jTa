package fr.mokel.trade.gui;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.DefaultHighLowDataset;

import fr.mokel.trade.model.BarChartData;
import fr.mokel.trade.model.Chart;

public class IndicatorChartView extends JPanel {

	private Chart stockData;
	private Chart indicatorData;
	private ChartPanel chartPanel;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public IndicatorChartView() {
		DefaultHighLowDataset dataset = createDataset();
		JFreeChart chart = createChart(dataset);
		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(400, 350));
		add(chartPanel);
		// TODO Auto-generated constructor stub
	}

	public void setModel(Chart stock, Chart indicator) {
		stockData = stock;
		indicatorData = indicator;
		DefaultHighLowDataset dsBar = IndicatorChartView
				.transformCandleDS(stockData);
		JFreeChart chart = createChart(dsBar);
		remove(chartPanel);
		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(400, 350));
		add(chartPanel);
		validate();
		repaint();
	}

	private static DefaultHighLowDataset transformCandleDS(Chart data) {
		int size = 40;
		Date[] date = new Date[size];
		double[] high = new double[size];
		double[] low = new double[size];
		double[] open = new double[size];
		double[] close = new double[size];
		double[] volume = new double[size];
		for (int i = data.size() - size; i < data.size(); i++) {
			BarChartData bar = (BarChartData) data.get(i);
			int index = i - (data.size() - size);
			System.out.println(index);
			//date[index] = new Date(bar.getDate().atStartOfDay().getMillis()); TODO
			high[index] = bar.getAdjHigh();
			low[index] = bar.getAdjLow();
			open[index] = bar.getAdjOpen();
			close[index] = bar.getAdjClose();
			volume[index] = bar.getVolume();
		}
		DefaultHighLowDataset dataSet = new DefaultHighLowDataset("", date,
				high, low, open, close, volume);
		return dataSet;
	}

	public void CandleStickChart(String titel) {
		DefaultHighLowDataset dataset = createDataset();
		JFreeChart chart = createChart(dataset);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(600, 350));
	}

	private DefaultHighLowDataset createDataset() {

		int serice = 15;

		Date[] date = new Date[serice];
		double[] high = new double[serice];
		double[] low = new double[serice];
		double[] open = new double[serice];
		double[] close = new double[serice];
		double[] volume = new double[serice];

		Calendar calendar = Calendar.getInstance();
		calendar.set(2008, 5, 1);

		for (int i = 0; i < serice; i++) {
			date[i] = createData(2008, 8, i + 1);
			high[i] = 30 + Math.round(10) + new Double(Math.random() * 20.0);
			low[i] = 30 + Math.round(10) + new Double(Math.random() * 20.0);
			open[i] = 10 + Math.round(10) + new Double(Math.random() * 20.0);
			close[i] = 10 + Math.round(10) + new Double(Math.random() * 20.0);
			volume[i] = 10.0 + new Double(Math.random() * 20.0);
		}

		DefaultHighLowDataset data = new DefaultHighLowDataset("", date, high,
				low, open, close, volume);
		return data;
	}

	private Date createData(int year, int month, int date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, date);
		return calendar.getTime();
	}

	private JFreeChart createChart(DefaultHighLowDataset dataset) {
		JFreeChart chart = ChartFactory.createCandlestickChart(null, null,
				null, dataset, false);
		return chart;
	}
}
