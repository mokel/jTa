package fr.mokel.trade.gui.controller;

import fr.mokel.trade.model.Chart;

public class IndicatorModel {

	private Integer state;
	private String name;
	private String entryPoint;
	private String exitPoint;
	
	private Chart indicator;
	private Chart stock;
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEntryPoint() {
		return entryPoint;
	}
	public void setEntryPoint(String entryPoint) {
		this.entryPoint = entryPoint;
	}
	public String getExitPoint() {
		return exitPoint;
	}
	public void setExitPoint(String exitPoint) {
		this.exitPoint = exitPoint;
	}
	public Chart getIndicator() {
		return indicator;
	}
	public void setIndicator(Chart indicator) {
		this.indicator = indicator;
	}
	public Chart getStock() {
		return stock;
	}
	public void setStock(Chart stock) {
		this.stock = stock;
	}
	
	
}
