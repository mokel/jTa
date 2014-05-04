package fr.mokel.trade.gui2.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.mokel.trade.gui2.util.ConstraintsBuilder;

public class IndicatorPanel extends JPanel {

	JComboBox<String> indicatorCombo;

	JButton addButton = new JButton("add");
	public IndicatorPanel() {
		setLayout(new GridBagLayout());
		indicatorCombo = new JComboBox<String>();
		DefaultComboBoxModel<String> indicModel = new DefaultComboBoxModel<String>();
		indicModel.addElement("MMA");
		indicModel.addElement("EMMA");
		indicatorCombo.setModel(indicModel);
		add(new JLabel("Indicators: "), new ConstraintsBuilder(0, 0).build());
		add(indicatorCombo, new ConstraintsBuilder(1, 0).build());
		add(addButton,
				new ConstraintsBuilder(0, 1).anchor(GridBagConstraints.WEST)
						.build());
	}
}
