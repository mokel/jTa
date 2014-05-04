package fr.mokel.trade.backtest;

import fr.mokel.trade.data.MarketDataRetriever;
import fr.mokel.trade.data.YahooDataRetriever;
import fr.mokel.trade.model.Chart;

public class StrategyRunner {
	
	
	
	public static void main(String[] args) {

		CCIStrategy strat = new CCIStrategy();
		MarketDataRetriever ret = new YahooDataRetriever();
		Chart data = null;//ret.getDayData("GLE.PA");
		strat.runBackTest(data);
		while(strat.increseParam()) {
			strat.runBackTest(data);
		}
		System.out.println("##################################################");
		
		CCIStrategy strat2 = new CCIStrategy();
		Chart data2 = null;//ret.getDayData("BNP.PA");
		strat2.runBackTest(data2);
		while(strat2.increseParam()) {
			strat2.runBackTest(data2);
		}
		System.out.println("##################################################");
		
		CCIStrategy strat3 = new CCIStrategy();
		Chart data3 = null;//ret.getDayData("ACA.PA");
		strat3.runBackTest(data3);
		while(strat3.increseParam()) {
			strat3.runBackTest(data3);
		}
	}
}
