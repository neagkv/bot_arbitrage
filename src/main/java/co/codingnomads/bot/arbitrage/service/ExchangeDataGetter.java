package co.codingnomads.bot.arbitrage.service;

import co.codingnomads.bot.arbitrage.model.ActivatedExchange;
import co.codingnomads.bot.arbitrage.model.BidAsk;
import co.codingnomads.bot.arbitrage.model.ExchangeServices;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by Thomas Leruth on 12/13/17
 */
@Service
public class ExchangeDataGetter {

    public ArrayList<BidAsk> getAllBidAsk(ArrayList<ActivatedExchange> activatedExchanges, CurrencyPair currencyPair) {
        ArrayList<BidAsk> list = new ArrayList<>();

        for (ActivatedExchange activatedExchange : activatedExchanges) {
            //todo make it thread-able
            if (activatedExchange.isActivated()) {
                ExchangeServices exchangeServices = activatedExchange.getExchangeServices();
                BidAsk bidAskData = getBidAsk(exchangeServices, currencyPair);
                if (null != bidAskData) {
                    list.add(bidAskData);
                } else {
                    System.out.println("no pair available for " + exchangeServices.getExchangeName());
                }
            }
        }
        return list;
    }

    // todo and an anti hang condition in there (if longer than X wait, return null)
    private static BidAsk getBidAsk(ExchangeServices exchangeServices, CurrencyPair currencyPair) {
        Ticker ticker;
        try {
            ticker = exchangeServices.getMarketDataService().getTicker(currencyPair);
        } catch (Exception e)  { //need to refine that exception handling
            return null;
        }
        return new BidAsk(exchangeServices.getExchangeName(), ticker.getBid(), ticker.getAsk());
    }
}
