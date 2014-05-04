package fr.mokel.trade.gui.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import fr.mokel.trade.data.YahooDataRetriever;
import fr.mokel.trade.indicators.Indicator;
import fr.mokel.trade.indicators.IndicatorFactory;
import fr.mokel.trade.model.Chart;
import fr.mokel.trade.model.IChartData;

public class Application {

	private Map<String, Stock> stocks;
	private Map<String, BackTest> tests;
	
	static Logger logger = Logger.getLogger(Application.class);
	private static Properties props;
	
	private LocalDate dateProcess;
	
	public Application() {
		loadProperties();
		loadStockList();
	}
	
	public void launchProcess() {
		loadStockData();
		startComputation();
	}
	

	public void launchBackTestProcess() {
		loadStockData();
		startBackTestComputation();
	}

	private void startComputation() {
		for (Stock s : stocks.values()) {
			logger.info("Start computation of " + s.getCode());
			s.runStrategies();
		}
	}
	
	private void startBackTestComputation() {
		tests = new HashMap<String, BackTest>();
		for (Stock s : stocks.values()) {
			logger.info("Start computation of " + s.getCode());
			tests.put(s.getCode(), runBackTestStrategies(s));
		}
		logger.info(tests);
	}
	
	private BackTest runBackTestStrategies(Stock s) {
		BackTest test = new BackTest(s);
		for (Indicator indicator : s.getStrategies()) {
			if(indicator.checkDataLength(s.getBarChart())) {
				IndicatorBackTest backTest = new IndicatorBackTest(indicator.getType());
				indicator.processData(s.getBarChart());
				for (int i = s.getBarChart().size() - 1; i >= 0; i--) {
					LocalDate date = s.getBarChart().getDate(i);
					if(indicator.checkDateAvailable(date)) {
						testExitPoint(indicator, backTest, s.getBarChart().getData(date));
						testEntryPoint(indicator, backTest, s.getBarChart().getData(date));
					}
				}
				backTest.processCalcul(s.getBarChart().get(s.getBarChart().size()-1));
				logger.debug(backTest);
				test.add(backTest);
			}
		}
		return test;
	}
	private void testExitPoint(Indicator strat, IndicatorBackTest backTest, IChartData iChartData) {
		if (strat.isExitPoint(iChartData.getDate())) {
			backTest.addExitPoint(iChartData);
		}
	}

	private void testEntryPoint(Indicator strat, IndicatorBackTest backTest, IChartData iChartData) {
		if (strat.isEntryPoint(iChartData.getDate())) {
			backTest.addEntryPoint(iChartData);
		}
	}


	private void loadStockData() {
		YahooDataRetriever ret = new YahooDataRetriever();
		for (Stock s : stocks.values()) {
			logger.info("Start download of " + s.getCode());
			Chart c = ret.getData(s.getCode(), dateProcess);
			s.setBarChart(c);
		}
	}

	private void loadStockList() {
		String stockString = props.getProperty("stocks");
		String[] stocksTab = stockString.split(",");
		stocks = new HashMap<String, Stock>();
		for (String stock : stocksTab) {
			Stock s = new Stock(stock);
			stocks.put(stock, s);
		}
	}

	public Collection<Stock> getStocks() {
		return stocks.values();
	}

	public void startDataDownload() {
		Thread t = new BackgroundWorkerSpot();
		t.start();
	}
	
	class BackgroundWorkerSpot extends Thread {
		@Override
		public void run() {
			YahooDataRetriever ret = new YahooDataRetriever();
			for (Stock s : stocks.values()) {
				logger.info("Start download of " + s.getCode());
				Chart c = ret.getData(s.getCode(), dateProcess);
				s.setBarChart(c);
			}
		}
	}

	public void startDataComputation() {
		Thread t = new BackgroundWorkerComputation();
		t.start();
	}
	
	class BackgroundWorkerComputation extends Thread {
		@Override
		public void run() {
			for (Stock s : stocks.values()) {
				logger.info("Start computation of " + s.getCode());
				s.runStrategies();
			}
		}
	}
	
	private static void loadProperties() {
        try {
        	Properties logProps = new Properties();
        	logProps.load(ClassLoader.getSystemResourceAsStream("logger.properties"));
        	PropertyConfigurator.configure(logProps);
        	props = new Properties();
            props.load(ClassLoader.getSystemResourceAsStream("app.properties"));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public List<Indicator> getMockedModel() {
		return IndicatorFactory.createList();
	}

	public Map<String, IndicatorModel> getIndicatorsModel(String stockKey) {
		Map<String, IndicatorModel> indics = new HashMap<String, IndicatorModel>();
		Stock s = stocks.get(stockKey);
		if (s != null && s.getStrategies() != null) {
			for (Indicator strat : s.getStrategies()) {
				IndicatorModel model = new IndicatorModel();
				model.setName(strat.getName());
				model.setState(strat.getState());
				model.setEntryPoint(strat.entryPointText());
				model.setExitPoint(strat.exitPointText());
				model.setStock(s.getBarChart());
				model.setIndicator(strat.getChart());
				indics.put(model.getName(), model);
			}
		}
		return indics;
	}

	public LocalDate getDateProcess() {
		return dateProcess;
	}

	public void setDateProcess(LocalDate dateProcess) {
		this.dateProcess = dateProcess;
	}

	public void launchBackTest(int nbMonth) {
		
	}
		
}
