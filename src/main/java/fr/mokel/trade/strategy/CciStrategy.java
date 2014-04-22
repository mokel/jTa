package fr.mokel.trade.strategy;

import java.util.ArrayList;
import java.util.List;

import fr.mokel.trade.functions.Average;
import fr.mokel.trade.functions.MeanDeviation;
import fr.mokel.trade.functions.TypicalPrice;
import fr.mokel.trade.model.DayValue;
import fr.mokel.trade.model.WindowedList;

public class CciStrategy extends Strategy2 {

	private DayValue today;
	private DayValue yesterday;

	private CciParamters params ;//= new CciParamters();

	@Override
	boolean checkDataLength() {
		return getDataSize() >= params.period;
	}

	@Override
	public CciParamters getParameters() {
		return params;
	}
	
	@Override
	public void setParameters(StrategyParamters params) {
		this.params = (CciParamters) params;
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
		if (isDataNull()) {
			return false;
		}
		return yesterday.getValue() <= -100 && today.getValue() > -100;
	}

	@Override
	void analyse(DayValue dayValue, WindowedList list, int stockListIndex) {
		if(stockListIndex < params.period - 1) {
			return;
		}
		yesterday = today;
		today = cci.get(stockListIndex - (params.period-1));
	}

	int getDataWindowLength() {
		return params.period;
	}

	private WindowedList typicalPrices;
	private List<DayValue> typicalPricesSMA;
	private List<DayValue> meanDeviation;
	private List<DayValue> cci;

	@Override
	void preProcess(WindowedList list) {
		typicalPrices = new WindowedList(new ArrayList<DayValue>());
		TypicalPrice tp = new TypicalPrice();
		for (DayValue dayValue : list) {
			typicalPrices.add(tp.process(dayValue));
		}
		typicalPricesSMA = new ArrayList<DayValue>();
		Average avg = new Average();
		int sizeAvg = typicalPrices.size() - params.period;
		for (int i = 0; i <= sizeAvg; i++) {
			typicalPricesSMA.add(avg.process(typicalPrices.subWindow( i, i + params.period - 1)));
		}
		//typicalPricesSMA is smaller than typicalPrices
		meanDeviation = new ArrayList<DayValue>();
		MeanDeviation md = new MeanDeviation();
		for (int i  = 0; i < typicalPricesSMA.size(); i++) {
			DayValue value = md.process(typicalPrices.subWindow(i, i + (params.period - 1)), typicalPricesSMA.get(i));
			meanDeviation.add(value);
		}
		cci = new ArrayList<DayValue>();
		for (int i  = 0; i < typicalPricesSMA.size(); i++) {
			if(!typicalPrices.getDate(i + params.period -1).equals(typicalPricesSMA.get(i).getDate())) {
				throw new RuntimeException("BUG ");
			}
			double value = typicalPrices.getValue(i + params.period -1) - typicalPricesSMA.get(i).getValue();
			value = value / (0.015*meanDeviation.get(i).getValue());
			cci.add(new DayValue(value, meanDeviation.get(i).getDate()));
		}
	}
}
