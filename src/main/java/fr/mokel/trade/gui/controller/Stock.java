package fr.mokel.trade.gui.controller;

import java.util.List;
import java.util.Observable;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;

import fr.mokel.trade.indicators.Indicator;
import fr.mokel.trade.indicators.IndicatorException;
import fr.mokel.trade.indicators.IndicatorFactory;
import fr.mokel.trade.model.Chart;

public class Stock extends Observable {

	private String code;
	private Chart barChart;
	private List<Indicator> strategies;
	private Integer status;
	private Logger logger = Logger.getLogger(getClass());

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

	public List<Indicator> getStrategies() {
		if(strategies == null) {
			strategies = IndicatorFactory.createList();
		}
		return strategies;
	}

	public void setStrategies(List<Indicator> strategies) {
		this.strategies = strategies;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void runStrategies() {
		strategies = IndicatorFactory.createList();
		for (Indicator indicator : strategies) {
			if(indicator.checkDataLength(barChart)) {
				indicator.processData(barChart);
			}
		}
		setChanged();
		notifyObservers("COMPUTATION");
	}


}
