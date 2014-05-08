package fr.mokel.trade.indicator2;

import fr.mokel.trade.indicator2.CciIndicator2.CciIndicatorParams;
import fr.mokel.trade.indicator2.CrossMovingAverageIndicator.CrossMovingAverageIndicatorParams;
import fr.mokel.trade.indicator2.MovingAverageIndicator.MovingAverageIndicatorParams;

public enum IndicatorType {

	// sformatter:off
	SMA("Moving Avg", MovingAverageIndicatorParams.class), CMA("Cross Moving Avg",
			CrossMovingAverageIndicatorParams.class),
	CCI("CCI", CciIndicatorParams.class);
	// @formatter:on
	
	private IndicatorType(String label, Class<?> clazz) {
		this.label = label;
		this.clazz = clazz;
	}

	private String label;
	private Class<?> clazz;

	public String getLabel() {
		return label;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public IndicatorChart getIndicator() {
		return clazz.newInstance();
	}

	@Override
	public String toString() {
		return label;
	}
}
