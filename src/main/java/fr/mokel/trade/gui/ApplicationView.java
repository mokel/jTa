package fr.mokel.trade.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import fr.mokel.trade.gui.controller.Application;

public class ApplicationView extends JPanel {

	private Application controller;

	private StockListView stockList;
	private IndicatorsPanelView stratPanel;
	
	private JButton load;
	private JButton compute;

	public ApplicationView(Application controller) {
		super();
		this.controller = controller;
		setLayout(new MigLayout());
		loadGui();
	}

	private void loadGui() {
		load = new JButton("Load");
		load.addActionListener(new LoadListener());
		GridBagConstraints gbcLoad = new GridBagConstraints();
		gbcLoad.gridx = 0;
		gbcLoad.gridy = 0;
		add(load, "wrap");
		
		compute = new JButton("Compute");
		compute.addActionListener(new ComputeListener());
		add(compute, "wrap");
		
		stockList = new StockListView(controller);
		stockList.addMouseListener(new ClickStockListener());
		add(stockList, "wrap");
		stratPanel = new IndicatorsPanelView(controller);
//		stratPanel.addMouseListener(new ClickStockListener());
		GridBagConstraints gbcStrat = Utils.getGbc(1, 0);
		gbcStrat.gridheight = GridBagConstraints.REMAINDER;
		add(stratPanel, "EAST");
	}

	class ClickStockListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() instanceof StockView) {
				StockView view = (StockView) e.getSource();
				System.out.println("Stock selected:" + view);
				stratPanel.setModels(controller.getIndicatorsModel(view.getStockKey()));
			}
		}
	}
	
	class LoadListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			controller.startDataDownload();
		}
	}
	
	class ComputeListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			controller.startDataComputation();
		}
	}
}
