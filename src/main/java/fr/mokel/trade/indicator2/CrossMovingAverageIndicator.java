package fr.mokel.trade.indicator2;

import java.util.List;

import fr.mokel.trade.functions.Average;
import fr.mokel.trade.gui2.util.SwingField;
import fr.mokel.trade.model.DayValue;

public class CrossMovingAverageIndicator implements Indicator {

	@Override
	public DayValue process(List<DayValue> prices, IndicatorParameters params) {
		CrossMovingAverageIndicatorParams cmaParams = (CrossMovingAverageIndicatorParams) params;
		if (cmaParams.getAverageLarge() > prices.size()) {
			throw new IllegalArgumentException("averageLarge > window.size()");
		}		if (cmaParams.getAverageSmall() > cmaParams.getAverageLarge()) {
			throw new IllegalArgumentException("averageSmall > averageLarge");
		}
		Average function = new Average();
		DayValue smallAvg = function.process(list.subWindowFrom(list.size()-cmaParams.getAverageSmall()));
		DayValue largeAvg = function.process(list);
		double value = smallAvg.getValue() - largeAvg.getValue();
		DayValue dv = new DayValue(value, largeAvg.getDate());
		return dv;
	}

	public static class CrossMovingAverageIndicatorParams implements IndicatorParameters {
		@SwingField(order = 1)
		int averageSmall = 20;
		@SwingField(order = 2)
		int averageLarge = 50;

		public int getAverageSmall() {
			return averageSmall;
		}

		public void setAverageSmall(int averageSmall) {
			this.averageSmall = averageSmall;
		}

		public int getAverageLarge() {
			return averageLarge;
		}

		public void setAverageLarge(int averageLarge) {
			this.averageLarge = averageLarge;
		}

	}
}
