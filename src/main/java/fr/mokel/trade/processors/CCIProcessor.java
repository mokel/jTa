package fr.mokel.trade.processors;

import fr.mokel.trade.model.Chart;
import fr.mokel.trade.model.ChartData;

public class CCIProcessor implements IProcess {

	/**
	 * The number of periods to average together.
	 */
	public static final int PERIODS_AVERAGE = 14;
	private int MIN_PERIODS_AVERAGE = 10;
	private int MAX_PERIODS_AVERAGE = 20;
	private int CURRENT_PERIODS_AVERAGE = MIN_PERIODS_AVERAGE;
	
	private TypicalPriceProcessor tpProcessor;
	private SimpleMovingAverageProcessor smaProcessor;
	public CCIProcessor() {
		tpProcessor = new TypicalPriceProcessor();
		smaProcessor = new SimpleMovingAverageProcessor();
		smaProcessor.setLength(CURRENT_PERIODS_AVERAGE);
	}
	
	public Chart process(Chart data) {
		
		Chart tpPrices = tpProcessor.process(data);
//		Opinion.printList(tpPrices);
		Chart smaTpPrices = smaProcessor.process(tpPrices);
//		Opinion.printList(smaTpPrices);
		//Mean deviation calcul
		
		Chart mdPrices = new Chart();
		for (int i = 0; i<smaTpPrices.size();i++) {
			double mean = 0;
			for (int j = i; j<CURRENT_PERIODS_AVERAGE +i;j++) {
				mean += Math.abs(smaTpPrices.get(i).getValue() - tpPrices.get(j).getValue());
			}
			mean = mean / CURRENT_PERIODS_AVERAGE;
			ChartData meanInd = new ChartData();
			meanInd.setValue(mean);
			meanInd.setDate(smaTpPrices.get(i).getDate());
			mdPrices.add(meanInd);
		}
//		Opinion.printList(mdPrices);
		//CCI CALCUL
		Chart cciPrices = new Chart();
		for (int i =0; i<mdPrices.size();i++) {
			double value = (tpPrices.get(i + CURRENT_PERIODS_AVERAGE -1).getValue() - smaTpPrices.get(i).getValue()) / (0.015d * mdPrices.get(i).getValue());
			ChartData cciInd = new ChartData();
			cciInd.setValue(value);
			cciInd.setDate(smaTpPrices.get(i).getDate());
			cciPrices.add(cciInd);
		}
		return cciPrices;
	}
	
	public void setNormalParameters() {
		CURRENT_PERIODS_AVERAGE = PERIODS_AVERAGE;
	}
	
	public Integer getParam(){
		return Integer.valueOf(CURRENT_PERIODS_AVERAGE);
	}
	
	public boolean increaseParameters() {
		if(CURRENT_PERIODS_AVERAGE < MAX_PERIODS_AVERAGE){
			CURRENT_PERIODS_AVERAGE++;
			smaProcessor.setLength(CURRENT_PERIODS_AVERAGE);
//			System.out.println("CCI PROCESSOR PARAM: period=" + CURRENT_PERIODS_AVERAGE);
			return true;
		}
		return false;
	}
	
}