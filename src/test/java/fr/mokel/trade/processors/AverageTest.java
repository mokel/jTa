package fr.mokel.trade.processors;

import java.time.LocalDate;

import org.junit.Assert;

import fr.mokel.trade.functions.Average;
import fr.mokel.trade.model.DataWindow;
import fr.mokel.trade.model.DayValue;

public class AverageTest {

	@org.junit.Test
	public void test() {
		Average a = new Average();
		DayValue res = a.process(createWindow(1,2,3));
		Assert.assertEquals(2d, res.getValue(), 0.001d);
		Assert.assertEquals(LocalDate.now(), res.getDate());
	}
	@org.junit.Test
	public void test2() {
		Average a = new Average();
		DayValue res = a.process(createWindow(1,2,3,4));
		Assert.assertEquals(2.5d, res.getValue(), 0.001d);
		Assert.assertEquals(LocalDate.now(), res.getDate());
	}
	
	private DataWindow createWindow(double... values) {
		DataWindow w = new DataWindow();
		LocalDate d = LocalDate.now().minusDays(values.length-1);
		for (int i = 0; i < values.length; i++) {
			DayValue cd = new DayValue(values[i], d);
			w.add(cd);
			d = d.plusDays(1);
		}
		return w;
	}
}
