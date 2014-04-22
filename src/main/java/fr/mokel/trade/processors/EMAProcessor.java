package fr.mokel.trade.processors;

import fr.mokel.trade.model.Chart;
import fr.mokel.trade.model.ChartData;

public class EMAProcessor implements IProcess {

	private int length = 10;
	private Chart ema;
	
	public EMAProcessor(int length) {
		this.length = length;
		ema = new Chart();
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public Chart process(Chart data) {
		Chart sma = new SimpleMovingAverageProcessor(length).process(data.subChart(0, length));
		ema.add(sma.get(0));
		
		double alpha = (2d/(length+1));
		for(int i=length;i<data.size();i++) {
			double value = (data.getValue(i)-ema.getValue(i-length))*alpha + ema.getValue(i-length);
			ema.add(new ChartData(value, data.getDate(i)));
		}
		return ema;
	}

	public void setNormalParameters() {
		
	}

	public boolean increaseParameters() {
		return false;
	}

}
