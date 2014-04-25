package fr.mokel.trade.gui.graph;

import java.awt.Color;
import java.awt.Font;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.ExtendedCategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.util.ShapeList;
import org.jfree.util.ShapeUtilities;

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

	/** Limit für datum axis (ausblenden) */
	private static int axisLabelThreshold = 22;

	/** Limit für logarithm axis */
	private static int axisLogThreshold = 50;

	/** Datenquelle */
	private Map<String, List<DayValue>> data = new HashMap<String, List<DayValue>>();

	private CategoryPlot plot;
	
	final JFreeChart chart;

	private BackTestResult results;
	
	/**
	 * @return a Combined Plot Graph
	 */
	public PerformanceChart() {
		plot = new CategoryPlot();
		chart = new JFreeChart(plot);
		// set the background color for the chart...
		chart.setBackgroundPaint(Color.WHITE);
		add(new ChartPanel(chart));
	}

//	/**
//	 * Fügt einen neuen Graph hinzu
//	 * 
//	 */
//	public void addPlot(List<DayValue> chart, String key) {
//		CategoryDataset ds = createDs(chart, key);
//		addPlot(key, ds);
//	}
//
//	private CategoryDataset createDs(List<DayValue> chart, String key) {
//		DefaultCategoryDataset ds = new DefaultCategoryDataset();
//			for (DayValue data : chart) {
//				ds.addValue(data.getValue(), key, data.getDate());
//			}
//		return ds;
//	}

//	/**
//	 * Löscht einen Graph
//	 * 
//	 * @param pChart graph instanz
//	 * @param pDataType Typ des Graph
//	 */
//	public void removePlot(JFreeChart pChart, String pDataType) {
//		CombinedDomainCategoryPlot plot = (CombinedDomainCategoryPlot) pChart.getPlot();
//		plot.remove(mapPlot.get(pDataType));
//		mapPlot.remove(pDataType);
//	}
//
//
//	private void addPlot(String key, CategoryDataset pDs) {
//		if (mapPlot.isEmpty()) {
//			createAxisX(chart, pDs);
//		}
//		addPlotServerLoad(key, pDs);
//	}

	private void addPlotServerLoad(String key, CategoryDataset pDs) {
		final CategoryPlot plot = new CategoryPlot();
		plot.setDataset(pDs);
		plot.setRangeAxis(new NumberAxis("Nb Instances Running"));

		final LineAndShapeRenderer renderer = new LineAndShapeRenderer();

		//NB WORKFLOW
		renderer.setSeriesPaint(0, Color.BLUE);
		renderer.setSeriesShape(0, ShapeUtilities.createRegularCross(4, 1));
		renderer.setSeriesPaint(1, Color.BLUE);
		renderer.setSeriesShape(1, ShapeUtilities.createUpTriangle(3));
		renderer.setSeriesPaint(2, Color.BLUE);
		renderer.setSeriesShape(2, ShapeUtilities.createDownTriangle(3));

		//NB SCD
		renderer.setSeriesPaint(3, Color.GREEN);
		renderer.setSeriesShape(3, ShapeUtilities.createRegularCross(4, 1));
		renderer.setSeriesPaint(4, Color.GREEN);
		renderer.setSeriesShape(4, ShapeUtilities.createUpTriangle(3));
		renderer.setSeriesPaint(5, Color.GREEN);
		renderer.setSeriesShape(5, ShapeUtilities.createDownTriangle(3));

		renderer.setBaseShapesFilled(false);

		plot.setRenderer(renderer);

		CombinedDomainCategoryPlot combinedPlot = (CombinedDomainCategoryPlot) chart.getPlot();
		combinedPlot.add(plot);
		//mapPlot.put(key, plot);

	}


	private static boolean needLogAxis(DefaultCategoryDataset pDs) {
		int max = 0;
		for (int i = 0; i < pDs.getRowCount(); i++) { // row ist categories
			for (int j = 0; j < pDs.getColumnCount(); j++) { // column ist Datum
				max = Math.max(max, pDs.getValue(i, j).intValue());
			}
		}
		if (max > axisLogThreshold) {
			// Log axis => add 1 to Datasoure because log(0) => Kaputt
			for (Object ob1 : pDs.getRowKeys()) {
				Comparable row = (Comparable) ob1;
				for (Object ob2 : pDs.getColumnKeys()) {
					Comparable column = (Comparable) ob2;
					pDs.setValue(Integer.valueOf(pDs.getValue(row, column).intValue() + 1), row, column);
				}
			}
			return true;
		}
		return false;
	}

	private void createAxisX(JFreeChart pChart, CategoryDataset pDataset) {
		CombinedDomainCategoryPlot combinedPlot = (CombinedDomainCategoryPlot) pChart.getPlot();
		ExtendedCategoryAxis axis = new ExtendedCategoryAxis("Workflow Date");
		Font theFont = axis.getTickLabelFont();
		axis.setTickLabelFont(new Font("Arial", Font.PLAIN, 0));
		axis.setSubLabelFont(theFont);
		axis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));
		int freq = 1;
		if (pDataset.getColumnCount() > axisLabelThreshold) {
			freq = pDataset.getColumnCount() / axisLabelThreshold + 1;
		}
		for (int i = 0; i < pDataset.getColumnCount(); i++) {
			if (i % freq == 0) {
				axis.addSubLabel(pDataset.getColumnKey(i), ((LocalDate) pDataset.getColumnKey(i)).toString());
			}
		}
		combinedPlot.setDomainAxis(axis);
	}

	public void setData(BackTestResult res) {
		results = res;
		
		
		ExtendedCategoryAxis axisDate = new ExtendedCategoryAxis("Workflow Date");
		Font theFont = axisDate.getTickLabelFont();
		axisDate.setTickLabelFont(new Font("Arial", Font.PLAIN, 0));
		axisDate.setSubLabelFont(theFont);
		axisDate.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));
//		int freq = 1;
//		int size = results.getStockValues().size();
//		if (size > axisLabelThreshold) {
//			freq = size / axisLabelThreshold + 1;
//		}
//		for (int i = 0; i < pDataset.getColumnCount(); i++) {
//			if (i % freq == 0) {
//				axisDate.addSubLabel(pDataset.getColumnKey(i), DateUtils.getShortFormat((Date) pDataset.getColumnKey(i)));
//			}
//		}
		plot.setDomainAxis(axisDate);
		
		
		
		int index = 0;
		CategoryDataset stockDs = createDs(res.getStockValues(), "Stock");
		LineAndShapeRenderer renderer = new LineAndShapeRenderer();
		renderer.setBasePaint(Color.BLUE);
		renderer.setBaseShapesVisible(false);
		NumberAxis axis = new NumberAxis("StockL");
		plot.setDataset(index, stockDs);
		plot.setRenderer(index, renderer);
		plot.setRangeAxis(index, axis);
		index++;
		
		CategoryDataset perfDs = createDs(res.getTotalPerformanceValues(), "Perf");
		renderer = new LineAndShapeRenderer();
		renderer.setBasePaint(Color.RED);
		renderer.setBaseShape(ShapeUtilities.createRegularCross(4, 1));
		axis = new NumberAxis("PerfL");
		plot.setDataset(index, perfDs);
		plot.setRenderer(index, renderer);
		plot.setRangeAxis(index, axis);
		plot.mapDatasetToRangeAxis(1, 1);
	}

	private CategoryDataset createDs(List<DayValue> stockValues, String category) {
		DefaultCategoryDataset ds = new DefaultCategoryDataset();
		for (DayValue dayValue : stockValues) {
			ds.addValue(dayValue.getValue(), category, dayValue.getJavaUtilDate());
		}
		return ds;
	}

}
