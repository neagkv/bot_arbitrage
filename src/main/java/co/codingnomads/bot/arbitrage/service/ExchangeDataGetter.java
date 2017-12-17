package co.codingnomads.bot.arbitrage.service;

import co.codingnomads.bot.arbitrage.model.ActivatedExchange;
import co.codingnomads.bot.arbitrage.model.BidAsk;
import org.knowm.xchange.Exchange;
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

/**
 * A class to get data from exchanges and format it correctly
 */
@Service
public class ExchangeDataGetter {

    // protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Get All the BidAsk from the selected exchanged
     * @param activatedExchanges list of currently acrivated exchanges
     * @param currencyPair the pair the BidAsk is seeked for
     * @return A list of BidAsk for all the exchanges
     */
    public ArrayList<BidAsk> getAllBidAsk(ArrayList<ActivatedExchange> activatedExchanges,
                                          CurrencyPair currencyPair,
                                          double baseNeed,
                                          double counterNeed) {

        ArrayList<BidAsk> list = new ArrayList<>();

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        CompletionService<BidAsk> pool = new ExecutorCompletionService<>(executor);

        for (ActivatedExchange activatedExchange : activatedExchanges) {
            if (activatedExchange.isActivated()) {
                GetBidAskThread temp = new GetBidAskThread(activatedExchange, currencyPair, baseNeed, counterNeed);
                pool.submit(temp);
            }
        }
        for (ActivatedExchange activatedExchange : activatedExchanges) {
            if (activatedExchange.isActivated()) {
                try {
                    BidAsk bidAsk = pool.take().get();
                    if (null != bidAsk) {
                        list.add(bidAsk);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }

        executor.shutdown();

        return list;
    }

    // todo and an anti hang condition in there (if longer than X wait, return null)
    protected static BidAsk getBidAsk(Exchange exchange, CurrencyPair currencyPair) {
        Ticker ticker;
        try {
            ticker = exchange.getMarketDataService().getTicker(currencyPair);
        } catch (Exception e)  { //todo need to refine that exception handling
            return null;
        }
        return new BidAsk(currencyPair, exchange.getExchangeSpecification().getExchangeName(), ticker.getBid(), ticker.getAsk());
    }
}
