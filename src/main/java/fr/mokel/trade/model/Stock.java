package fr.mokel.trade.model;

import java.util.List;
import java.util.Observable;


public class Stock extends Observable {

	private String code;
	private Chart barChart;
	private List<DayValue> list;


	public Stock(String code) {
		this.code = code;
	}

	public Chart getBarChart() {
		return barChart;
	}

	public void setBarChart(Chart barChart) {
		this.barChart = barChart;
		setChanged();
		notifyObservers("DATA");
	}
	
	

//	public List<Indicator> getStrategies() {
//		if(strategies == null) {
//			strategies = IndicatorFactory.createList();
//		}
//		return strategies;
//	}
//
//	public void setStrategies(List<Indicator> strategies) {
//		this.strategies = strategies;
//	}

//	public Integer getStatus() {
//		return status;
//	}
//
//	public void setStatus(Integer status) {
//		this.status = status;
//	}


	public List<DayValue> getList() {
		return list;
	}

	public void setList(List<DayValue> list) {
		this.list = list;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
//
//	public void runStrategies() {
//		strategies = IndicatorFactory.createList();
//		for (Indicator indicator : strategies) {
//			if(indicator.checkDataLength(barChart)) {
//				indicator.processData(barChart);
//			}
//		}
//		setChanged();
//		notifyObservers("COMPUTATION");
//	}


}
