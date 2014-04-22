package fr.mokel.trade.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.mokel.trade.gui.controller.Stock;

public class StockView extends JPanel implements Observer {

	private String code;
	private Light light;
	
	public StockView(Stock stock) {
		stock.addObserver(this);
		setLayout(new GridBagLayout());
		code = stock.getCode();
		JLabel jLabel = new JLabel(code);
		jLabel.setPreferredSize(new Dimension(50,20));
		GridBagConstraints gbcLbl = new GridBagConstraints();
		gbcLbl.gridx=0;
		gbcLbl.gridy=0;
		add(jLabel, gbcLbl);
		
		light = new Light();
		light.setGreen(false);
		GridBagConstraints gbcLight = new GridBagConstraints();
		gbcLight.gridx = 1;
		gbcLight.gridy = 0;
		add(light, gbcLight);
	}

	public void update(Observable o, Object arg) {
		if("DATA".equals(arg)) {
			light.setGray(true);
		}
		if("COMPUTATION".equals(arg)) {
			light.setGreen(true);
		}
	}
	
	@Override
	public String toString() {
		return "StockView ["+ code +"]";
	}
	
	public String getStockKey() {
		return code;
	}
}

