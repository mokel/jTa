package fr.mokel.trade.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import fr.mokel.trade.gui.controller.Application;
import fr.mokel.trade.gui.controller.IndicatorModel;

public class IndicatorsPanelView extends JPanel {

	private IndicatorView indicatorView;
	private IndicatorsSmallPanelView smallIndicators;
	private IndicatorChartView chartView;
	
	private Map<String, IndicatorModel> indicatorModels;

	public IndicatorsPanelView(Application controller) {
		super();
		setLayout(new MigLayout());
		smallIndicators = new IndicatorsSmallPanelView();
		smallIndicators.addMouseListener(new ClickIndicatorListener());
		add(smallIndicators, "west");
		
		chartView = new IndicatorChartView();
		add(chartView, "east");
		
		indicatorView = new IndicatorView();
		add(indicatorView, "east");
		
	}

	public void setModels(Map<String, IndicatorModel> map) {
		indicatorModels = map;
		smallIndicators.setModel(indicatorModels);
	}


	public void setDimensions() {
		// setPreferredSize(new Dimension(400,400));
		// setMaximumSize(new Dimension(400,400));
		// setMinimumSize(new Dimension(400,400));
	}

	class ClickIndicatorListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() instanceof IndicatorSmallView) {
				IndicatorSmallView view = (IndicatorSmallView) e.getSource();
				System.out.println("Indicator selected:" + view);
				IndicatorModel model = indicatorModels.get(view.getIndicatorKey());
				indicatorView.setModel(model);
				chartView.setModel(model.getStock(), model.getIndicator());
			}
		}
	}

}
