package fr.mokel.trade.data;


import java.time.LocalDate;

import fr.mokel.trade.model.WindowedList;

public interface MarketDataRetriever {

	WindowedList getData(String code, LocalDate date);
}
