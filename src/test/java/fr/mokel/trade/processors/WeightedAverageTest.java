package fr.mokel.trade.processors;

import java.time.LocalDate;

import org.junit.Assert;

import fr.mokel.trade.functions.WeightedAverage;
import fr.mokel.trade.model.DataWindow;
import fr.mokel.trade.model.DayValue;

public class WeightedAverageTest {

	@org.junit.Test
	public void test() {
		WeightedAverage a = new WeightedAverage();
		DayValue res = a.getValue(createWindow(1, 2, 3));
		Assert.assertEquals(2.3333333d, res.getValue(), 0.00001d);
		Assert.assertEquals(LocalDate.now(), res.getDate());
	}

	@org.junit.Test
	public void test2() {
		WeightedAverage a = new WeightedAverage();
		DayValue res = a.getValue(createWindow(3, 2, 1));
		Assert.assertEquals(1.6666666d, res.getValue(), 0.00001d);
	}

	private DataWindow createWindow(double... values) {
		DataWindow w = new DataWindow();
		LocalDate d = LocalDate.now().minusDays(values.length - 1);
		for (int i = 0; i < values.length; i++) {
			DayValue cd = new DayValue(values[i], d);
			w.add(cd);
			d = d.plusDays(1);
		}
		return w;
	}

}
