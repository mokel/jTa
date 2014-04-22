package fr.mokel.trade.processors;

import fr.mokel.trade.model.Chart;
import fr.mokel.trade.model.ChartData;

public class MagicTrendProcessor implements IProcess {
	
	private CCIProcessor cci;
	private TrueRangeProcessor tr;
	private SimpleMovingAverageProcessor smaTr;
	
	public MagicTrendProcessor() {
		 cci = new CCIProcessor();
		 tr = new TrueRangeProcessor();
		 smaTr = new SimpleMovingAverageProcessor();
		 smaTr.setLength(5);
	}
	
	 public Chart process(Chart data) {
		 Chart cciChar = cci.process(data);
		 Chart trChart = tr.process(data);
		 Chart smaTrChart = smaTr.process(trChart);
		 
		 //reverse list => lists are not of the same length
		 //now, we can access the same day with i = x for all lists
		 cciChar = Chart.reverseList(cciChar);
		 smaTrChart = Chart.reverseList(smaTrChart);
		 data = Chart.reverseList(data);
		 Chart magicChart = new Chart();
		 int minLength = Math.min(cciChar.size(), smaTrChart.size());
		 for(int i=minLength-1; i >= 0 ;i--){
			 ChartData magic = new ChartData();
			 if (cciChar.getValue(i) >= 0) {
				 magic.setValue(data.getBar(i).getAdjLow() - smaTrChart.getValue(i));
			 }
			 if (cciChar.getValue(i) < 0) {
				 magic.setValue(data.getBar(i).getAdjHigh() + smaTrChart.getValue(i));
			 }
			 if (cciChar.getValue(i) >= 0 && i != (minLength-1) && magic.getValue() < magicChart.getValue(minLength-1 -i -1)) {
				 magic.setValue(magicChart.getValue(minLength-1 -i -1));
			 }
			 if (cciChar.getValue(i) < 0 && i != (minLength-1) && magic.getValue() > magicChart.getValue(minLength-1 -i -1)) {
				 magic.setValue(magicChart.getValue(minLength-1 -i -1));
			 }
			 magic.setDate(cciChar.getDate(i));
			 magicChart.add(magic);
		 }
		 return magicChart;
	}

	public void setNormalParameters() {
		// TODO Auto-generated method stub
		
	}

	public boolean increaseParameters() {
		// TODO Auto-generated method stub
		return false;
	}
}
