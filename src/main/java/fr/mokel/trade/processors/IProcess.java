package fr.mokel.trade.processors;

import fr.mokel.trade.model.Chart;

public interface IProcess {

	Chart process(Chart data);
	
	void setNormalParameters();
	
	boolean increaseParameters();
}
