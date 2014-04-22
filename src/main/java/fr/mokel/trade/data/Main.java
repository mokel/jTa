package fr.mokel.trade.data;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Properties;

import fr.mokel.trade.model.DataWindow;

public class Main {

    protected static Properties props = new Properties();

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
    	
    	System.out.println(System.getProperty("java.class.path"));
    	MarketDataRetrieverImpl m = new MarketDataRetrieverImpl();
    	DataWindow c = m.getDayData("ACA.PA", LocalDate.now());
    	System.out.println(c);
    }
}
