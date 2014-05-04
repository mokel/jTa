package fr.mokel.trade.indicator2;

public enum Indicator {

	// @formatter:off
	SMA("Moving Avg", MovingAverageIndicator.class),
	CMA("Cross Moving Avg",CrossMovingAverageIndicator.class);
	// @formatter:on
	
	private Indicator(String label, Class<?> clazz) {
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

}
