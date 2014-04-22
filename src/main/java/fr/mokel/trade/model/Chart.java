package fr.mokel.trade.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Chart extends ArrayList<IChartData> {

	private static final long serialVersionUID = 5409873209211897231L;

	public LocalDate getDate(int i) {
		return get(i).getDate();
	}
	
	public double getValue(int i) {
		return get(i).getValue();
	}
	
	public IChartData getData(LocalDate date) {
		for (IChartData data : this) {
			if(data.getDate().equals(date)) {
				return data;
			}
		}
		return null;
	}
	
	public IChartData getPrevious(IChartData point) {
		int index = indexOf(point);
		if(index > 0) {
			return get(index -1);
		}
		return null;
	}
	
	public BarChartData getBar(int i) {
		return (BarChartData) get(i);
	}
	
	public Chart getSubChart(LocalDate endLocalDate) {
		Chart res = new Chart();
		int i = 0;
		while (i<size()-1 && (getDate(i).isBefore(endLocalDate) || getDate(i).isEqual(endLocalDate))) {
			res.add(get(i));
			i++;
		}
		return res;
	}
	
	public Chart subChart(int from, int to) {
		List<IChartData> subList = this.subList(from, to);
		Chart res = new Chart();
		res.addAll(subList);
		return res;
	}
	
    public static Chart reverseList(Chart toReverse){
    	Chart result = new Chart();
    	for (int i = toReverse.size()-1; i >= 0; i--) {
			result.add(toReverse.get(i));
		}
    	return result;
	}

}
