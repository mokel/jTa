package fr.mokel.trade.gui.graph;

import java.util.ArrayList;
import java.util.List;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import fr.mokel.trade.gui.graph.PerformanceManager.ChartEnum;
import fr.mokel.trade.model.DayValue;

/**
 * Erzeugt verschiedene CategoryDataset
 * 
 * @author vincent.mokel
 * @version $Revision: 1.1 $ $Date: 2013/11/18 08:29:58 $ $Author: mokel $
 */
public class DatasetManager {

	/** Datenquelle */
	private List<DayValue> internDs;

	/**
	 * @param pDs neuer Wert für Property internDs
	 */
	public void setDatasource(List<DayValue> pDs) {
		internDs = new ArrayList<DayValue>(pDs);
	}

	/**
	 * Create Time Dataset
	 * 
	 * @param pDataType Aggregation Type: Avg, max...
	 * @return Dataset
	 */
	public CategoryDataset getDatasetTime(ChartType pDataType) {
		DefaultCategoryDataset ds = new DefaultCategoryDataset();
		if (internDs != null) {
			for (DayValue data : internDs) {
				double time = 0;
				double extTime = 0;
				switch (pDataType) {
				case TimeAvg:
					time = data.getTimeAvg();
					extTime = data.getExtTimeAvg();
					break;
				case TimeMax:
					time = data.getTimeMax();
					extTime = data.getExtTimeMax();
					break;
				case TimeMin:
					time = data.getTimeMin();
					extTime = data.getExtTimeMin();
					break;
				case TimeSum:
					time = data.getTimeSum();
					extTime = data.getExtTimeSum();
					break;
				default:
					break;
				}
				ds.addValue(time, ChartEnum.WfTime, data.getDate());
				ds.addValue(extTime, ChartEnum.ExternTime, data.getDate());
			}
		}
		return ds;
	}

	/**
	 * Create Performance Index Dataset
	 * <p>
	 * Performance ist 'Workload / Time'
	 * 
	 * @param pDataType Aggregation Type: Avg, max...
	 * @return Dataset
	 */
	public CategoryDataset getDatasetPerformanceIndex(ChartType pDataType) {
		DefaultCategoryDataset ds = new DefaultCategoryDataset();
		if (internDs != null) {
			for (DayValue data : internDs) {
				double time = 0;
				double extTime = 0;
				double workload = 0;
				switch (pDataType) {
				case TimeAvg:
					time = data.getTimeAvg();
					extTime = data.getExtTimeAvg();
					workload = data.getWorkloadAvg();
					break;
				case TimeMax:
					time = data.getTimeMax();
					extTime = data.getExtTimeMax();
					workload = data.getWorkloadMax();
					break;
				case TimeMin:
					time = data.getTimeMin();
					extTime = data.getExtTimeMin();
					workload = data.getWorkloadMin();
					break;
				case TimeSum:
					time = data.getTimeSum();
					extTime = data.getExtTimeSum();
					workload = data.getWorkloadSum();
					break;
				default:
					break;
				}

				double perf = 0;
				if (time != 0) {
					perf = workload / time;
				}
				ds.addValue(perf, ChartEnum.PerfIndexWf, data.getDate());

				double perfExt = 0;
				if (extTime != 0) {
					perfExt = workload / extTime;
				}
				ds.addValue(perfExt, ChartEnum.PerfIndexExt, data.getDate());
			}
		}
		return ds;
	}

	/**
	 * Create Wkf stat Dataset
	 * 
	 * @return Dataset
	 */
	public DefaultCategoryDataset getDatasetWorkflowStat() {
		DefaultCategoryDataset ds = new DefaultCategoryDataset();
		if (internDs != null) {
			for (DayValue data : internDs) {
				ds.addValue(data.getNbWorkflow(), ChartEnum.NbWorkflow, data.getDate());
				ds.addValue(data.getNbEmptyWorkflow(), ChartEnum.NbEmptyWorkflow, data.getDate());
				ds.addValue(data.getNbErrorWorkflow(), ChartEnum.NbErrorWorkflow, data.getDate());
			}
		}
		return ds;
	}

	/**
	 * Create Time with no work Dataset
	 * 
	 * @return Dataset
	 */
	public DefaultCategoryDataset getDatasetTimeNoWork() {
		DefaultCategoryDataset ds = new DefaultCategoryDataset();
		if (internDs != null) {
			for (DayValue data : internDs) {
				ds.addValue(data.getTimeNoWork(), ChartEnum.TimeNoWork, data.getDate());
				ds.addValue(data.getExtTimeNoWork(), ChartEnum.ExtTimeNoWork, data.getDate());
			}
		}
		return ds;
	}

	/**
	 * Create Workload Dataset
	 * 
	 * @param dataType Aggregation Type: Avg, max...
	 * @return Dataset
	 */
	public DefaultCategoryDataset getDatasetWorkload(ChartType dataType) {
		DefaultCategoryDataset ds = new DefaultCategoryDataset();
		if (internDs != null) {
			for (DayValue data : internDs) {
				double load = 0;
				switch (dataType) {
				case TimeAvg:
					load = data.getWorkloadAvg();
					break;
				case TimeMax:
					load = data.getWorkloadMax();
					break;
				case TimeMin:
					load = data.getWorkloadMin();
					break;
				case TimeSum:
					load = data.getWorkloadSum();
					break;
				default:
					break;
				}
				ds.addValue(load, ChartEnum.WorkLoad, data.getDate());
			}
		}
		return ds;
	}

	/**
	 * Create ServerLoad Dataset
	 * 
	 * @return Dataset
	 */
	public CategoryDataset getDatasetServerLoad() {
		DefaultCategoryDataset ds = new DefaultCategoryDataset();
		if (internDs != null) {
			for (DayValue data : internDs) {
				ds.addValue(data.getNbRunWfAvg(), "NbRunWfAvg", data.getDate());
				ds.addValue(data.getNbRunWfMax(), "NbRunWfMax", data.getDate());
				ds.addValue(data.getNbRunWfSd(), "NbRunWfSd", data.getDate());

				ds.addValue(data.getNbRunScdAvg(), "NbRunScdAvg", data.getDate());
				ds.addValue(data.getNbRunScdMax(), "NbRunScdMax", data.getDate());
				ds.addValue(data.getNbRunScdSd(), "NbRunScdSd", data.getDate());
			}
		}
		return ds;
	}
}
