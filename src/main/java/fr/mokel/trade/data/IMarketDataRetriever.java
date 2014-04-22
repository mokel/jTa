package fr.mokel.trade.data;


import java.time.LocalDate;

import fr.mokel.trade.model.Chart;
import fr.mokel.trade.model.DataWindow;
import fr.mokel.trade.model.WindowedList;

public interface IMarketDataRetriever {

	WindowedList getDayData(String code, LocalDate date);
}
