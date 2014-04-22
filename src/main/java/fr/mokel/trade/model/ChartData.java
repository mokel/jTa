package fr.mokel.trade.model;

import java.time.LocalDate;


public class ChartData implements IChartData {
	
	private double value;
	private LocalDate date;
	
	public ChartData() {
	}
	
	

	public ChartData(double value, LocalDate date) {
		this.value = value;
		this.date = date;
	}



	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "[value=" + value + ", date=" + date + "]";
	}
}
