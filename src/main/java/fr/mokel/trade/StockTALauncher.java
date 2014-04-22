package fr.mokel.trade;


import java.util.Date;

import fr.mokel.trade.gui.controller.Application;

public class StockTALauncher {

	public static void main(String[] args) {
		Application app = new Application();
		app.setDateProcess(new Date(2011, 02, 02));
		app.launchProcess();
	}
}
