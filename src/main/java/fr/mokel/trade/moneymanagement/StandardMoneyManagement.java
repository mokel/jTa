package fr.mokel.trade.moneymanagement;

import java.util.ArrayList;
import java.util.List;

import fr.mokel.trade.model.BackTest;
import fr.mokel.trade.model.Chart;
import fr.mokel.trade.model.ChartData;
import fr.mokel.trade.model.Execution;
import fr.mokel.trade.model.IChartData;
import fr.mokel.trade.model.Signal;
import fr.mokel.trade.model.Stock;

public class StandardMoneyManagement extends MoneyManagement {

	private static double INITIAL_CASH = 2550;
	private static double MINIMAL_NOMINAL = 2500;
	private static double EXEC_FEES = 0.0049;

	private static boolean WITH_FEES = false;
	
	private double cash = INITIAL_CASH;
	private int quantity = 0;
	private List<Execution> execs;
	private Chart account;
	
	private List<Signal> signals;
	private Chart stock;
	
	@Override
	public BackTest applySignals(List<Signal> signs, Stock s) {
		stock = s.getBarChart();
		signals = signs;
		execs = new ArrayList<Execution>();
		account = new Chart();
		account.add(new ChartData(cash, s.getBarChart().getDate(0)));
		processIndicatorSignals();
		BackTest test = new BackTest(s);
		test.setExecx(execs);
		test.setPortfolioChart(account);
		return test;
	}

	
	private void processIndicatorSignals() {
		for(int i = 0; i < stock.size(); i++) {
			IChartData bar = stock.get(i);
			if (signals.contains(bar)) {
				Signal s = signals.get(signals.indexOf(bar));
				if(s.isEntryPoint()) {
					tryBuy(s.getPoint());
				} else {
					trySell(s.getPoint());
				}
			}
			actualizePF(bar);
		}
//		trySell(lastSpot);
	}

	private void actualizePF(IChartData bar) {
		ChartData newDay = new ChartData();
		newDay.setDate(bar.getDate());
		newDay.setValue(estimatePF(bar));
		account.add(newDay);
	}

	private double estimatePF(IChartData bar) {
		double res = cash;
		if (quantity > 0) {
			double nominal = quantity*bar.getValue();
			double fees = 0;
			if(WITH_FEES) {
				fees = nominal*EXEC_FEES;
			}
			res += (nominal-fees);
		}
		return res;
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
			execs.add(exec);
//		}
	}
	
	private int getNbPosAvailable() {
		return (int) ((cash- cash*EXEC_FEES*(WITH_FEES?1:0))/MINIMAL_NOMINAL);
	}
}
