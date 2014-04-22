package fr.mokel.trade.model;

public class Indicator implements SpotValue {

	private double value;
	private String date;
	
	public Indicator() {
	}
	
	

	public Indicator(double value, String date) {
		super();
		this.value = value;
		this.date = date;
	}



	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Indicator [value=" + value + ", date=" + date + "]";
	}

}
