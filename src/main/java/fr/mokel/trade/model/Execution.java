package fr.mokel.trade.model;

import java.time.LocalDate;

public class Execution {

	private LocalDate date;
	private String code;
	private double price;
	private int quantity;
	private double fees;
	
	public Execution(LocalDate date, double price, int quantity,
			double fees) {
		super();
		this.date = date;
		this.price = price;
		this.quantity = quantity;
		this.fees = fees;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getFees() {
		return fees;
	}
	public void setFees(double fees) {
		this.fees = fees;
	}
	private double getNominal() {
		return quantity*price + (quantity/quantity)*fees;
	}
	@Override
	public String toString() {
		return "Execution [date=" + date + ", nominal=" + getNominal() + ", price="
				+ price + ", quantity=" + quantity + ", fees=" + fees + "]";
	}
	
	
}
