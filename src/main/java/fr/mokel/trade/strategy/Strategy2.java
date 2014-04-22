package fr.mokel.trade.strategy;

import java.util.ArrayList;
import java.util.List;

import fr.mokel.trade.model.DayValue;
import fr.mokel.trade.model.Stock;
import fr.mokel.trade.model.WindowedList;

public abstract class Strategy2 {

	private Stock stock;
	
	private Double stopLoss = null;//0.1d;
	
	private DayValue entryPoint;
	
	private List<Trade> trades;
	
	public List<Trade> process(Stock s) {
		stock = s;
		trades = new ArrayList<Trade>();
		if(checkDataLength()) {
			preProcess(stock.getList());
			for(int i = getDataWindowLength()-1; i<getDataSize();i++){
				DayValue dayValue = stock.getList().get(i);
				if(hasToStopLoss(dayValue)) {
					newTrade(dayValue);
				}
				analyse(dayValue, stock.getList().subWindow(i - (getDataWindowLength()-1), i), i); // from inclusive, to inklusiv
				if(isEntryPoint()) {
					entryPoint = dayValue;
				} else if(isExitPoint() && entryPoint != null) {
					newTrade(dayValue);
				} 
			}
		}
		return trades;
	}
	
	private void newTrade(DayValue dayValue) {
		trades.add(new Trade(entryPoint, dayValue));
		entryPoint = null;		
	}

	private boolean hasToStopLoss(DayValue dayValue) {
		if(stopLoss != null && entryPoint != null) {
			return new Trade(entryPoint, dayValue).getPerformance() <= (1-stopLoss.doubleValue());
		}
		return false;
	}

	abstract boolean isExitPoint();
	abstract boolean isEntryPoint();
	abstract void analyse(DayValue dayValue, WindowedList windowedList, int sotckListIndex);

	int getDataSize() {
		return stock.getList().size();
	}
	
	abstract boolean checkDataLength();
	abstract int getDataWindowLength();
	public abstract StrategyParamters getParameters();
	public abstract void setParameters(StrategyParamters params);
	void preProcess(WindowedList list) {
		
	}
}

