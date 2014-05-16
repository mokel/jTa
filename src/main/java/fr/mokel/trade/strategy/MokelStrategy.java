package fr.mokel.trade.strategy;

import java.util.List;

import fr.mokel.trade.indicator2.CciIndicator2;
import fr.mokel.trade.indicator2.CciIndicator2.CciIndicatorParams;
import fr.mokel.trade.indicator2.CrossMovingAverageIndicator.CrossMovingAverageIndicatorParams;
import fr.mokel.trade.model.DayValue;

public class MokelStrategy extends Strategy2 {

	private DayValue today;
	private DayValue cmaToday;
	private DayValue yesterday;
	private List<DayValue> cci;
	private List<DayValue> cmaIndicator;
	private MokelParamters params;// = new CciParamters();

	@Override
	boolean checkDataLength() {
		return getDataSize() >= params.getLongSMALength() || getDataSize() >= params.getCciLength();
	}

	@Override
	public MokelParamters getParameters() {
		return params;
	}

	@Override
	public void setParameters(StrategyParamters params) {
		this.params = (MokelParamters) params;
	}

	@Override
	boolean isExitPoint() {
		if (isDataNull()) {
			return false;
		}
		return yesterday.getValue() > 100 && today.getValue() <= 100;
	}

	private boolean isDataNull() {
		return yesterday == null || today == null;
	}

	@Override
	boolean isEntryPoint() {
		if (isDataNull()) {// || cmaToday.getValue() < 0) {
			return false;
		}
		return yesterday.getValue() <= -100 && today.getValue() > -100;
	}

	@Override
	void analyse(DayValue dayValue, int stockListIndex) {
		//stockListIndex i.e. 49
		yesterday = today;
		today = cci.get(stockListIndex - (params.getCciLength() - 1)); // 49 - (5-1) = 45
		// cmaToday = cmaIndicator.get(stockListIndex -
		// (params.getLongSMALength() - 1));
		// 49 - (50 -1) = 0
	}

	int getMinDataLength() {
		return Math.max(params.getCciLength(), params.getLongSMALength());
	}

	@Override
	void preProcess(List<DayValue> list) {
		CciIndicator2 cciIndicator = new CciIndicator2();
		CciIndicatorParams cciParams = new CciIndicatorParams();
		cciParams.setPeriod(params.getCciLength());
		cci = cciIndicator.process(list, cciParams);

		CrossMovingAverageIndicatorParams p = new CrossMovingAverageIndicatorParams();
		p.setAverageLarge(params.getLongSMALength());
		p.setAverageSmall(params.getShortSMALength());
		// cmaIndicator = new CrossMovingAverageIndicator().process(list, p);
	}
}
