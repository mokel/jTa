package fr.mokel.trade.indicator2;

import fr.mokel.trade.functions.Average;
import fr.mokel.trade.functions.TypicalPrice;
import fr.mokel.trade.model.Chart;
import fr.mokel.trade.model.ChartData;
import fr.mokel.trade.model.DayValue;
import fr.mokel.trade.model.WindowedList;

public class CciIndicator {

	
	public DayValue process(WindowedList list, int length) {
		
		if (averageLarge > list.size()) {
			throw new IllegalArgumentException("averageLarge > window.size()");
		}		if (averageSmall > averageLarge) {
			throw new IllegalArgumentException("averageSmall > averageLarge");
		}
		
		TypicalPrice tp = new  TypicalPrice();
		for (DayValue dayValue : list) {
			
		}
		DayValue typicalPrice = tp.process(list);
		
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
		
		
		Average function = new Average();
		DayValue smallAvg = function.process(list.subWindowFrom(list.size()-averageSmall));
		DayValue largeAvg = function.process(list);
		double value = smallAvg.getValue() - largeAvg.getValue();
		DayValue dv = new DayValue(value, largeAvg.getDate());
		return dv;
	}

	
}
