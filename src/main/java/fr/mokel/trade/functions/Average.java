package fr.mokel.trade.functions;

import fr.mokel.trade.model.DayValue;
import fr.mokel.trade.model.WindowedList;

public class Average {

	public DayValue process(WindowedList windowedList) {
		double value = 0;
		for (DayValue dayValue : windowedList) {
			value += dayValue.getValue();
		}
		DayValue res = new DayValue(0, windowedList.getLast().getDate());
		res.setValue(value / windowedList.size());
		return res;
	}

}
