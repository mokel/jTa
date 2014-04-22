package fr.mokel.trade.backtest;

import fr.mokel.trade.model.BarChartData;
import fr.mokel.trade.model.Chart;
import fr.mokel.trade.model.IChartData;

public abstract class AbstractBackTest {

	private int quantity = 0;
	private double cash = 3000;
	private int nbPose = 0;
	
	public final double EXEC_COSTS = 0.0049;
	
	public final void runBackTest(Chart data){
		initStrat();
		_runBackTest(data);
	}
	
	private void initStrat() {
		quantity = 0;
		cash = 3000;
		nbPose = 0;
		_initStrat();
	}
	abstract void _initStrat();

	abstract void _runBackTest(Chart data);

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public boolean isLong() {
		return quantity != 0;
	}
	
	public void sell(IChartData sellPoint, Chart data) {
		for (IChartData iChartData : data) {
			BarChartData bar = (BarChartData) iChartData;
			if(bar.getDate().equals(sellPoint.getDate())) {
				cash += (1-EXEC_COSTS)*quantity*bar.getAdjClose();
				quantity = 0;
//				System.out.println("SELL at: "+bar.getAdjClose() + " INDEX: " + sellPoint + " PF: "+getBalanceAccount(bar.getAdjClose()));
			}
		}
	}	
	
	public void buy(IChartData buyPoint, Chart data) {
		for (IChartData iChartData : data) {
			BarChartData bar = (BarChartData) iChartData;
			if(bar.getDate().equals(buyPoint.getDate())) {
				quantity =  (int) (cash / bar.getAdjClose());
				cash -= quantity * bar.getAdjClose()*(1+EXEC_COSTS);
				nbPose++;
//				System.out.println("BUY at: "+bar.getAdjClose() + " INDEX: " + buyPoint + " PF: "+getBalanceAccount(bar.getAdjClose()));
			}
		}
	}
	

	public void sellAll(BarChartData bar) {
		if(quantity != 0) {
			cash += quantity*bar.getAdjClose();
		}
		System.out.println("CLOSE STRAT: "+bar.getAdjClose() + " CASH: "+cash + " nbPose: " +nbPose +" Params: "+getParams());
	}
	
	private double getBalanceAccount(double close) {
		return cash + quantity*close;
	}
	
	abstract Object getParams();
	
}
