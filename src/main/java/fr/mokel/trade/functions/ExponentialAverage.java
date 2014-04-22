package fr.mokel.trade.functions;

import fr.mokel.trade.model.DataWindow;
import fr.mokel.trade.model.DayValue;

public class ExponentialAverage {

	public ExponentialAverage() {
	}
	
	public DayValue process(DataWindow data) {
		double value = 0;
		for (DayValue dayValue : data) {
			value +=dayValue.getValue();
		}
		DayValue res = new DayValue(0,data.getLastDate());
		res.setValue(value / data.size());
		return res;
	}
	
	private double calcul(double spot, double previous, int length) {
		double alpha = (2d/(length+1));
		double value = spot*alpha + (1-alpha)*previous;
		return value;
	}

}
