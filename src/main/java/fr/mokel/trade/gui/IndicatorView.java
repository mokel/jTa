package fr.mokel.trade.gui;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import fr.mokel.trade.gui.controller.IndicatorModel;

public class IndicatorView extends JPanel {

	private IndicatorModel model;
	
	private IndicatorPartView entryPoint;
	private IndicatorPartView exitPoint;
	
	public IndicatorView() {
		setLayout(new MigLayout());
		
		entryPoint = new IndicatorPartView("Entry Point:");
		add(entryPoint, "wrap");
		
		exitPoint = new IndicatorPartView("Exit Point:");
		add(exitPoint, "wrap");
	}
	
	public void setModel(IndicatorModel amodel) {
		this.model = amodel;
		entryPoint.setModel(model.getEntryPoint());
		exitPoint.setModel(model.getExitPoint());
	}
}
