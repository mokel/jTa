package fr.mokel.trade.indicators;

import java.util.ArrayList;
import java.util.List;

public class IndicatorFactory {

	public static List<Indicator> createList() {
		List<Indicator> strats = new ArrayList<Indicator>();
//		CCIIndicator cci = new CCIIndicator();
//		strats.add(cci);
//		MMAIndicator ma = new MMAIndicator();
//		strats.add(ma);
		Indicator macd = new MACDIndicator();
		strats.add(macd);
		return strats;
	}
}
