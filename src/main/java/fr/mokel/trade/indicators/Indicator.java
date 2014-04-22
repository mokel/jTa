package fr.mokel.trade.indicators;


import java.time.LocalDate;

import fr.mokel.trade.model.Chart;

public interface Indicator {

	String getName();
	
	void processData(Chart data);// throws IndicatorException;
	
	boolean isEntryPoint();
	boolean isEntryPoint(LocalDate date);
	String entryPointText();
	
	boolean isExitPoint();
	boolean isExitPoint(LocalDate date);
	String exitPointText();
	
	boolean isGoodPosition();
	
	boolean isBadPosition();

	Integer getState();

	Chart getChart();
	
	IndicatorType getType();

	boolean checkDataLength(Chart data);
	boolean checkDateAvailable(LocalDate date);
	
}
