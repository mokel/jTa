package fr.mokel.trade.gui.controller;

public class StockController {

	private String code;
	private State state = State.NONE;
	
	public StockController(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	
	
}
