package fr.mokel.trade.gui;

import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.UIManager;

import fr.mokel.trade.gui.controller.Application;
import fr.mokel.trade.gui.controller.Stock;

public class MainFrame {

	private static JFrame application;
	private static Properties props;
	
	public static final int FRAME_WIDTH = 800;
	public static final int FRAME_HEIGTH = 400;
	
	public static void main(String[] args) {
		loadProperties();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e2) {
            e2.printStackTrace();
        }
		//Create model
		Application app = new Application();
		app.setDateProcess(new Date());
		String stockString = props.getProperty("stocks");
		String[] stocks = stockString.split(",");
		List<Stock> stockList = new ArrayList<Stock>();
		for (String stock : stocks) {
			Stock s = new Stock(stock);
			stockList.add(s);
		}
//		app.setStocks(stockList);
		
		//Create GUI
		ApplicationView view = new ApplicationView(app);

		application = new JFrame();
		application.add(view);
		application.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGTH));
//		application.pack();
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		application.setVisible(true);
	}
	
	public static void pack() {
//		application.pack();
	}
	
	private static void loadProperties() {
        try {
        	props = new Properties();
            props.load(ClassLoader.getSystemResourceAsStream("app.properties"));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
