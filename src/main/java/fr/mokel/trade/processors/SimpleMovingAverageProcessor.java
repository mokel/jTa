package fr.mokel.trade.processors;

import fr.mokel.trade.model.Chart;
import fr.mokel.trade.model.ChartData;

public class SimpleMovingAverageProcessor implements IProcess {

	private int length = 0;
	
	public SimpleMovingAverageProcessor(int aLength) {
		this.length = aLength;
	}
	
	public Chart process(Chart data) {
		Chart res = new Chart();
		for(int i = length -1; i < data.size();i++) {
			ChartData chartData = new ChartData();
			chartData.setDate(data.get(i).getDate());
			double value = 0;
			for(int j = i - length + 1;j<= i;j++){
				value += data.get(j).getValue();
			}
			value = value / length;
			chartData.setValue(value);
			res.add(chartData);
		}
		return res;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setNormalParameters() {
		// TODO Auto-generated method stub
		
	}

	public boolean increaseParameters() {
		// TODO Auto-generated method stub
		return false;
	}

	
}
