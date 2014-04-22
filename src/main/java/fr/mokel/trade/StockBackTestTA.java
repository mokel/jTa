package fr.mokel.trade;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collection;

import fr.mokel.trade.gui.controller.Application;
import fr.mokel.trade.gui.controller.Stock;

public class StockBackTestTA {

	public static void main(String[] args) {
		Application app = new Application();
		app.setDateProcess(LocalDate.fromCalendarFields(Calendar.getInstance()));
		app.launchBackTestProcess();
		Collection<Stock> sL = app.getStocks();
		
	}
}
