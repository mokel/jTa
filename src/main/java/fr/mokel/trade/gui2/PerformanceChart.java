package fr.mokel.trade.gui2;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ExtendedCategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import fr.mokel.trade.gui2.util.ConstraintsBuilder;
import fr.mokel.trade.gui2.util.EventManager;
import fr.mokel.trade.gui2.util.EventManager.Event;
import fr.mokel.trade.gui2.util.EventManager.EventType;
import fr.mokel.trade.indicator2.IndicatorChart;
import fr.mokel.trade.indicator2.IndicatorParameters;
import fr.mokel.trade.indicator2.IndicatorType;
import fr.mokel.trade.model.DayValue;
import fr.mokel.trade.strategy.Run.BackTestResult;

/**
 * Manager für Performances Graphs
 * 
 * @author vincent.mokel
 * @version $Revision: 1.1 $ $Date: 2013/11/18 08:29:58 $ $Author: mokel $
 */
public class PerformanceChart extends JPanel {

	/**	 */
	private static final long serialVersionUID = 1L;

	/** Datenquelle */
	private Map<IndicatorType, List<DayValue>> data = new HashMap<IndicatorType, List<DayValue>>();

	private XYPlot stockPlot;
	private CombinedDomainXYPlot combinedPlot;

	final JFreeChart chart;
	ChartPanel chartPanel;
	JLabel info = new JLabel("info");


	private BackTestResult results;
	private int index = 0;

	private List<DayValue> stockData;

	/**
	 * @return a Combined Plot Graph
	 */
	public PerformanceChart() {
		setLayout(new GridBagLayout());
		combinedPlot = new CombinedDomainXYPlot();
		chart = new JFreeChart(combinedPlot);
		// set the background color for the chart...

		chart.setBackgroundPaint(Color.WHITE);
		chartPanel = new ChartPanel(chart);
		chartPanel.addChartMouseListener(new ChartMouseListener() {

			@Override
			public void chartMouseMoved(ChartMouseEvent event) {
				if (event.getEntity() instanceof XYItemEntity) {
					XYItemEntity entity = (XYItemEntity) event.getEntity();
					info.setText(MessageFormat.format("{0, date, short} : {1}",
							((TimeSeriesCollection) entity.getDataset())
									.getSeries(0).getDataItem(entity.getItem())
									.getPeriod().getEnd(),
							((TimeSeriesCollection) entity.getDataset())
									.getSeries(0).getDataItem(entity.getItem())
									.getValue()));
				}
			}

			@Override
			public void chartMouseClicked(ChartMouseEvent event) {
				// TODO Auto-generated method stub

			}
		});
		add(chartPanel, new ConstraintsBuilder().fillBoth().build());
		add(info, new ConstraintsBuilder(0, 1).anchor(GridBagConstraints.WEST).build());

		EventManager.addListener(EventType.IndicatorAdded, new EventManager.EventListener() {
			@Override
			public void eventOccured(Event e) {
				if (stockData != null) {
				Object[] args = e.getArgs();
					IndicatorType iType = (IndicatorType) args[0];
					IndicatorParameters params = (IndicatorParameters) args[1];
					addIndicator(iType, params);
				}
			}

		});
	}

	protected void addIndicator(IndicatorType iType, IndicatorParameters params) {
		XYPlot plot = new XYPlot();
		IndicatorChart indic = params.createIndicatorInstance();
		List<DayValue> indicChart = indic.process(stockData, params);
		data.put(iType, indicChart);
		TimeSeriesCollection ds = createDs(indicChart, iType.getLabel());
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setBaseShapesVisible(false);
		// renderer.setSeriesPaint(0, Colo);
		// renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator(
		// StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,
		// new SimpleDateFormat("dd-MM-yyyy"), new DecimalFormat(
		// "0,000.00")));
		NumberAxis axis = new NumberAxis(iType.getLabel());
		axis.setAutoRangeIncludesZero(false);
		plot.setDataset(0, ds);
		plot.setRenderer(0, renderer);
		plot.setRangeAxis(0, axis);
		plot.setBackgroundPaint(Color.GRAY);
		combinedPlot.add(plot);
	}

	protected void addIndicatorOnStockChart(IndicatorType iType, IndicatorParameters params) {
		IndicatorChart indic = params.createIndicatorInstance();
		List<DayValue> indicChart = indic.process(stockData, params);
		data.put(iType, indicChart);
		TimeSeriesCollection ds = createDs(indicChart, iType.getLabel());
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setBaseShapesVisible(false);
		// renderer.setSeriesPaint(0, Colo);
		// renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator(
		// StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,
		// new SimpleDateFormat("dd-MM-yyyy"), new DecimalFormat(
		// "0,000.00")));
		NumberAxis axis = new NumberAxis(iType.getLabel());
		stockPlot.setDataset(0, ds);
		stockPlot.setRenderer(0, renderer);
		stockPlot.setRangeAxis(0, axis);
		stockPlot.setBackgroundPaint(Color.GRAY);
		combinedPlot.add(stockPlot);
	}

	public void setRange(Date begin, Date end) {
		DateAxis va = (DateAxis) combinedPlot.getDomainAxis();
		va.setRange(begin, end);
	}

	public void setStock(List<DayValue> stockValues) {
		stockData = stockValues;
		stockPlot = new XYPlot();
		DateAxis axisDate = new DateAxis("Date");
		Font theFont = axisDate.getTickLabelFont();
		axisDate.setTickLabelFont(new Font("Arial", Font.PLAIN, 0));
		combinedPlot.setDomainAxis(axisDate);
		TimeSeriesCollection stockDs = createDs(stockData, "Stock");
		XYAreaRenderer renderer = new XYAreaRenderer();
		GradientPaint p = new GradientPaint(new Point(), Color.BLUE,
				new Point(), new Color(0, 137, 255, 30));
		p = renderer.getGradientTransformer().transform(p,
				renderer.getBaseShape());
		renderer.setSeriesPaint(0, p);
		renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator(
				StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,
				new SimpleDateFormat("dd-MM-yyyy"), new DecimalFormat(
						"0,000.00")));
		NumberAxis axis = new NumberAxis("Stock");
		axis.setAutoRangeIncludesZero(false);
		stockPlot.setDataset(index, stockDs);
		stockPlot.setRenderer(index, renderer);
		stockPlot.setRangeAxis(index, axis);
		stockPlot.setBackgroundPaint(Color.GRAY);
		combinedPlot.add(stockPlot, 3);
		index++;
	}

	public void setData(BackTestResult res) {
		results = res;
		ExtendedCategoryAxis axisDate = new ExtendedCategoryAxis(
				"Workflow Date");
		Font theFont = axisDate.getTickLabelFont();
		axisDate.setTickLabelFont(new Font("Arial", Font.PLAIN, 0));
		axisDate.setSubLabelFont(theFont);
		axisDate.setCategoryLabelPositions(CategoryLabelPositions
				.createUpRotationLabelPositions(Math.PI / 6.0));
		// int freq = 1;
		// int size = results.getStockValues().size();
		// if (size > axisLabelThreshold) {
		// freq = size / axisLabelThreshold + 1;
		// }
		// for (int i = 0; i < pDataset.getColumnCount(); i++) {
		// if (i % freq == 0) {
		// axisDate.addSubLabel(pDataset.getColumnKey(i),
		// DateUtils.getShortFormat((Date) pDataset.getColumnKey(i)));
		// }
		// }

		// plot.setDomainAxis(axisDate);
		// int index = 0;
		// CategoryDataset stockDs = createDs(res.getStockValues(), "Stock");
		// LineAndShapeRenderer renderer = new LineAndShapeRenderer();
		// renderer.setBasePaint(Color.BLUE);
		// renderer.setBaseShapesVisible(false);
		// NumberAxis axis = new NumberAxis("StockL");
		// plot.setDataset(index, stockDs);
		// plot.setRenderer(index, renderer);
		// plot.setRangeAxis(index, axis);
		// index++;
		//
		// CategoryDataset perfDs = createDs(res.getTotalPerformanceValues(),
		// "Perf");
		// renderer = new LineAndShapeRenderer();
		// renderer.setBasePaint(Color.RED);
		// renderer.setBaseShape(ShapeUtilities.createRegularCross(4, 1));
		// axis = new NumberAxis("PerfL");
		// plot.setDataset(index, perfDs);
		// plot.setRenderer(index, renderer);
		// plot.setRangeAxis(index, axis);
		// plot.mapDatasetToRangeAxis(1, 1);
	}

	private TimeSeriesCollection createDs(List<DayValue> stockValues,
			String category) {
		TimeSeries ds = new TimeSeries(category);
		for (DayValue dayValue : stockValues) {
			ds.add(new Day(dayValue.getJavaUtilDate()), dayValue.getValue());
		}
		return new TimeSeriesCollection(ds);
	}

}
