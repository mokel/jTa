package fr.mokel.trade.indicator2;

import fr.mokel.trade.functions.Average;
import fr.mokel.trade.gui2.util.SwingField;
import fr.mokel.trade.model.DataWindow;
import fr.mokel.trade.model.DayValue;
import fr.mokel.trade.model.WindowedList;

public class MovingAverageIndicator implements Parameterized {

	private DataWindow data;

	public void process(WindowedList list, int averageSize) {
		if (averageSize > list.size()) {
			throw new IllegalArgumentException("averageSize < window.size()");
		}
		data = new DataWindow();
		Average avg = new Average();
		for (int i = averageSize; i <= list.size(); i++) {
			DayValue res = avg.process(list.subWindow(i - averageSize, i));
			data.add(res);
		}
	}

	public DataWindow getData() {
		return data;
	}

	public void setData(DataWindow data) {
		this.data = data;
	}

	public static class MovingAverageIndicatorParams {
		@SwingField(order = 1)
		private int averageSize;

		public int getAverageSize() {
			return averageSize;
		}

		public void setAverageSize(int averageSize) {
			this.averageSize = averageSize;
		}

	}
}
