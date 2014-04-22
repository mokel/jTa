package fr.mokel.trade.gui.controller;

import fr.mokel.trade.model.IChartData;

public class BackTestPoint {

	private IChartData point;
	private boolean entry;
	
	public BackTestPoint(IChartData point, boolean entry) {
		this.point = point;
		this.entry = entry;
	}

	public IChartData getPoint() {
		return point;
	}


	public boolean isEntryPoint() {
		return entry;
	}

	public boolean isExitPoint() {
		return !entry;
	}
	

}
