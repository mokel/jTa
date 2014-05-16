package fr.mokel.trade.strategy;

import java.util.List;

import fr.mokel.trade.indicator2.CrossMovingAverageIndicator;
import fr.mokel.trade.indicator2.CrossMovingAverageIndicator.CrossMovingAverageIndicatorParams;
import fr.mokel.trade.model.DayValue;

public class CrossMovingAvgStrategy extends Strategy2 {

	private DayValue today;
	private DayValue yesterday;
	private List<DayValue> cmaIndicator;
	
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
	
	int getMinDataLength() {
		 return params.getLongSMALength();
	}
	
	
	@Override
	void analyse(DayValue dayValue, int stockListIndex) {
		yesterday = today;
		today = cmaIndicator.get(stockListIndex - getMinDataLength() + 1);
	}
	@Override
	public void setParameters(StrategyParamters params) {
		this.params = (CrossMovingAvgParamters) params;
	}

	@Override
	void preProcess(List<DayValue> list) {
		CrossMovingAverageIndicatorParams p = new CrossMovingAverageIndicatorParams();
		p.setAverageLarge(params.getLongSMALength());
		p.setAverageSmall(params.getShortSMALength());
		cmaIndicator = new CrossMovingAverageIndicator().process(list, p);
	}

}
