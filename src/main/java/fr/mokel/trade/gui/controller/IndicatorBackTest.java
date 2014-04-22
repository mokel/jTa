package fr.mokel.trade.gui.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import fr.mokel.trade.indicators.IndicatorType;
import fr.mokel.trade.model.IChartData;

public class IndicatorBackTest {
	
	private static double INITIAL_CASH = 2550;
	private static double MINIMAL_NOMINAL = 2500;
	private static double EXEC_FEES = 0.0049;

	private static boolean WITH_FEES = false;
	
	private double cash = INITIAL_CASH;
	private int quantity = 0;
	private List<Execution> execs;
	
	private List<BackTestPoint> points;
	private IndicatorType type;
	
	private static Logger logger = Logger.getLogger(IndicatorBackTest.class);
	
	public IndicatorBackTest(IndicatorType indicatorType) {
		type = indicatorType;
		points = new ArrayList<BackTestPoint>();
		execs = new ArrayList<Execution>();
	}
	
	public IndicatorType getType() {
		return type;
	}

	public List<BackTestPoint> getPoints() {
		return points;
	}

	public void addExitPoint(IChartData value) {
		points.add(new BackTestPoint(value, false));
	}

	public void addEntryPoint(IChartData value) {
		points.add(new BackTestPoint(value, true));
	}
	
	public double processCalcul(IChartData lastSpot) {
		for(int i = points.size() -1; i>=0; i--) {
			if(points.get(i).isEntryPoint()) {
				tryBuy(points.get(i).getPoint());
			} else {
				trySell(points.get(i).getPoint());
			}
		}
		trySell(lastSpot);
		return cash;
	}

	private void trySell(IChartData point) {
		if(quantity > 0) {
			double nominal = quantity*point.getValue();
			double fees = 0;
			if(WITH_FEES) {
				fees = nominal*EXEC_FEES;
			}
			cash += (nominal-fees);
			Execution exec = new Execution(point.getDate(), point.getValue(), quantity, fees);
			execs.add(exec);
			logger.debug(exec);
			quantity = 0;
		}
	}

	private void tryBuy(IChartData point) {
//		if(getNbPosAvailable() > 0) {
			double nominal = 0;
			if(getNbPosAvailable() < 2) {
				nominal = cash - cash*EXEC_FEES*(WITH_FEES ? 1:0);
			} else {
				nominal = MINIMAL_NOMINAL;
			}
			int qty = (int) (nominal / point.getValue());
			nominal = qty*point.getValue();
			double fees = 0;
			if(WITH_FEES) {
				fees = nominal*EXEC_FEES;
			}
			cash -= nominal;
			quantity += qty;
			Execution exec = new Execution(point.getDate(), point.getValue(), qty*-1, fees);
			logger.debug(exec);
			execs.add(exec);
//		}
	}
	
	private int getNbPosAvailable() {
		return (int) ((cash- cash*EXEC_FEES*(WITH_FEES?1:0))/MINIMAL_NOMINAL);
	}

	
	public List<Execution> getExecs() {
		return execs;
	}

	@Override
	public String toString() {
		return "IndicatorBackTest [cash=" + cash + ", execs=" + execs.size() + ", type=" + type	+ "]";
	}
	
	
}
