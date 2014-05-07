package fr.mokel.trade.gui2.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.mokel.trade.gui2.util.ConstraintsBuilder;
import fr.mokel.trade.gui2.util.EventManager;
import fr.mokel.trade.gui2.util.EventManager.EventType;
import fr.mokel.trade.gui2.util.FormPanel;
import fr.mokel.trade.indicator2.IndicatorType;

public class IndicatorPanel extends JPanel {

	JComboBox<IndicatorType> indicatorCombo;

	JButton addButton = new JButton("add");

	FormPanel paramsPanel;
	GridBagConstraints paramsPanelConstraints = new ConstraintsBuilder(2, 0, true).fillBoth()
			.gridwidth(GridBagConstraints.REMAINDER).build();

	public IndicatorPanel() {
		setLayout(new GridBagLayout());
		indicatorCombo = new JComboBox<IndicatorType>();
		DefaultComboBoxModel<IndicatorType> indicModel = new DefaultComboBoxModel<IndicatorType>();
		indicModel.addElement(IndicatorType.SMA);
		indicModel.addElement(IndicatorType.CMA);
		indicatorCombo.setModel(indicModel);
		add(new JLabel("Indicators: "), new ConstraintsBuilder(0, 0, true).build());
		add(indicatorCombo, new ConstraintsBuilder(1, 0, true).build());
		add(addButton, new ConstraintsBuilder(0, 1, true).anchor(GridBagConstraints.WEST).build());
		indicatorCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadParamsPanel((IndicatorType) indicatorCombo.getSelectedItem());
			}
		});
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object params = paramsPanel.getObject();
				EventManager.fireEvent(EventType.IndicatorAdded, params);
			}
		});
		// add dummy panel
		paramsPanel = new FormPanel();
		paramsPanel.setBackground(Color.RED);
		add(paramsPanel, paramsPanelConstraints);
		setBackground(Color.ORANGE);
		paramsPanel.setPreferredSize(new Dimension(50, 50));
	}

	private void loadParamsPanel(IndicatorType selectedIndic) {
		if (paramsPanel != null) {
			remove(paramsPanel);
		}
		paramsPanel = new FormPanel(selectedIndic.getClazz());
		add(paramsPanel, paramsPanelConstraints);
		validate();
	}
}
