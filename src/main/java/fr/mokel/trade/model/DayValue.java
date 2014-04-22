package fr.mokel.trade.model;

import java.time.LocalDate;

public class DayValue {

	private double value;
	private LocalDate date;
	private double high;
	private double low;
	private double close;

	public DayValue() {
	}

	public DayValue(double value, LocalDate date) {
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

	@Override
	public String toString() {
		return "[value=" + value + ", date=" + date + "]";
	}

}
