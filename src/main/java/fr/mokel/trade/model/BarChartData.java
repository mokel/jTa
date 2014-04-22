package fr.mokel.trade.model;

import java.time.LocalDate;

public class BarChartData implements IChartData {

	private LocalDate date;
	private double open;
	private double high;
	private double low;
	private double close;
	private long volume;
	private double adjClose;
	private double adjFactor = -1;
	private BarValueType type;
	
	public BarChartData() {
		type = BarValueType.ADJ_CLOSE;
	}

	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public void setDateString(String dateString) {
		this.date = LocalDate.parse(dateString);
	}


	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}


	public long getVolume() {
		return volume;
	}


	public void setVolume(long volume) {
		this.volume = volume;
	}


	public double getAdjClose() {
		return adjClose;
	}

	public void setAdjClose(double adjClose) {
		this.adjClose = adjClose;
	}

	public double getAdjOpen() {
		return open * getAdjFactor();
	}
	
	public double getAdjHigh() {
		return high * getAdjFactor();
	}
	
	public double getAdjLow() {
		return low * getAdjFactor();
	}

	private double getAdjFactor() {
		if (adjFactor == -1) {
			adjFactor = adjClose/close;
		}
		return adjFactor;
	}
	

	@Override
	public String toString() {
		return "DayMarketData [date=" + date + ", open=" + open + ", high="
				+ high + ", low=" + low + ", close=" + close + ", volume="
				+ volume + ", adjClose=" + adjClose 
				+ ", adjOpen=" + getAdjOpen()
				+ ", adjHigh=" + getAdjHigh()
				+ ", adjLow=" + getAdjLow()
				+ ", adj=" + (close-adjClose)
				+ ", adj%=" + (1-getAdjFactor())*100
				+ "]";
	}


	public BarValueType getType() {
		return type;
	}


	public void setType(BarValueType type) {
		this.type = type;
	}


	public double getValue() {
		switch (type) {
		case OPEN:
			return getOpen();
		case CLOSE:
			return getClose();
		case HIGH:
			return getHigh();
		case LOW:
			return getLow();
		case ADJ_OPEN:
			return getAdjOpen();
		case ADJ_CLOSE:
			return getAdjClose();
		case ADJ_HIGH:
			return getAdjHigh();
		case ADJ_LOW:
			return getAdjLow();
		default:
			return Double.NaN;
		}
	}
	
	
	@Override
	public boolean equals(Object obj) {
//		if(obj != null && obj instanceof Signal) {
//			return getDate().equals(((Signal)obj).getPoint().getDate());
//		}
		return super.equals(obj);
	}	
}

