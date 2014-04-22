package fr.mokel.trade.moneymanagement;

import java.util.List;

import fr.mokel.trade.model.BackTest;
import fr.mokel.trade.model.Signal;
import fr.mokel.trade.model.Stock;

public abstract class MoneyManagement {

	public abstract BackTest applySignals(List<Signal> mm, Stock stock);

}
