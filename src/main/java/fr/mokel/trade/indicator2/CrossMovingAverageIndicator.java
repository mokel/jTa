package fr.mokel.trade.indicator2;

import fr.mokel.trade.functions.Average;
import fr.mokel.trade.gui2.util.SwingField;
import fr.mokel.trade.model.DayValue;
import fr.mokel.trade.model.WindowedList;

public class CrossMovingAverageIndicator {

	
	public DayValue process(WindowedList list, int averageSmall, int averageLarge) {
		if (averageLarge > list.size()) {
			throw new IllegalArgumentException("averageLarge > window.size()");
		}		if (averageSmall > averageLarge) {
			throw new IllegalArgumentException("averageSmall > averageLarge");
		}
		Average function = new Average();
		DayValue smallAvg = function.process(list.subWindowFrom(list.size()-averageSmall));
		DayValue largeAvg = function.process(list);
		double value = smallAvg.getValue() - largeAvg.getValue();
		DayValue dv = new DayValue(value, largeAvg.getDate());
		return dv;
	}

	public static class CrossMovingAverageIndicatorParams {
		@SwingField(order = 1)
		int averageSmall;
		@SwingField(order = 2)
		int averageLarge;

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
