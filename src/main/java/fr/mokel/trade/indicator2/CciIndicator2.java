package fr.mokel.trade.indicator2;

import java.util.ArrayList;
import java.util.List;

import fr.mokel.trade.functions.Average;
import fr.mokel.trade.functions.MeanDeviation;
import fr.mokel.trade.functions.TypicalPrice;
import fr.mokel.trade.gui2.util.SwingField;
import fr.mokel.trade.model.DayValue;

public class CciIndicator2 implements IndicatorChart {

	@Override
	public List<DayValue> process(List<DayValue> prices, IndicatorParameters iparams) {
		CciIndicatorParams params = (CciIndicatorParams) iparams;
		List<DayValue> typicalPrices = createTypicalPrices(prices);
		List<DayValue> typicalPricesSMA = createMovingAvg(params, typicalPrices);
		List<DayValue> meanDeviation = createMeanDeviation(params, typicalPrices, typicalPricesSMA);
		List<DayValue> cci = createCci(params, typicalPrices, typicalPricesSMA, meanDeviation);
		return cci;
	}

	private List<DayValue> createCci(CciIndicatorParams params, List<DayValue> typicalPrices,
			List<DayValue> typicalPricesSMA, List<DayValue> meanDeviation) {
		List<DayValue> cci = new ArrayList<DayValue>();
		for (int i = 0; i < typicalPricesSMA.size(); i++) {
			// check synchro between the 2 list
			if (!typicalPrices.get(i + params.period - 1).getDate()
					.equals(typicalPricesSMA.get(i).getDate())) {
				throw new RuntimeException("BUG ");
			}
			double value = typicalPrices.get(i + params.period - 1).getValue()
					- typicalPricesSMA.get(i).getValue();
			value = value / (0.015 * meanDeviation.get(i).getValue());
			cci.add(new DayValue(value, meanDeviation.get(i).getDate()));
		}
		return cci;
	}

	private List<DayValue> createMeanDeviation(CciIndicatorParams params,
			List<DayValue> typicalPrices, List<DayValue> typicalPricesSMA) {
		// typicalPricesSMA is smaller than typicalPrices
		List<DayValue> meanDeviation = new ArrayList<DayValue>();
		MeanDeviation md = new MeanDeviation();
		for (int i = 0; i < typicalPricesSMA.size(); i++) {
			DayValue value = md.process(typicalPrices, typicalPricesSMA.get(i), i, params.period);
			meanDeviation.add(value);
		}
		return meanDeviation;
	}

	private List<DayValue> createMovingAvg(CciIndicatorParams params, List<DayValue> typicalPrices) {
		List<DayValue> typicalPricesSMA = new ArrayList<DayValue>();
		Average avg = new Average();
		int sizeAvg = typicalPrices.size() - params.period;
		for (int i = 0; i <= sizeAvg; i++) {
			typicalPricesSMA.add(avg.process(typicalPrices, i, params.period));
		}
		return typicalPricesSMA;
	}

	private List<DayValue> createTypicalPrices(List<DayValue> prices) {
		List<DayValue> typicalPrices = new ArrayList<DayValue>();
		TypicalPrice tp = new TypicalPrice();
		for (DayValue dayValue : prices) {
			typicalPrices.add(tp.process(dayValue));
		}
		return typicalPrices;
	}

	public static class CciIndicatorParams implements IndicatorParameters {
		@SwingField(order = 1)
		int period = 20;

		public int getPeriod() {
			return period;
		}

		public void setPeriod(int period) {
			this.period = period;
		}

		@Override
		public IndicatorChart createIndicatorInstance() {
			return new CciIndicator2();
		}

	}

}
