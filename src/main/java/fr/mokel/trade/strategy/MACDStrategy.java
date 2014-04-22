package fr.mokel.trade.strategy;

import java.time.LocalDate;
import java.util.List;

import fr.mokel.trade.model.Chart;
import fr.mokel.trade.model.ChartData;
import fr.mokel.trade.model.IChartData;
import fr.mokel.trade.model.Signal;
import fr.mokel.trade.processors.EMAProcessor;

public class MACDStrategy extends Strategy{

	private Chart signalEmaMacd;
	private Chart macd;
	private Chart macdHistogramme;
	
	private int shortSMALength = 12;
	private int longSMALength = 26;
	private int emaMacdLength = 9;
	
	@Override
	StrategyType getType() {
		return StrategyType.MACD;
	}

	void _runBackTest(List<Signal> signals) {

	}
	
	@Override
	boolean checkDataLength() {
		return getData().size() >= (emaMacdLength + longSMALength);
	}
	
	private boolean checkDateAvailable(LocalDate date) {
		IChartData point = macdHistogramme.getData(date);
		return point != null
				&& macdHistogramme.getPrevious(point) != null;
	}

	void detectSignals(List<Signal> signals, boolean backTest) {
		for (int i = getData().size() - 1; i >= 0; i--) {
			LocalDate date = getData().getDate(i);
			if(checkDateAvailable(date)) {
				IChartData data = getData().getData(date);
				if(isExitPoint(data.getDate())) {
					signals.add(new Signal(data, false));
				} else if(isEntryPoint(data.getDate())) {
					signals.add(new Signal(data, true));
				}
			}
			//stop when we found a signal when no backTest mode
			if(!backTest && !signals.isEmpty()) {
				break;
			}
		}
	}

	void processData() {
		Chart shortEma = new EMAProcessor(shortSMALength).process(getData());
		Chart longEma = new EMAProcessor(longSMALength).process(getData());
		int j = Math.min(shortEma.size(), longEma.size())-1;
		macd = new Chart();
		for(int i = j;i>=0;i--){
			double value = shortEma.getValue(shortEma.size()-1 -i) - longEma.getValue(longEma.size()-1 -i);
			ChartData point = new ChartData();
			point.setValue(value);
			point.setDate(shortEma.getDate(shortEma.size()-1 -i));
			macd.add(point);
		}
		signalEmaMacd = new EMAProcessor(emaMacdLength).process(macd);
		
		int k = Math.min(signalEmaMacd.size(), macd.size())-1;
		macdHistogramme = new Chart();
		for(int i = k;i>=0;i--){
			double value = macd.getValue(macd.size()-1 -i) - signalEmaMacd.getValue(signalEmaMacd.size()-1 -i);
			ChartData point = new ChartData();
			point.setValue(value);
			point.setDate(macd.getDate(macd.size()-1 -i));
			macdHistogramme.add(point);
		}		
	}

	private boolean isEntryPoint(LocalDate date) {
		IChartData point = macdHistogramme.getData(date);
		double lastValue = point.getValue();
		double oldValue = macdHistogramme.getPrevious(point).getValue();
		return lastValue > 0 && oldValue <= 0;
	}

	private boolean isExitPoint(LocalDate date) {
		IChartData point = macdHistogramme.getData(date);
		double lastValue = point.getValue();
		double oldValue = macdHistogramme.getPrevious(point).getValue();
		return lastValue < 0 && oldValue >= 0;
	}
}
