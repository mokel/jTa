package fr.mokel.trade.gui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import fr.mokel.trade.gui.controller.IndicatorModel;

public class IndicatorsSmallPanelView extends JPanel {

	private Map<String, IndicatorSmallView> indicators;
	
	public IndicatorsSmallPanelView() {
		setLayout(new MigLayout());
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		createIndicatorList();
	}
	
	private void createIndicatorList() {
		indicators = new HashMap<String, IndicatorSmallView>();
		for (String indic : getIndicatorListModel()) {
			IndicatorSmallView small = new IndicatorSmallView(indic);
			indicators.put(indic, small);
			small.addMouseListener(new ForwardClickListener());
			add(small, "wrap");
		}
	}
	
	
	public String[] getIndicatorListModel() {
		return new String[]{"CCI", "MMA"};
	}

	public void setModel(Map<String, IndicatorModel> models) {
		for (Entry<String, IndicatorModel> model : models.entrySet()) {
			IndicatorSmallView view = indicators.get(model.getKey());
			view.setModel(model.getValue());
		}
	}
	
	class ForwardClickListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			//Send the event to the hierarchy
			processMouseEvent(e);
		}
	}
}
