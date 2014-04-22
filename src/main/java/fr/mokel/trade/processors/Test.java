package fr.mokel.trade.processors;

import fr.mokel.trade.data.IMarketDataRetriever;
import fr.mokel.trade.model.Chart;

public class Test {

	private IMarketDataRetriever ret;
	
	
	public void setRet(IMarketDataRetriever ret) {
		this.ret = ret;
	}
	
	
	public void printTest(String code){
		Chart data = null;//ret.getDayData(code);
		Opinion.printList(data);
		MagicTrendProcessor magic = new MagicTrendProcessor();
		Chart b = magic.process(data);
		Opinion.printList(b);
//		Opinion.printList(data);
//		System.out.println("############################################################");
//		System.out.println("############################################################");
//		System.out.println("############################################################");
//		SimpleMovingAverageProcessor sma = new SimpleMovingAverageProcessor();
//		sma.setLength(50);
//		Chart b = sma.process(data);
////		Opinion.printList(b);
//		System.out.println("############################################################");
//		System.out.println("############################################################");
//		System.out.println("############################################################");
//		CCIProcessor p = new CCIProcessor();
//		Chart a = p.process(data);
//		Opinion.printList(a);
//		System.out.println("############################################################");
//		System.out.println("############################################################");
//		System.out.println("############################################################");
	}
	
}
