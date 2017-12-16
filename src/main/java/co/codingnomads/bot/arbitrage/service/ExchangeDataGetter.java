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
@Service
public class ExchangeDataGetter {

    // protected final Logger logger = LoggerFactory.getLogger(getClass());

    public ArrayList<BidAsk> getAllBidAsk(ArrayList<ActivatedExchange> activatedExchanges, CurrencyPair currencyPair) {
        ArrayList<BidAsk> list = new ArrayList<>();

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        CompletionService<BidAsk> pool = new ExecutorCompletionService<>(executor);

        //todo change the thread taking active exchange
        //todo ask bid/ask sufficient
        //todo if both out, desactivate exchange
        //todo run the bid/ask getter and fix for sufficience
        for (ActivatedExchange activatedExchange : activatedExchanges) {
            if (activatedExchange.isActivated()) {
                GetBidAskThread temp = new GetBidAskThread(activatedExchange.getExchange(), currencyPair);
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
    protected static BidAsk getBidAsk(Exchange exchange, CurrencyPair currencyPair) {
        Ticker ticker;
        try {
            ticker = exchange.getMarketDataService().getTicker(currencyPair);
        } catch (Exception e)  { //todo need to refine that exception handling
            return null;
        }
        return new BidAsk(exchange.getExchangeSpecification().getExchangeName(), ticker.getBid(), ticker.getAsk());
    }
}
