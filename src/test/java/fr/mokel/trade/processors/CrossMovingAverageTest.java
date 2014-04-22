package fr.mokel.trade.processors;

import java.time.LocalDate;

import org.junit.Assert;

import fr.mokel.trade.indicator2.CrossMovingAverageIndicator;
import fr.mokel.trade.model.DataWindow;
import fr.mokel.trade.model.DayValue;

public class CrossMovingAverageTest {

	@org.junit.Test
	public void test1() {
		DataWindow w = createWindow(1d,2d,3d,4d,5d,6d,7d,8d,9d,10d);
		CrossMovingAverageIndicator ind = new CrossMovingAverageIndicator();
		ind.process(w, 2, 3);
		DataWindow res = ind.getData();
		Assert.assertEquals(8, res.size());
		Assert.assertEquals(.5d,res.getValue(0),0.0001d);
		Assert.assertEquals(.5d,res.getValue(1),0.0001d);
		Assert.assertEquals(.5d,res.getValue(7),0.0001d);
	}
	@org.junit.Test
	public void test2() {
		DataWindow w = createWindow(10d,9d,8d,7d,6d,5d,4d,3d,2d,1d);
		CrossMovingAverageIndicator ind = new CrossMovingAverageIndicator();
		ind.process(w, 2, 3);
		DataWindow res = ind.getData();
		Assert.assertEquals(8, res.size());
		Assert.assertEquals(-.5d,res.getValue(0),0.0001d);
		Assert.assertEquals(-.5d,res.getValue(1),0.0001d);
		Assert.assertEquals(-.5d,res.getValue(7),0.0001d);
	}

	private DataWindow createWindow(double... values) {
		DataWindow w = new DataWindow();
		LocalDate d = LocalDate.now().minusDays(values.length);
		for (int i = 0; i < values.length; i++) {
			DayValue cd = new DayValue(values[i], d);
			w.add(cd);
			d = d.plusDays(1);
		}
		return w;
	}
}
