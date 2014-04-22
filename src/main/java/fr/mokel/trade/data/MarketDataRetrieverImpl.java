package fr.mokel.trade.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import fr.mokel.trade.model.BarChartData;
import fr.mokel.trade.model.DataWindow;
import fr.mokel.trade.model.WindowedList;

public class MarketDataRetrieverImpl extends Observable implements
		IMarketDataRetriever {

	private static final int NB_MONTHS = 96;

	protected static Properties props = new Properties();
	private static Logger logger = Logger
			.getLogger(MarketDataRetrieverImpl.class);
	private DataWindow result;
	private String codeOld;

	public MarketDataRetrieverImpl() {
		MarketDataRetrieverImpl.loadConf();
	}

	public String getCode() {
		return codeOld;
	}

	public void setCode(String code) {
		this.codeOld = code;
	}

	public WindowedList getDayData(String code, LocalDate date) {
		CSVReader read = null;
		File csv = new File(createFileName(code, date));
		if (csv.canRead()) {
			try {
				read = new CSVReader(new FileReader(csv));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			Retriever ret;
			String url = createUrl(code, date);
			logger.info("Fetch: " + url);
			if (StringUtils.isNotBlank(MarketDataRetrieverImpl.props
					.getProperty("proxy.url"))) {
				ret = new Retriever(
						MarketDataRetrieverImpl.props.getProperty("proxy.url"),
						Integer.valueOf(
								MarketDataRetrieverImpl.props
										.getProperty("proxy.port")).intValue(),
						MarketDataRetrieverImpl.props
								.getProperty("proxy.login"),
						MarketDataRetrieverImpl.props
								.getProperty("proxy.password"), url);

			} else {
				ret = new Retriever(url);
			}
			ret.load();
			String data = ret.getWebPage();
			read = new CSVReader(new StringReader(data));
		}
		CsvToBean<BarChartData> toBean = new CsvToBean<BarChartData>();
		HeaderColumnNameTranslateMappingStrategy<BarChartData> strat = new HeaderColumnNameTranslateMappingStrategy<BarChartData>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("Date", "dateString");
		map.put("Open", "open");
		map.put("High", "high");
		map.put("Low", "low");
		map.put("Close", "close");
		map.put("Volume", "volume");
		map.put("Adj Close", "adjClose");
		strat.setColumnMapping(map);
		strat.setType(BarChartData.class);
		List<BarChartData> list = new ArrayList<BarChartData>(toBean.parse(
				strat, read));
		if (!csv.canRead()) {
			writeCsv(code, list, date);
		}
		list = reverseList(list);
		WindowedList wlist = new WindowedList(list,"");
		return wlist;
	}

	private String createUrl(String code, LocalDate date) {
		LocalDate yahooDate = date.minusMonths(NB_MONTHS);
		// alt
		// http://ichart.yahoo.com/table.csv?s=ACA.PA&g=d&a=1&b=1&c=2010&d=3&e=8&f=2014&ignore=.csv
		StringBuilder sb = new StringBuilder(
				"https://ichart.finance.yahoo.com/table.csv?s=");
		sb.append(code);
		sb.append("&g=d").append("&a=").append(yahooDate.getMonthValue())
				.append("&b=1").append("&c=").append(yahooDate.getYear())
				.append("&d=").append(date.getMonthValue()).append("&e=")
				.append(date.getDayOfMonth()).append("&f=")
				.append(date.getYear()).append("&ignore=.csv");
		return sb.toString();
	}

	private String createFileName(String code, LocalDate date) {
		return code + "_"
				+ date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
				+ ".csv";
	}

	private void writeCsv(String code, List<BarChartData> list, LocalDate date) {
		String fileName = createFileName(code, date);
		List<String[]> bars = new ArrayList<String[]>();
		bars.add(getHeaders());
		for (BarChartData barChartData : list) {
			String[] barString = transform(barChartData);
			bars.add(barString);
		}
		FileWriter fw;
		try {
			fw = new FileWriter(fileName);
			CSVWriter w = new CSVWriter(fw, ',', (char) 0);
			w.writeAll(bars);
			w.flush();
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String[] transform(BarChartData barChartData) {
		return new String[] {
				barChartData.getDate().format(
						DateTimeFormatter.ofPattern("yyyy-MM-dd")),
				"" + barChartData.getOpen(), "" + barChartData.getHigh(),
				"" + barChartData.getLow(), "" + barChartData.getClose(),
				"" + barChartData.getVolume(), "" + barChartData.getAdjClose() };
	}

	private String[] getHeaders() {
		return new String[] { "Date", "Open", "High", "Low", "Close", "Volume",
				"Adj Close" };
	}

	private static void loadConf() {
		try {
			props.load(ClassLoader.getSystemResourceAsStream("app.properties"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static List<BarChartData> reverseList(List<BarChartData> toReverse) {
		List<BarChartData> result = new ArrayList<BarChartData>();
		for (int i = toReverse.size() - 1; i >= 0; i--) {
			result.add(toReverse.get(i));
		}
		return result;
	}

	public void run() {
		// result = getDayData();
		setChanged();
		notifyObservers(result);
	}

}
