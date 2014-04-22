package fr.mokel.trade.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataWindow extends ArrayList<DayValue> {

	private static final long serialVersionUID = -129124413657463597L;

	public LocalDate getDate(int i) {
		return get(i).getDate();
	}
	
	public double getValue(int i) {
		return get(i).getValue();
	}
	
	public DayValue getPrevious(DayValue point) {
		int index = indexOf(point);
		if(index > 0) {
			return get(index -1);
		}
		return null;
	}
	
	public DataWindow floorDataWindow(LocalDate startDate) {
		DataWindow res = new DataWindow();
		for (DayValue dayValue : this) {
			if(dayValue.getDate().isAfter(startDate) || dayValue.getDate().isEqual(startDate)) {
				res.add(dayValue);
			}
		}
		return res;
	}
	
	public DataWindow getSubWindow(LocalDate endLocalDate) {
		DataWindow res = new DataWindow();
		int i = 0;
		while (i<size()-1 && (getDate(i).isBefore(endLocalDate) || getDate(i).isEqual(endLocalDate))) {
			res.add(get(i));
			i++;
		}
		return res;
	}
	
	public DataWindow subWindow(int from, int to) {
		List<DayValue> subList = this.subList(from, to);
		DataWindow res = new DataWindow();
		res.addAll(subList);
		return res;
	}
	
    public static DataWindow reverseWindow(DataWindow toReverse){
    	DataWindow result = new DataWindow();
    	for (int i = toReverse.size()-1; i >= 0; i--) {
			result.add(toReverse.get(i));
		}
    	return result;
	}

	public LocalDate getLastDate() {
		return getDate(size()-1);
	}

	public void addAll(List<BarChartData> list) {
		for (BarChartData data : list) {
			DayValue v = new DayValue(data.getAdjClose(), data.getDate());
			add(v);
		}
	}

}
