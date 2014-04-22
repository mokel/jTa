package fr.mokel.trade.indicators;


import java.time.LocalDate;

import fr.mokel.trade.model.Chart;
import fr.mokel.trade.model.ChartData;
import fr.mokel.trade.model.IChartData;
import fr.mokel.trade.processors.EMAProcessor;

public class MACDIndicator implements Indicator {

	private Chart signalEma9Macd;
	private Chart macd;
	private Chart macdHistogramme;
	
	public String getName() {
		return "MACD";
	}

	public void processData(Chart data) {
		Chart ema12 = new EMAProcessor(12).process(data);
		Chart ema26 = new EMAProcessor(26).process(data);
		int j = Math.min(ema12.size(), ema26.size())-1;
		macd = new Chart();
		for(int i = j;i>=0;i--){
			double value = ema12.getValue(ema12.size()-1 -i) - ema26.getValue(ema26.size()-1 -i);
			ChartData point = new ChartData();
			point.setValue(value);
			point.setDate(ema12.getDate(ema12.size()-1 -i));
			macd.add(point);
		}
		signalEma9Macd = new EMAProcessor(9).process(macd);
		
		int k = Math.min(signalEma9Macd.size(), macd.size())-1;
		macdHistogramme = new Chart();
		for(int i = k;i>=0;i--){
			double value = macd.getValue(macd.size()-1 -i) - signalEma9Macd.getValue(signalEma9Macd.size()-1 -i);
			ChartData point = new ChartData();
			point.setValue(value);
			point.setDate(macd.getDate(macd.size()-1 -i));
			macdHistogramme.add(point);
		}
	}
	
	public boolean checkDataLength(Chart data) {
		return data.size() >= 35;
	}
	
	public boolean checkLocalDateAvailable(LocalDate date) {
		IChartData point = macdHistogramme.getData(date);
		return point != null
				&& macdHistogramme.getPrevious(point) != null;
	}
	
	public boolean isEntryPoint() {
		double lastValue = macdHistogramme.getValue(macdHistogramme.size()-1);
		double oldValue = macdHistogramme.getValue(macdHistogramme.size()-2);
		return lastValue > 0 && oldValue <= 0;
	}

	public boolean isExitPoint() {
		double lastValue = macdHistogramme.getValue(macdHistogramme.size()-1);
		double oldValue = macdHistogramme.getValue(macdHistogramme.size()-2);
		return lastValue < 0 && oldValue >= 0;
	}
	
	public boolean isEntryPoint(LocalDate date) {
		IChartData point = macdHistogramme.getData(date);
		double lastValue = point.getValue();
		double oldValue = macdHistogramme.getPrevious(point).getValue();
		return lastValue > 0 && oldValue <= 0;
	}

	public boolean isExitPoint(LocalDate date) {
		IChartData point = macdHistogramme.getData(date);
		double lastValue = point.getValue();
		double oldValue = macdHistogramme.getPrevious(point).getValue();
		return lastValue < 0 && oldValue >= 0;
	}
	
	
	public String entryPointText() {
		// TODO Auto-generated method stub
		return null;
	}

	public String exitPointText() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isGoodPosition() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isBadPosition() {
		// TODO Auto-generated method stub
		return false;
	}

	public Integer getState() {
		// TODO Auto-generated method stub
		return null;
	}

	public Chart getChart() {
		return macdHistogramme;
	}

	public IndicatorType getType() {
		return IndicatorType.MACD;
	}

}
