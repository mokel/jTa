package fr.mokel.trade.gui2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXTextField;

import fr.mokel.trade.data.YahooDataRetriever;
import fr.mokel.trade.gui2.components.IndicatorPanel;
import fr.mokel.trade.model.DayValue;

/**
 * @author vincent.mokel
 *
 */
public class MainPanel extends JPanel {

	private JXTextField stockField;
	private GraphViewer graph = new GraphViewer();
	private IndicatorPanel indicPanel = new IndicatorPanel();

	public MainPanel() {
		setLayout(new MigLayout());
		stockField = new JXTextField("yahoo code");
		stockField.addActionListener(new StockListener());
		add(new JLabel("Yahoo stock code:"));
		add(stockField, "wrap");
		add(indicPanel, "wrap, span 2");
		add(graph, "dock south");
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
			List<DayValue> list = (List<DayValue>) value;
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					graph.setStock(list);
				}
			});
		}
		
	}
}
