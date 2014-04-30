package fr.mokel.trade.gui2;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.UIManager;

import fr.mokel.trade.gui2.util.ConstraintsBuilder;

public class MainFrame extends JFrame {

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		MainFrame app = new MainFrame();
		app.pack();
		app.setVisible(true);
	}
	
	public MainFrame() {
		setTitle("jTa");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridBagLayout());
		add(new MainPanel(), new ConstraintsBuilder().fill(GridBagConstraints.BOTH).build());
	}
	
}
