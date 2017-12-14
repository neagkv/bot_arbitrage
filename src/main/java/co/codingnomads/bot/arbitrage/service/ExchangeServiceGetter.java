package co.codingnomads.bot.arbitrage.service;

import co.codingnomads.bot.arbitrage.model.ExchangeServices;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;

/**
 * Created by Thomas Leruth on 12/13/17
 */

public class ExchangeServiceGetter {

    public ExchangeServices getExchangeService(Class exchangeClass) {
        Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exchangeClass.getName());

        ExchangeServices exchangeServices = new ExchangeServices();
        exchangeServices.setExchangeName(exchangeClass.getSimpleName());
        exchangeServices.setMarketDataService(exchange.getMarketDataService());
        exchangeServices.setTradeService(exchange.getTradeService());
        exchangeServices.setAccountService(exchange.getAccountService());
        return exchangeServices;
    }
}
