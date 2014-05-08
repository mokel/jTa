package fr.mokel.trade.indicator2;

import java.util.List;

import fr.mokel.trade.model.DayValue;

public interface IndicatorChart {

	public abstract List<DayValue> process(List<DayValue> prices, IndicatorParameters params);

}
