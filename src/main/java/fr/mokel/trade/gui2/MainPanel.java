package fr.mokel.trade.gui2;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jdesktop.swingx.JXTextField;

import fr.mokel.trade.data.MarketDataRetrieverImpl;
import fr.mokel.trade.gui2.util.ConstraintsBuilder;
import fr.mokel.trade.model.WindowedList;

/**
 * @author vincent.mokel
 *
 */
public class MainPanel extends JPanel {

	private JXTextField stockField;
	private GraphViewer graph = new GraphViewer();
	public MainPanel() {
		setLayout(new GridBagLayout());
		stockField = new JXTextField("yahoo code");
		stockField.addActionListener(new StockListener());
		add(stockField, new ConstraintsBuilder(0,0).build());
		add(graph, new ConstraintsBuilder(0,1).fill(GridBagConstraints.BOTH).build());
	}
	
	class StockListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JXTextField field = (JXTextField) e.getSource();
			String stock = field.getText();
			new Thread(new Runnable() {
				@Override
				public void run() {
					MarketDataRetrieverImpl m = new MarketDataRetrieverImpl();
					final WindowedList list = m.getDayData(stock, LocalDate.now());
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							graph.setStock(list.getUnderlyingList());							
						}
					});
				}
			}).start();
		}
		
	}
}
