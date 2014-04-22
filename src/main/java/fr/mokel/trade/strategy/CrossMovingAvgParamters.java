package fr.mokel.trade.strategy;

public class CrossMovingAvgParamters extends StrategyParamters {

	private int shortSMALength;
	private int longSMALength;
	public int getShortSMALength() {
		return shortSMALength;
	}
	public void setShortSMALength(int shortSMALength) {
		this.shortSMALength = shortSMALength;
	}
	public int getLongSMALength() {
		return longSMALength;
	}
	public void setLongSMALength(int longSMALength) {
		this.longSMALength = longSMALength;
	}
	@Override
	public String toString() {
		return "" + shortSMALength+ ", " + longSMALength;
	}
}
