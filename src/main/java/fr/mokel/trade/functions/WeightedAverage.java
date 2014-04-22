package fr.mokel.trade.functions;

import fr.mokel.trade.model.DataWindow;
import fr.mokel.trade.model.DayValue;

public class WeightedAverage {

	public WeightedAverage() {
	}

	public DayValue getValue(DataWindow data) {
		double value = 0;
		int weight = 1;
		for (DayValue dayValue : data) {
			value += dayValue.getValue() * weight;
			weight++;
		}
		DayValue res = new DayValue(0, data.getLastDate());
		int denominator = data.size() * (data.size() + 1) / 2;
		res.setValue(value / denominator);
		return res;
	}

}
