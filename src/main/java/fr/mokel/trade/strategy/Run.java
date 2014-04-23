package fr.mokel.trade.strategy;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import fr.mokel.trade.data.MarketDataRetrieverImpl;
import fr.mokel.trade.gui.graph.PerformanceManager;
import fr.mokel.trade.model.DayValue;
import fr.mokel.trade.model.Stock;
import fr.mokel.trade.model.WindowedList;

public class Run {

	public static void main(String[] args) {
		MarketDataRetrieverImpl m = new MarketDataRetrieverImpl();
		WindowedList c = m.getDayData("DSY.PA", LocalDate.now());
		Stock aca = new Stock("BNP.PA");
		aca.setList(c);
		PerformanceManager pf = new PerformanceManager();
		//for (int small = 1; small <= 50; small++) {
		for (int small = 4; small <= 6; small++) {
			//for (int big = small + 3; big <= 200; big++) {
				StrategyParamters p = createParams(small);
				BackTestResult res = backtest(aca, p);
				System.out.println(res);
				int i = 0;
				if (res.totalPerfo > 0d && res.nbPositif > res.nbNegatif) {
					//System.out.println(res.toCsv());
					pf.addPlot(res.getPlot(), ""+i++);
				}
		}
		JFrame frame = new JFrame();
		frame.add(pf);
		frame.setVisible(true);
	}

	private static StrategyParamters createParams(int...arg ) {
		CciParamters p = new CciParamters();
		p.period = arg[0];
		return p;
	}

	private static BackTestResult backtest(Stock aca, StrategyParamters p) {
		CciStrategy s = new CciStrategy();
		s.setParameters(p);
		BackTestResult res = new BackTestResult(s.getParameters());
		List<Trade> trades = s.process(aca);
		res.computeInfos(trades);
		return res;
	}

	static class BackTestResult {
		List<Trade> trades;
		List<DayValue> perfos = new ArrayList<DayValue>();
		double totalPerfo;
		double variance;
		int nbPositif;
		int nbNegatif;
		SummaryStatistics stats = new SummaryStatistics();
		StrategyParamters params;

		public BackTestResult(StrategyParamters parameters) {
			params = parameters;
		}

		public String toCsv() {
			StringBuilder sb = new StringBuilder("DATE\tPERF\t").append(System.lineSeparator());
			NumberFormat f = DecimalFormat.getNumberInstance();
			for (Trade t : trades) {
				sb.append(t.getEntry().getDate()).append("\t").append(f.format(t.getEntry().getValue())).append(System.lineSeparator());
			}
			for (Trade t : trades) {
				  sb.append(t.getExit().getDate()).append("\t").append(f.format(t.getExit().getValue())).append(System.lineSeparator());
			}
			for (Trade t : trades) {
				  sb.append(t.getExit().getDate()).append("\t").append(f.format(t.getPerformance())).append(System.lineSeparator());
			}
			return sb.toString();
		}
		
		public List<DayValue> getPlot() {
			List<DayValue> res = new ArrayList<DayValue>();
			for (Trade t : trades) {
				res.add(t.getPerfoAsDayValue());
		}
			return res;
		}

		public void computeInfos(List<Trade> trades) {
			this.trades = trades;
			totalPerfo = 1;
			for (Trade t : trades) {
					double perfo = t.getPerformance();
					perfos.add(new DayValue(perfo, t.getExit().getDate()));
					totalPerfo *= perfo;
					stats.addValue(perfo);
					if(perfo > 1) {
						nbPositif++;
					} else {
						nbNegatif++;
					}
			}
			// if(totalPerfo > 1)
			// System.out.println("########## "+params.getShortSMALength() +
			// " - "+params.getLongSMALength() + " total " + totalPerfo +
			// " signals "+ signals.size());

		}

		@Override
		public String toString() {
			return new StringBuilder().append(params).append(" perf: ")
					.append(totalPerfo)
					.append(" ecarttype: ").append(stats.getStandardDeviation())
					.append(" max: ").append(stats.getMax())
					.append(" min: ").append(stats.getMin())
					.append(" length: ").append(trades.size())
					.append(" nb+: ").append(nbPositif)
					.append(" nb-: ").append(nbNegatif).toString();
		}
	}

}
