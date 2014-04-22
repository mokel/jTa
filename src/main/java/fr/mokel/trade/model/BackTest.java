package fr.mokel.trade.model;

import java.util.List;



public class BackTest {
	
	private Stock stock;
	private List<Execution> execx;
	private Chart portfolioChart;
	
	public BackTest(Stock s) {
		stock = s;
	}

	public List<Execution> getExecx() {
		return execx;
	}

	public void setExecx(List<Execution> execx) {
		this.execx = execx;
	}

	public Chart getPortfolioChart() {
		return portfolioChart;
	}

	public void setPortfolioChart(Chart portfolioChart) {
		this.portfolioChart = portfolioChart;
	}
	
	
}
