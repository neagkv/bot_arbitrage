package co.codingnomads.bot.arbitrage.service;

import co.codingnomads.bot.arbitrage.model.ActivatedExchange;
import co.codingnomads.bot.arbitrage.model.BidAsk;
import co.codingnomads.bot.arbitrage.model.ExchangeServices;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * Created by Thomas Leruth on 12/13/17
 */
@Service
public class ExchangeDataGetter {

    // protected final Logger logger = LoggerFactory.getLogger(getClass());

    public ArrayList<BidAsk> getAllBidAsk(ArrayList<ActivatedExchange> activatedExchanges, CurrencyPair currencyPair) {
        ArrayList<BidAsk> list = new ArrayList<>();

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        CompletionService<BidAsk> pool = new ExecutorCompletionService<>(executor);

        for (ActivatedExchange activatedExchange : activatedExchanges) {
            if (activatedExchange.isActivated()) {
                ExchangeServices exchangeServices = activatedExchange.getExchangeServices();
                GetBidAskThread temp = new GetBidAskThread(activatedExchange.getExchangeServices().getExchangeName(),
                        exchangeServices, currencyPair);
                pool.submit(temp);
            }
        }
        for (int i = 0; i < activatedExchanges.size(); i++) {
            try {
                BidAsk bidAsk = pool.take().get();
                if (null != bidAsk) {
                    list.add(bidAsk);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();

        return list;
    }

    // todo and an anti hang condition in there (if longer than X wait, return null)
    protected static BidAsk getBidAsk(ExchangeServices exchangeServices, CurrencyPair currencyPair) {
        Ticker ticker;
        try {
            ticker = exchangeServices.getMarketDataService().getTicker(currencyPair);
        } catch (Exception e)  { //todo need to refine that exception handling
            return null;
        }
        return new BidAsk(exchangeServices.getExchangeName(), ticker.getBid(), ticker.getAsk());
    }
}
