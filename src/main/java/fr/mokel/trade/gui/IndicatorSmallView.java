package fr.mokel.trade.gui;

import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.mokel.trade.gui.controller.IndicatorModel;

public class IndicatorSmallView extends JPanel {

	private String name;
	private Light status;
	
	public IndicatorSmallView(String indic) {
		setLayout(new GridBagLayout());
		name = indic;
		JLabel label = new JLabel(indic);
		add(label, Utils.getGbc(0, 0));
		
		status = new Light();
		add(status, Utils.getGbc(1, 0));
	}
	
	public String getIndicatorKey() {
		return name;
	}

	public void setModel(IndicatorModel model) {
		status.setState(model.getState());
	}

	@Override
	public String toString() {
		return "IndicatorSmallView [" + name + "]";
	}
	
}
