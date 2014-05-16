package fr.mokel.trade.indicator2;

import java.util.ArrayList;
import java.util.List;

import fr.mokel.trade.functions.Average;
import fr.mokel.trade.gui2.util.SwingField;
import fr.mokel.trade.model.DayValue;

public class CrossMovingAverageIndicator implements IndicatorChart {

	@Override
	public List<DayValue> process(List<DayValue> prices, IndicatorParameters params) {
		List<DayValue> data = new ArrayList<DayValue>();
		CrossMovingAverageIndicatorParams cmaParams = (CrossMovingAverageIndicatorParams) params;
		if (cmaParams.getAverageLarge() > prices.size()) {
			throw new IllegalArgumentException("averageLarge > window.size()");
		}		if (cmaParams.getAverageSmall() > cmaParams.getAverageLarge()) {
			throw new IllegalArgumentException("averageSmall > averageLarge");
		}
		for (int i = 0; i <= prices.size() - cmaParams.averageLarge; i++) {
			int sizeDiff = cmaParams.averageLarge - cmaParams.averageSmall;
			Average function = new Average();
			DayValue smallAvg = function.process(prices, i + sizeDiff, cmaParams.averageSmall);
			DayValue largeAvg = function.process(prices, i, cmaParams.averageLarge);
			// TODO REMOVE
			if (!smallAvg.getDate().equals(largeAvg.getDate())) {
				throw new RuntimeException("Kaputt");
			}
			double value = smallAvg.getValue() - largeAvg.getValue();
			DayValue dv = new DayValue(value, largeAvg.getDate());
			data.add(dv);
		}
		return data;
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

		@Override
		public IndicatorChart createIndicatorInstance() {
			return new CrossMovingAverageIndicator();
		}

	}
}
