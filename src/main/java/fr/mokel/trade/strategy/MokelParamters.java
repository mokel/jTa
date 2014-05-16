package fr.mokel.trade.strategy;

public class MokelParamters extends StrategyParamters {

	private int shortSMALength;
	private int longSMALength;
	private int cciLength;
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
	public int getCciLength() {
		return cciLength;
	}
	public void setCciLength(int cciLength) {
		this.cciLength = cciLength;
	}
	@Override
	public String toString() {
		return "MokelParamters [shortSMALength=" + shortSMALength + ", longSMALength="
				+ longSMALength + ", cciLength=" + cciLength + "]";
	}
}
