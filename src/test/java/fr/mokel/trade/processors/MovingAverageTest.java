package fr.mokel.trade.processors;

import java.time.LocalDate;

import org.junit.Assert;

import fr.mokel.trade.indicator2.MovingAverageIndicator;
import fr.mokel.trade.model.DataWindow;
import fr.mokel.trade.model.DayValue;

public class MovingAverageTest {

	@org.junit.Test
	public void test2() {
		DataWindow w = createWindow(1d, 2d, 3d, 4d, 5d, 6d, 7d, 8d, 9d, 10d);
		MovingAverageIndicator mai = new MovingAverageIndicator();
		mai.process(w, 2);
		DataWindow res = mai.getData();
		Assert.assertEquals(9, res.size());
		Assert.assertEquals(1.5d, res.getValue(0), 0.0001d);
		Assert.assertEquals(2.5d, res.getValue(1), 0.0001d);
		Assert.assertEquals(9.5d, res.getValue(8), 0.0001d);
		Assert.assertEquals(LocalDate.now(), res.getDate(8));
	}

	@org.junit.Test
	public void test3() {
		DataWindow w = createWindow(1d, 2d, 3d, 4d, 5d, 6d, 7d, 8d, 9d, 10d);
		MovingAverageIndicator mai = new MovingAverageIndicator();
		mai.process(w, 3);
		DataWindow res = mai.getData();
		Assert.assertEquals(8, res.size());
		Assert.assertEquals(2d, res.getValue(0), 0.0001d);
		Assert.assertEquals(3d, res.getValue(1), 0.0001d);
		Assert.assertEquals(9d, res.getValue(7), 0.0001d);
		Assert.assertEquals(LocalDate.now(), res.getDate(7));
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
