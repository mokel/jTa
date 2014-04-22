package fr.mokel.trade.gui;

import java.awt.GridBagConstraints;

public class Utils {

	public static GridBagConstraints getGbc(int x, int y) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		return gbc;
	}
}
