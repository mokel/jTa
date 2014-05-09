package fr.mokel.trade.gui2;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import fr.mokel.trade.gui2.util.ConstraintsBuilder;

public class MainFrame extends JFrame {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(
							UIManager.getSystemLookAndFeelClassName());
				} catch (Exception useDefault) {
				}
				INTANCE = new MainFrame();
				INTANCE.pack();
				INTANCE.setLocationByPlatform(true);
				INTANCE.setVisible(true);
			}
		});
	}
	
	public static MainFrame INTANCE;

	public MainFrame() {
		setTitle("jTa");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		add(new MainPanel(), new ConstraintsBuilder().fill(GridBagConstraints.BOTH).build());
	}
	
}
