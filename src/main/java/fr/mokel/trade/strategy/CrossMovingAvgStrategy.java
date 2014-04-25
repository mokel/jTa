package fr.mokel.trade.strategy;

import fr.mokel.trade.indicator2.CrossMovingAverageIndicator;
import fr.mokel.trade.model.DayValue;
import fr.mokel.trade.model.WindowedList;

public class CrossMovingAvgStrategy extends Strategy2 {

	private DayValue today;
	private DayValue yesterday;
	
	private CrossMovingAvgParamters params;
	
	@Override
	boolean checkDataLength() {
		return getDataSize() >= params.getLongSMALength();
	}
	
	@Override
	public CrossMovingAvgParamters getParameters() {
		return params;
	}
	@Override
	boolean isExitPoint() {
		if(isDataNull()) {
			return false;
		}
		return yesterday.getValue() > 0 && today.getValue() <= 0;
	}
	private boolean isDataNull() {
		return yesterday ==  null || today == null;
	}
	@Override
	boolean isEntryPoint() {
		if(isDataNull()) {
			return false;
		}
		return yesterday.getValue() <= 0 && today.getValue() > 0;
	}
	
	int getDataWindowLength() {
		 return params.getLongSMALength();
	}
	
	
	@Override
	void analyse(DayValue dayValue, WindowedList windowedList,
			int stockListIndex) {
		yesterday = today;
		today = new CrossMovingAverageIndicator().process(windowedList, params.getShortSMALength(), params.getLongSMALength());
	}
	@Override
	public void setParameters(StrategyParamters params) {
		this.params = (CrossMovingAvgParamters) params;
	}

}
