package fr.mokel.trade.functions;

import java.util.List;

import fr.mokel.trade.model.DayValue;

public class MeanDeviation {

	public DayValue process(List<DayValue> prices, DayValue average, int begin, int length) {
		double value = 0;
		for (int i = 0; i < length; i++) {
			DayValue dayValue = prices.get(begin + i);
			value += Math.abs(dayValue.getValue() - average.getValue());
		}
		DayValue res = new DayValue(0, average.getDate());
		res.setValue(value / length);
		return res;
	}
}
