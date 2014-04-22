package fr.mokel.trade.processors;

import fr.mokel.trade.model.BarChartData;
import fr.mokel.trade.model.Chart;
import fr.mokel.trade.model.ChartData;

public class TrueRangeProcessor implements IProcess {

	public Chart process(Chart data) {
		Chart res = new Chart();
		for (int i=0;i<data.size();i++) {
			BarChartData bar = (BarChartData) data.get(i);
			double dayVol = Math.abs(bar.getAdjLow() - bar.getAdjHigh());
			double upVol = 0;
			double lowVol = 0;
			if (i != 0) {
				BarChartData previousBar = (BarChartData) data.get(i-1);
				upVol = Math.abs(bar.getAdjHigh() - previousBar.getAdjClose());
				lowVol = Math.abs(bar.getAdjLow() - previousBar.getAdjClose());
			}
			double max = Math.max(upVol, lowVol);
			max = Math.max(max, dayVol);
			ChartData trueRange = new ChartData();
			trueRange.setDate(bar.getDate());
			trueRange.setValue(max);
			res.add(trueRange);
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
