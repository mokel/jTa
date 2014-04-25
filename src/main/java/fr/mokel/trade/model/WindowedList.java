package fr.mokel.trade.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WindowedList implements Iterable<DayValue> {

	private List<DayValue> data;
	private int from;
	private int to;

	public WindowedList() {
		data = new ArrayList<DayValue>();
		from = 0;
		to = 0;
	}
	
	public WindowedList(List<DayValue> data) {
		this.data = data;
		from = 0;
		to = data.size() - 1;
	}

	public WindowedList(List<DayValue> data, int from, int to) {
		super();
		this.data = data;
		this.from = from;
		this.to = to;
		if (from > to) {
			throw new IllegalArgumentException("from > to");
		}
		if (to >= data.size()) {
			throw new IllegalArgumentException("to >= data.size");
		}
	}

	public WindowedList(List<BarChartData> list, String gg) {
		data = new ArrayList<DayValue>();
		for (BarChartData chartData : list) {
			DayValue dv = new DayValue(chartData.getAdjClose(),
					chartData.getDate());
			dv.setHigh(chartData.getHigh());
			dv.setLow(chartData.getLow());
			dv.setClose(chartData.getClose());
			data.add(dv);
		}
		from = 0;
		to = data.size() - 1;
	}

	public LocalDate getDate(int i) {
		return get(i).getDate();
	}

	public double getValue(int i) {
		return get(i).getValue();
	}

	// public WindowedList floorDataWindow(LocalDate startDate) {
	// WindowedList res = new WindowedList();
	// for (DayValue dayValue : this) {
	// if(dayValue.getDate().isAfter(startDate) ||
	// dayValue.getDate().isEqual(startDate)) {
	// res.add(dayValue);
	// }
	// }
	// return res;
	// }
	//
	// public WindowedList getSubWindow(LocalDate endLocalDate) {
	// WindowedList res = new WindowedList();
	// int i = 0;
	// while (i<size()-1 && (getDate(i).isBefore(endLocalDate) ||
	// getDate(i).isEqual(endLocalDate))) {
	// res.add(get(i));
	// i++;
	// }
	// return res;
	// }

	public WindowedList subWindow(int from, int to) {
		return new WindowedList(data, this.from + from, this.from + to);
	}

	public static WindowedList reverseWindow(WindowedList toReverse) {
		List<DayValue> reversed = new ArrayList<DayValue>();
		for (int i = toReverse.size() - 1; i >= 0; i--) {
			reversed.add(toReverse.get(i));
		}
		return new WindowedList(reversed);
	}

	// public LocalDate getLastDate() {
	// return getDate(size()-1);
	// }

	// public void addAll(List<BarChartData> list) {
	// for (BarChartData data : list) {
	// DayValue v = new DayValue(data.getAdjClose(), data.getDate());
	// add(v);
	// }
	// }
	public int size() {
		return to - from + 1;
	}

	public DayValue get(int i) {
		if (from + i > to) {
			throw new ArrayIndexOutOfBoundsException(from + " + " + i + "  > "
					+ to);
		}
		return data.get(from + i);
	}

	@Override
	public Iterator<DayValue> iterator() {
		return new Iterator<DayValue>() {

			private int index = from;

			@Override
			public boolean hasNext() {
				return index <= to;
			}

			@Override
			public DayValue next() {
				DayValue value = data.get(index);
				index++;
				return value;
			}
		};
	}

	public DayValue getLast() {
		return data.get(to);
	}

	public WindowedList subWindowFrom(int from) {
		return subWindow(from, size() - 1);
	}

	public void add(DayValue value) {
		if (to > 0 && to != data.size() - 1) {
			throw new IllegalStateException("to != data.size()-1 : " + to + " - " + (data.size() - 1));
		}
		data.add(value);
		to = data.size() - 1;
	}
	
	public List<DayValue> getUnderlyingList() {
		return data;
	}
}
