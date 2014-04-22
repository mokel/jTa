package fr.mokel.trade.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import fr.mokel.trade.gui.controller.Application;
import fr.mokel.trade.gui.controller.Stock;

public class StockListView extends JPanel {

	private Map<String, StockView> stocks;
	
	private Application controller;
	
	public StockListView(Application controller) {
		this.controller = controller;
		setLayout(new MigLayout());
		setDimensions();
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		stocks = new HashMap<String, StockView>();
		for (Stock stock : controller.getStocks()) {
			StockView view = new StockView(stock);
			view.addMouseListener(new ForwardClickListener());
			stocks.put(stock.getCode(), view);
			add(view, "wrap");
		}
	}
	
	public void setDimensions() {
		 setPreferredSize(new Dimension(100,MainFrame.FRAME_HEIGTH));
		 setMaximumSize(new Dimension(100,MainFrame.FRAME_HEIGTH));
		 setMinimumSize(new Dimension(100,MainFrame.FRAME_HEIGTH));
	}
	
	class ForwardClickListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			//Send the event to the hierarchy
			processMouseEvent(e);
		}
	}
	
}
