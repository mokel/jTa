package fr.mokel.trade.gui2;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jdesktop.swingx.JXTextField;

import fr.mokel.trade.data.YahooDataRetriever;
import fr.mokel.trade.gui2.components.IndicatorPanel;
import fr.mokel.trade.gui2.util.ConstraintsBuilder;
import fr.mokel.trade.model.WindowedList;

/**
 * @author vincent.mokel
 *
 */
public class MainPanel extends JPanel {

	private JXTextField stockField;
	private GraphViewer graph = new GraphViewer();
	private IndicatorPanel indicPanel = new IndicatorPanel();
	public MainPanel() {
		setLayout(new GridBagLayout());
		stockField = new JXTextField("yahoo code");
		stockField.addActionListener(new StockListener());
		add(stockField, new ConstraintsBuilder(0,0).build());

		add(indicPanel,
				new ConstraintsBuilder(0, 1).fill(GridBagConstraints.HORIZONTAL)
						.gridwidth(GridBagConstraints.REMAINDER)
				.build());

		add(graph, new ConstraintsBuilder(0, 2).gridwidth(2).fill(GridBagConstraints.BOTH)
				.build());
	}
	
	class StockListener implements ActionListener, Observer {
		@Override
		public void actionPerformed(ActionEvent e) {
			JXTextField field = (JXTextField) e.getSource();
			String stock = field.getText();
			YahooDataRetriever m = new YahooDataRetriever();
			m.getDataAsync(stock, LocalDate.now(), this);
		}

		@Override
		public void update(Observable obs, Object value) {
			WindowedList list = (WindowedList) value;
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					graph.setStock(list.getUnderlyingList());
				}
			});
		}
		
	}
}
