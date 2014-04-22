package fr.mokel.trade.strategy;


public class StrategyFactory {

	public static Strategy create(StrategyType type) {
		switch (type) {
		case MACD:
			return new MACDStrategy();
		default:
			break;
		}
		return null;
	}

}
