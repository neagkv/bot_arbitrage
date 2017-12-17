package co.codingnomads.bot.arbitrage.service;

import co.codingnomads.bot.arbitrage.model.ActivatedExchange;
import co.codingnomads.bot.arbitrage.model.BidAsk;
import co.codingnomads.bot.arbitrage.model.exchange.ExchangeSpecs;
import com.sun.org.apache.bcel.internal.ExceptionConstants;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;

import java.util.ArrayList;
import java.util.concurrent.*;


/**
 * Created by Thomas Leruth on 12/13/17
 */

/**
 * A class with a method to get the exchanges correctly set up
 */
public class ExchangeGetter {

    ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    CompletionService<ActivatedExchange> pool = new ExecutorCompletionService<>(executor);

    /**
     * Turn a list of exchange with correct security parameters into a list of exchanges with enabled service
     * @param selectedExchanges a list of exchanges with their needed authentification set
     * @return list of exchange with all services loaded up
     */
    public ArrayList<ActivatedExchange> getAllSelectedExchangeServices(ArrayList<ExchangeSpecs> selectedExchanges) {
        ArrayList<ActivatedExchange> list = new ArrayList<>();

        for (ExchangeSpecs selected : selectedExchanges) {
            GetExchangeThread temp = new GetExchangeThread(selected);
            pool.submit(temp);
        }

        for (int i = 0; i < selectedExchanges.size(); i++) {
            try {
                ActivatedExchange activatedExchange = pool.take().get();
                if (null != activatedExchange.getExchange()) {
                    list.add(activatedExchange);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace(); }
        }
        executor.shutdown();
        return list;
    }
}
