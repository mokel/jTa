package fr.mokel.trade.model;

import java.util.Observable;


public class Stock extends Observable {

	private String code;
	private Chart barChart;
	private DataWindow data;
	private WindowedList list;
//	
//	private List<Indicator> strategies;
//	private Integer status;

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

	public DataWindow getData() {
		return data;
	}

	public void setData(DataWindow data) {
		this.data = data;
	}

	public WindowedList getList() {
		return list;
	}

	public void setList(WindowedList list) {
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
