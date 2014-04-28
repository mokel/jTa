package fr.mokel.trade.gui2;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.lang.time.DateUtils;
import org.jdesktop.swingx.JXDatePicker;

import fr.mokel.trade.gui2.util.ConstraintsBuilder;
import fr.mokel.trade.model.DayValue;

public class GraphViewer extends JPanel {

	private PerformanceChart chart;

	private JXDatePicker fromDatePicker = new JXDatePicker();
	private JXDatePicker toDatePicker = new JXDatePicker();
	
	public GraphViewer() {
		setLayout(new GridBagLayout());
		fromDatePicker.addMouseWheelListener(new MouseWheelDateListener());
		fromDatePicker.addActionListener(new ActionDateListener());
		toDatePicker.addMouseWheelListener(new MouseWheelDateListener());
		add(new JLabel("From: "), new ConstraintsBuilder(0,0).build());
		add(fromDatePicker, new ConstraintsBuilder(1,0).build());
		add(new JLabel("To: "), new ConstraintsBuilder(0,1).build());
		add(toDatePicker, new ConstraintsBuilder(1,1).build());
		chart = new PerformanceChart();
		add(chart, new ConstraintsBuilder(0,2).fill(GridBagConstraints.BOTH).gridwidth(2).build());
	}

	private void updateChartRange() {
		Date fromDate = fromDatePicker.getDate();
		Date toDate = toDatePicker.getDate();
		chart.setRange(fromDate, toDate);
	}
	public void setStock(List<DayValue> stockValues) {
		fromDatePicker.setDate(stockValues.get(0).getJavaUtilDate());
		toDatePicker.setDate(stockValues.get(stockValues.size()-1).getJavaUtilDate());
		chart.setStock(stockValues);
	}
	class ActionDateListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			updateChartRange();
		}
	}
	class MouseWheelDateListener implements MouseWheelListener {

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			JXDatePicker source = (JXDatePicker) e.getSource();
			if(e.getWheelRotation() != 0) {
				Date date = source.getDate();
				date = DateUtils.addDays(date, e.getWheelRotation());
				source.setDate(date);
				updateChartRange();
			}
		}
		
	}
}
