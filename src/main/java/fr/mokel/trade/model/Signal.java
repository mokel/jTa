package fr.mokel.trade.model;


public class Signal {
	
	private DayValue stockValue;
	private DayValue indicatorValue;
	private boolean entry;
	
	public Signal(DayValue stockValue, DayValue indicatorValue, boolean entry) {
		this.indicatorValue = indicatorValue;
		this.stockValue = stockValue;
		this.entry = entry;
	}


	public boolean isEntryPoint() {
		return entry;
	}

	public boolean isExitPoint() {
		return !entry;
	}

	@Override
	public String toString() {
		return "Signal [point=" + indicatorValue + ", entry=" + entry + "]";
	}


	public DayValue getStockValue() {
		return stockValue;
	}


	public void setStockValue(DayValue stockValue) {
		this.stockValue = stockValue;
	}


	public DayValue getIndicatorValue() {
		return indicatorValue;
	}


	public void setIndicatorValue(DayValue indicatorValue) {
		this.indicatorValue = indicatorValue;
	}
	

}
