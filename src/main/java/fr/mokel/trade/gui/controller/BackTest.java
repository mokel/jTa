package fr.mokel.trade.gui.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import fr.mokel.trade.indicators.IndicatorType;

public class BackTest {
	
	private Map<IndicatorType, IndicatorBackTest> indicatorsBackTests;
	private Stock stock;
	
	public BackTest() {
		indicatorsBackTests = new HashMap<IndicatorType, IndicatorBackTest>();
	}

	public BackTest(Stock s) {
		stock = s;
		indicatorsBackTests = new HashMap<IndicatorType, IndicatorBackTest>();
	}

	public void add(IndicatorBackTest backTest) {
		indicatorsBackTests.put(backTest.getType(), backTest);
	}
	
	public Collection<IndicatorBackTest> getTests() {
		return indicatorsBackTests.values();
	}

	@Override
	public String toString() {
		return "BackTest [indicatorsBackTests=" + indicatorsBackTests + "]";
	}
	
	
	
}
