package fr.mokel.trade.functions;

import fr.mokel.trade.model.DayValue;

public class MeanDeviation {

	public DayValue process(Iterable<DayValue> list, DayValue average) {
		double value = 0;
		int size = 0;
		for (DayValue dayValue : list) {
			size++;
			value += Math.abs(dayValue.getValue() - average.getValue());
		}
		DayValue res = new DayValue(0, average.getDate());
		res.setValue(value / size);
		return res;
	}
}
