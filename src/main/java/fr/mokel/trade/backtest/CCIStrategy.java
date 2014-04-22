package fr.mokel.trade.backtest;

import fr.mokel.trade.model.BarChartData;
import fr.mokel.trade.model.Chart;
import fr.mokel.trade.processors.CCIProcessor;

public class CCIStrategy extends AbstractBackTest {

	CCIProcessor cci = new CCIProcessor();
	
	@Override
	public void _runBackTest(Chart data) {
		Chart cciChart = cci.process(data);
		for (int i = 1;i<cciChart.size();i++) {
			// cci crossed +100 line decresing => sell
			if (isLong() && cciChart.getValue(i)<100 && cciChart.getValue(i-1)>=100) {
				sell(cciChart.get(i), data);
			}
			// cci crossed -100 line incresing => buy
			if(!isLong() && cciChart.getValue(i)>-100 && cciChart.getValue(i-1)<=-100) {
				buy(cciChart.get(i), data);
			}
		}
		sellAll((BarChartData)data.get(data.size()-1));
	}

	public boolean increseParam(){
		return cci.increaseParameters();
	}

	@Override
	Object getParams() {
		return cci.getParam();
	}

	@Override
	void _initStrat() {
		
	}

}
