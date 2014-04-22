package fr.mokel.trade.indicators;


import java.time.LocalDate;

import fr.mokel.trade.model.Chart;
import fr.mokel.trade.model.ChartData;
import fr.mokel.trade.model.IChartData;
import fr.mokel.trade.processors.SimpleMovingAverageProcessor;

public class MMAIndicator implements Indicator {

	Chart diffSma;
	Chart sma20;
	Chart sma50;
	
	public String getName() {
		return "MMA";
	}

	public void processData(Chart data) {
		checkDataLength(data);
		SimpleMovingAverageProcessor sma20p = new SimpleMovingAverageProcessor();
		sma20p.setLength(20);
		sma20 = sma20p.process(data);
		
		SimpleMovingAverageProcessor sma50p = new SimpleMovingAverageProcessor();
		sma50p.setLength(50);
		sma50 = sma50p.process(data);
		
		int j = Math.min(sma50.size(), sma20.size())-1;
		diffSma = new Chart();
		for(int i = j;i>=0;i--){
			double value = sma20.getValue(sma20.size()-1 -i) - sma50.getValue(sma50.size()-1 -i);
			ChartData point = new ChartData();
			point.setValue(value);
			point.setDate(sma20.getDate(sma20.size()-1 -i));
			diffSma.add(point);
		}
	}

	public boolean checkDataLength(Chart data) {
		return data.size() >= 50 +1;
	}

	public boolean isGoodPosition() {
		return false;
	}

	public boolean isBadPosition() {
		return false;
	}

	public Integer getState() {
		int value = 0;
		if(isEntryPoint()) {
			value = 255;
		}
		if(isExitPoint()) {
			value = -255;
		}
		return value;
	}

	public String entryPointText() {
		int nbDay = getDayNbCrossPositive(20);
		if(nbDay > 0) {
			return "SMA20 passed over SMA50 "+nbDay+" ago";
		}
		return null;
	}

	public String exitPointText() {
		int nbDay = getDayNbCrossNegative(20);
		if(nbDay > 0) {
			return "SMA50 passed over SMA20 "+nbDay+" days ago";
		}
		return null;
	}
	
	private int getDayNbCrossNegative(int length) {
		double lastValue = diffSma.getValue(diffSma.size()-1);
		int nbDay = 0;
		if(lastValue <= 0) {
			for(int i=0;i<length;i++) {
				double oldValue = diffSma.getValue(diffSma.size()-i-2);
				if(oldValue > 0) {
					nbDay = i+1;
					break;
				}
			}
		}
		return nbDay;
	}

	private int getDayNbCrossPositive(int length){
		double lastValue = diffSma.getValue(diffSma.size()-1);
		int nbDay = 0;
		if(lastValue >= 0) {
			for(int i=0;i<length;i++) {
				double oldValue = diffSma.getValue(diffSma.size()-i-2);
				if(oldValue < 0) {
					nbDay = i+1;
					break;
				}
			}
		}
		return nbDay;
	}
	
	public boolean isEntryPoint() {
		double lastValue = diffSma.getValue(diffSma.size()-1);
		double oldValue = diffSma.getValue(diffSma.size()-2);
		return lastValue > 0 && oldValue <= 0;
	}

	public boolean isExitPoint() {
		double lastValue = diffSma.getValue(diffSma.size()-1);
		double oldValue = diffSma.getValue(diffSma.size()-2);
		return lastValue < 0 && oldValue >= 0;
	}
	
	public Chart getChart() {
		return diffSma;
	}

	public IndicatorType getType() {
		return IndicatorType.MMA;
	}

	public boolean isEntryPoint(LocalDate date) {
		IChartData point = diffSma.getData(date);
		double lastValue = point.getValue();
		double oldValue = diffSma.getPrevious(point).getValue();
		return lastValue > 0 && oldValue <= 0;
	}

	public boolean isExitPoint(LocalDate date) {
		IChartData point = diffSma.getData(date);
		double lastValue = point.getValue();
		double oldValue = diffSma.getPrevious(point).getValue();
		return lastValue < 0 && oldValue >= 0;
	}
	
	public boolean checkDateAvailable(LocalDate date) {
		IChartData point = diffSma.getData(date);
		return point != null
				&& diffSma.getPrevious(point) != null;
	}


}
