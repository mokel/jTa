package fr.mokel.trade.strategy;

import fr.mokel.trade.indicator2.CrossMovingAverageIndicator;
import fr.mokel.trade.model.DayValue;
import fr.mokel.trade.model.WindowedList;

public class CrossMovingAvgStrategy extends Strategy2 {

	private DayValue today;
	private DayValue yesterday;
	
	private CrossMovingAvgParamters params = new CrossMovingAvgParamters();
	
	@Override
	StrategyType getType() {
		return StrategyType.MACD;
	}
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
	@Override
	void analyse(DayValue dayValue, WindowedList list) {
		yesterday = today;
		today = new CrossMovingAverageIndicator().process(list, params.getShortSMALength(), params.getLongSMALength());
	}
	
	int getDataWindowLength() {
		 return params.getLongSMALength();
	}

}
