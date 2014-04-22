package fr.mokel.trade.strategy;

import java.time.Period;

import fr.mokel.trade.model.DayValue;

public class Trade {

	private DayValue entry;
	private DayValue exit;

	public Trade(DayValue entry, DayValue exit) {
		super();
		this.entry = entry;
		this.exit = exit;
	}

	public double getPerformance() {
		return (exit.getValue()/entry.getValue())-0.008;
	}
	
	public Period getPeriod() {
		//until is exclusiv
		return entry.getDate().until(exit.getDate().plusDays(1));
	}

	public DayValue getEntry() {
		return entry;
	}

	public DayValue getExit() {
		return exit;
	}

	
	
}
