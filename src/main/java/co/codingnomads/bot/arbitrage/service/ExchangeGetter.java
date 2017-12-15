package co.codingnomads.bot.arbitrage.service;

import co.codingnomads.bot.arbitrage.model.ActivatedExchange;
import co.codingnomads.bot.arbitrage.model.ExchangeDetailsEnum;
import co.codingnomads.bot.arbitrage.model.ExchangeServices;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.exceptions.ExchangeException;

import java.util.ArrayList;


/**
 * Created by Thomas Leruth on 12/13/17
 */
public class ExchangeGetter {

    public ArrayList<ActivatedExchange> getAllSelectedExchangeServices(ArrayList<ExchangeDetailsEnum> selectedExchanges) {
        ArrayList<ActivatedExchange> list = new ArrayList<>();

        for (ExchangeDetailsEnum selected : selectedExchanges) {
            ExchangeServices exchangeServices = getServices(selected.getExchangeClass());
            if (exchangeServices != null) {
                list.add(new ActivatedExchange(exchangeServices));
            }
            else {
                System.out.println(selected.getExchangeName() + " was not successfully added");
            }
        }
        return list;
    }

    private static ExchangeServices getServices(Class exchangeClass) {

        try {
            Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exchangeClass.getName());

            ExchangeServices exchangeServices = new ExchangeServices();
            exchangeServices.setExchangeName(exchangeClass.getSimpleName());
            exchangeServices.setMarketDataService(exchange.getMarketDataService());
            exchangeServices.setTradeService(exchange.getTradeService());
            exchangeServices.setAccountService(exchange.getAccountService());
            return exchangeServices;
        }
        catch (ExchangeException e) { return null; }
    }
}
