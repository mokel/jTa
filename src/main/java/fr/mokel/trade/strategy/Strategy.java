package fr.mokel.trade.strategy;

import java.util.ArrayList;
import java.util.List;

import fr.mokel.trade.model.Chart;
import fr.mokel.trade.model.Signal;
import fr.mokel.trade.model.Stock;

public abstract class Strategy {

	private Stock stock;
	
	public Signal runTest(Stock s) {
		List<Signal> signals = _runTest(s, false);
		if(!signals.isEmpty()) {
			return signals.get(0);
		}
		return null;
	}
	
	public List<Signal> runBackTest(Stock s) {
		return _runTest(s, true);
	}
	
	private List<Signal> _runTest(Stock s, boolean backTest) {
		List<Signal> signals = new ArrayList<Signal>();
		stock = s;
		if(checkDataLength()) {
			processData();
			detectSignals(signals, backTest);
		}
		return signals;
	}
	
	Chart getData() {
		return stock.getBarChart();
	}
	
	abstract boolean checkDataLength();
	abstract StrategyType getType();
	abstract void detectSignals(List<Signal> signals, boolean backTest);
	abstract void processData();
}
