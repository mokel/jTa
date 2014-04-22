package fr.mokel.trade.moneymanagement;

public class MoneyManagementFactory {

	public static MoneyManagement getStandard() {
		return new StandardMoneyManagement();
	}

}
