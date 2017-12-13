package co.codingnomads.bot.arbitrage.service;

import co.codingnomads.bot.arbitrage.model.BidAsk;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;

/**
 * Created by Thomas Leruth on 12/13/17
 */

public class DataGetter {

    public BidAsk getBidAsk(MarketDataService marketDataService, CurrencyPair currencyPair, String exchangeName) {
        Ticker ticker;
        try {
            ticker = marketDataService.getTicker(currencyPair);
        } catch (Exception e)  { //need to refine that exception handling
            return null;
        }
        return new BidAsk(exchangeName, ticker.getBid(), ticker.getAsk());
    }
}
