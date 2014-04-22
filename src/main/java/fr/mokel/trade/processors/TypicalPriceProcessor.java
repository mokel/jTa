package fr.mokel.trade.processors;

import fr.mokel.trade.model.BarChartData;
import fr.mokel.trade.model.Chart;
import fr.mokel.trade.model.ChartData;
import fr.mokel.trade.model.IChartData;

public class TypicalPriceProcessor implements IProcess {

	public Chart process(Chart data) {
		Chart res = new Chart();
		for (IChartData chartData : data) {
			BarChartData dayMarketData = (BarChartData) chartData;
			ChartData i = new ChartData();
			i.setDate(dayMarketData.getDate());
			i.setValue((dayMarketData.getAdjClose() + dayMarketData.getAdjHigh() + dayMarketData.getAdjLow()) / 3);
			res.add(i);
		}
		return res;
	}

	public void setNormalParameters() {
		// TODO Auto-generated method stub
		
	}

	public boolean increaseParameters() {
		// TODO Auto-generated method stub
		return false;
	}

}
