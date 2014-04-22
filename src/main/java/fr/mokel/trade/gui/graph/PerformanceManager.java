package fr.mokel.trade.gui.graph;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import org.apache.commons.lang.time.DateUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.ExtendedCategoryAxis;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LayeredBarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.util.ShapeUtilities;

import fr.mokel.trade.model.DayValue;

/**
 * Manager für Performances Graphs
 * 
 * @author vincent.mokel
 * @version $Revision: 1.1 $ $Date: 2013/11/18 08:29:58 $ $Author: mokel $
 */
public class PerformanceManager extends JPanel {

	/**	 */
	private static final long serialVersionUID = 1L;

	/** Limit für datum axis (ausblenden) */
	private static int axisLabelThreshold = 22;

	/** Limit für logarithm axis */
	private static int axisLogThreshold = 50;

	/** Datenquelle */
	private Map<String, List<DayValue>> data = new HashMap<String, List<DayValue>>();

	/** Map, die Plot speichert */
	private Map<String, CategoryPlot> mapPlot = new HashMap<String, CategoryPlot>();

	/**
	 * @return a Combined Plot Graph
	 */
	public JFreeChart createCombinedChart() {
		CombinedDomainCategoryPlot plot = new CombinedDomainCategoryPlot();
		final JFreeChart chart = new JFreeChart(plot);
		// set the background color for the chart...
		chart.setBackgroundPaint(Color.WHITE);
		return chart;
	}

	/**
	 * Fügt einen neuen Graph hinzu
	 * 
	 */
	public void addPlot(List<DayValue> chart, String key) {
	}

	/**
	 * Löscht einen Graph
	 * 
	 * @param pChart graph instanz
	 * @param pDataType Typ des Graph
	 */
	public void removePlot(JFreeChart pChart, String pDataType) {
		CombinedDomainCategoryPlot plot = (CombinedDomainCategoryPlot) pChart.getPlot();
		plot.remove(mapPlot.get(pDataType));
		mapPlot.remove(pDataType);
	}


	private void addPlot(JFreeChart pChart, String pChartType, CategoryDataset... pDs) {
		if (mapPlot.isEmpty()) {
			createAxisX(pChart, pDs[0]);
		}
		switch (pChartType) {
		case WorkflowStat:
			addPlotWorkflowStat(pChart, pDs);
			break;
		case TimeAvg:
		case TimeMax:
		case TimeMin:
		case TimeSum:
			addPlotTime(pChart, pDs[0], pDs[1], pDs[2], pChartType);
			break;
		case ServerLoad:
			addPlotServerLoad(pChart, pDs[0], pChartType);
			break;
		}
	}

	private void addPlotServerLoad(JFreeChart pChart, CategoryDataset pDs, String pChartType) {
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

		CombinedDomainCategoryPlot combinedPlot = (CombinedDomainCategoryPlot) pChart.getPlot();
		combinedPlot.add(plot);
		mapPlot.put(String.ServerLoad, plot);

	}

	private void addPlotWorkflowStat(JFreeChart pChart, CategoryDataset... pDs) {
		final CategoryPlot plot = new CategoryPlot();
		plot.setDataset(pDs[0]);
		if (needLogAxis((DefaultCategoryDataset) pDs[0])) {
			plot.setRangeAxis(new LogarithmicAxis("Nb Workflows"));
		} else {
			plot.setRangeAxis(new NumberAxis("Nb Workflows"));
		}

		final LineAndShapeRenderer renderer0 = new LineAndShapeRenderer();
		renderer0.setSeriesPaint(0, Color.BLUE);
		renderer0.setSeriesShape(0, ShapeUtilities.createRegularCross(4, 1));
		renderer0.setSeriesPaint(1, Color.DARK_GRAY);
		renderer0.setSeriesShape(1, ShapeUtilities.createUpTriangle(3));
		renderer0.setSeriesPaint(2, Color.RED);
		renderer0.setSeriesShape(2, ShapeUtilities.createDownTriangle(3));
		renderer0.setBaseShapesFilled(false);
		plot.setRenderer(renderer0);

		plot.setDataset(1, pDs[1]);
		plot.setRangeAxis(1, new NumberAxis("Time (s)"));
		plot.mapDatasetToRangeAxis(1, 1);

		final LayeredBarRenderer renderer1 = new LayeredBarRenderer();
		renderer1.setSeriesPaint(0, Color.GRAY);
		renderer1.setSeriesPaint(1, Color.GREEN);
		renderer1.setSeriesBarWidth(0, 1);
		renderer1.setSeriesBarWidth(1, 0.3);
		plot.setRenderer(1, renderer1);

		CombinedDomainCategoryPlot combinedPlot = (CombinedDomainCategoryPlot) pChart.getPlot();
		combinedPlot.add(plot);
		mapPlot.put(String.WorkflowStat, plot);
	}

	private void addPlotTime(JFreeChart pChart,
	                         CategoryDataset pDatasetTime,
	                         CategoryDataset pDatasetLoad,
	                         CategoryDataset pDatasetPerfIndex,
	                         String type) {
		final CategoryPlot plot = new CategoryPlot();
		plot.setDataset(pDatasetTime);
		plot.setRangeAxis(new NumberAxis(type + " (s)"));
		LayeredBarRenderer render = new LayeredBarRenderer();
		plot.setRenderer(render);
		final GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, Color.RED, 0.0f, 0.0f, Color.lightGray);
		final GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, Color.ORANGE, 0.0f, 0.0f, Color.lightGray);
		render.setSeriesPaint(0, gp1);
		render.setSeriesPaint(1, gp2);
		render.setSeriesBarWidth(1, 0.3);

		plot.setOrientation(PlotOrientation.VERTICAL);
		plot.setRangeGridlinesVisible(true);
		plot.setDomainGridlinesVisible(true);

		plot.setDataset(1, pDatasetLoad);
		final LineAndShapeRenderer renderer2 = new LineAndShapeRenderer();
		renderer2.setSeriesPaint(0, Color.BLACK);
		renderer2.setBaseShapesFilled(false);
		renderer2.setSeriesShape(0, ShapeUtilities.createRegularCross(4, 1));
		plot.setRenderer(1, renderer2);

		final ValueAxis rangeAxis2 = new NumberAxis("Workload");
		plot.setRangeAxis(1, rangeAxis2);
		plot.mapDatasetToRangeAxis(1, 1);

		plot.setDataset(2, pDatasetPerfIndex);
		final LineAndShapeRenderer renderer3 = new LineAndShapeRenderer();
		renderer3.setBaseShapesVisible(false);
		renderer3.setSeriesShape(0, ShapeUtilities.createDownTriangle(1f));
		renderer3.setSeriesPaint(0, Color.GREEN);
		renderer3.setSeriesShape(1, ShapeUtilities.createDownTriangle(1f));
		renderer3.setSeriesPaint(1, Color.BLUE);
		plot.setRenderer(2, renderer3);

		final ValueAxis rangeAxis3 = new NumberAxis("Performance Index");
		plot.setRangeAxis(2, rangeAxis3);
		plot.mapDatasetToRangeAxis(2, 2);

		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
		CombinedDomainCategoryPlot combinedPlot = (CombinedDomainCategoryPlot) pChart.getPlot();
		combinedPlot.add(plot);
		mapPlot.put(type, plot);
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
				axis.addSubLabel(pDataset.getColumnKey(i), DateUtils.getShortFormat((Date) pDataset.getColumnKey(i)));
			}
		}
		combinedPlot.setDomainAxis(axis);
	}

}
