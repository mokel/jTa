package fr.mokel.trade.functions;

import java.util.List;

import fr.mokel.trade.model.DayValue;

public class Average {

	public DayValue process(List<DayValue> prices, int begin, int length) {
		double value = 0;
		for (int i = 0; i < length; i++) {
			value += prices.get(begin + i).getValue();
		}
		DayValue res = new DayValue(0, prices.get(begin + length - 1).getDate());
		res.setValue(value / length);
		return res;
	}

}
