package fr.mokel.trade.strategy;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import fr.mokel.trade.data.YahooDataRetriever;
import fr.mokel.trade.model.DayValue;
import fr.mokel.trade.model.Stock;

public class Run {
//http://trac.erichseifert.de/gral/wiki
//http://stackoverflow.com/questions/7206542/jfreechart-interactive-chart-editing-tranforming-mouse-coordinates-into-series
//http://stackoverflow.com/questions/6337851/jfreechart-general-issue-on-the-possibility-of-interactlively-modify-a-displayed
	public static void main(String[] args) {
		YahooDataRetriever m = new YahooDataRetriever();
		List<DayValue> c = m.getData("BNP.PA", LocalDate.now());
		Stock aca = new Stock("BNP.PA");
		aca.setList(c);
		// for (int small = 1; small <= 50; small++) {
//		for (int small = 4; small <= 6; small++) {
			// for (int big = small + 3; big <= 200; big++) {
		StrategyParamters p = createParams(5, 5, 5);// (20,50);
			BackTestResult res = backtest(aca, p);
			System.out.println(res);
		// if (res.totalPerfo > 0d && res.nbPositif > res.nbNegatif) {
				 System.out.println(res.toCsv());
		// }
	//	}

	}

	private static StrategyParamters createParams(int... arg) {
		MokelParamters p = new MokelParamters();
		p.setCciLength(arg[0]);
		// CrossMovingAvgParamters p = new CrossMovingAvgParamters();
		p.setShortSMALength(arg[1]);
		p.setLongSMALength(arg[2]);
		return p;
	}

	private static BackTestResult backtest(Stock stock, StrategyParamters p) {
		MokelStrategy s = new MokelStrategy();
		// CrossMovingAvgStrategy s = new CrossMovingAvgStrategy();
		s.setParameters(p);
		BackTestResult res = new BackTestResult(stock, s.getParameters());
		List<Trade> trades = s.process(stock);
		res.computeInfos(trades);
		return res;
	}

	public static class BackTestResult {
		List<Trade> trades;
		List<DayValue> perfos = new ArrayList<DayValue>();
		List<DayValue> totalPerfos = new ArrayList<DayValue>();
		double totalPerfo;
		double variance;
		int nbPositif;
		int nbNegatif;
		Stock stock;
		SummaryStatistics stats = new SummaryStatistics();
		StrategyParamters params;

		public BackTestResult(Stock s, StrategyParamters parameters) {
			stock = s;
			params = parameters;
		}

		public String toCsv() {
			StringBuilder sb = new StringBuilder("DATE\tPERF\t").append(System
					.lineSeparator());
			NumberFormat f = DecimalFormat.getNumberInstance();
			for (Trade t : trades) {
				sb.append("Entry: ").append(t.getEntry().getDate()).append("\t")
						.append(f.format(t.getEntry().getValue())).append("\t")
						.append(" - length: ")
						.append(Period.between(t.getEntry().getDate(), t.getExit().getDate())
								.getDays() + 1)
						.append("\t")
						.append(" brut: ")
						.append(f.format(t.getExit().getValue() - t.getEntry().getValue()))
						.append("\t")
						.append(" perf: ").append(f.format(t.getPerformance())).append("\t")
						.append(System.lineSeparator());
			}
			return sb.toString();
		}

		public List<DayValue> getPerformanceValues() {
			return perfos;
		}		
		public List<DayValue> getTotalPerformanceValues() {
			return totalPerfos;
		}

		public List<DayValue> getStockValues() {
			return stock.getList();
		}

		public void computeInfos(List<Trade> trades) {
			this.trades = trades;
			totalPerfo = 1;
			if (!trades.isEmpty()) {
				totalPerfos.add(new DayValue(totalPerfo, trades.get(0).getEntry().getDate()));
				for (Trade t : trades) {
					double perfo = t.getPerformance();
					LocalDate exitDate =t.getExit().getDate();
					perfos.add(new DayValue(perfo, exitDate));
					totalPerfo *= perfo;
					totalPerfos.add(new DayValue(totalPerfo, exitDate));
					stats.addValue(perfo);
					if (perfo > 1) {
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
		}

		@Override
		public String toString() {
			return new StringBuilder().append(params).append(" perf: ")
					.append(totalPerfo).append(" ecarttype: ")
					.append(stats.getStandardDeviation()).append(" max: ")
					.append(stats.getMax()).append(" min: ")
					.append(stats.getMin()).append(" length: ")
					.append(trades.size()).append(" nb+: ").append(nbPositif)
					.append(" nb-: ").append(nbNegatif).toString();
		}
	}

}
