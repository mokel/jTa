package fr.mokel.trade.indicator2;

import fr.mokel.trade.indicator2.CciIndicator2.CciIndicatorParams;
import fr.mokel.trade.indicator2.CrossMovingAverageIndicator.CrossMovingAverageIndicatorParams;
import fr.mokel.trade.indicator2.MovingAverageIndicator.MovingAverageIndicatorParams;

public enum IndicatorType {

	// sformatter:off
	SMA("Moving Avg", MovingAverageIndicatorParams.class, true, false), CMA("Cross Moving Avg",
			CrossMovingAverageIndicatorParams.class, false, false),
	CCI("CCI", CciIndicatorParams.class, false, false);
	// @formatter:on
	
	private boolean onStockChart;

	private IndicatorType(String label, Class<?> clazz, boolean onStockChart, boolean needRangeAxis) {
		this.onStockChart = onStockChart;
		this.needRangeAxis = needRangeAxis;
		this.label = label;
		this.clazz = clazz;
	}

	private boolean needRangeAxis;
	private String label;
	private Class<?> clazz;

	public String getLabel() {
		return label;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public boolean isOnStockChart() {
		return onStockChart;
	}

	public boolean isNeedRangeAxis() {
		return needRangeAxis;
	}

	@Override
	public String toString() {
		return label;
	}
}
