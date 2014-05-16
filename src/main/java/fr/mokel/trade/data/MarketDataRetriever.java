package fr.mokel.trade.data;


import java.time.LocalDate;
import java.util.List;

import fr.mokel.trade.model.DayValue;

public interface MarketDataRetriever {

	List<DayValue> getData(String code, LocalDate date);
}
