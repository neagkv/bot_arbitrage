package co.codingnomads.bot.arbitrage.service;

import co.codingnomads.bot.arbitrage.model.BidAsk;
import co.codingnomads.bot.arbitrage.model.ExchangeServices;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

/**
 * Created by Thomas Leruth on 12/13/17
 */

public class DataGetter {
    // todo make this service into threads so we call cut the API delay
    // todo and an anti hang condition in there (if longer than X wait, return null)
    public BidAsk getBidAsk(ExchangeServices exchangeServices, CurrencyPair currencyPair) {
        Ticker ticker;
        try {
            ticker = exchangeServices.getMarketDataService().getTicker(currencyPair);
        } catch (Exception e)  { //need to refine that exception handling
            return null;
        }
        return new BidAsk(exchangeServices.getExchangeName(), ticker.getBid(), ticker.getAsk());
    }
}
