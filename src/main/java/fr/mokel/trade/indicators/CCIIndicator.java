package fr.mokel.trade.indicators;

import fr.mokel.trade.model.Chart;
import fr.mokel.trade.processors.CCIProcessor;

public class CCIIndicator {//implements IIndicator {

	private Chart cciChart;
	
	public String getName() {
		return "CCI";
	}
	
	public void processData(Chart data) {
		CCIProcessor cci = new CCIProcessor();
		cciChart = cci.process(data);
	}

	public boolean isEntryPoint() {
		return cciChart.getValue(cciChart.size()-1) > -100 
				&& cciChart.getValue(cciChart.size()-2) <= -100;
	}

	public boolean isExitPoint() {
		return cciChart.getValue(cciChart.size()-1) < 100 
		&& cciChart.getValue(cciChart.size()-2) >= 100;
	}

	public boolean isGoodPosition() {
		return false;
	}

	public boolean isBadPosition() {
		return false;
	}

	public Integer getState() {
		int value = 0;
		if(isEntryPoint()) {
			value = 255;
		}
		if(isExitPoint()) {
			value = -255;
		}
		return value;
	}

	public String entryPointText() {
		// TODO Auto-generated method stub
		return null;
	}

	public String exitPointText() {
		// TODO Auto-generated method stub
		return null;
	}

	public Chart getChart() {
		return cciChart;
	}

}
