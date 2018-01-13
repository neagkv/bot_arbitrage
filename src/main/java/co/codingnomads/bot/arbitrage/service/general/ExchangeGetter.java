package co.codingnomads.bot.arbitrage.service.general;

import co.codingnomads.bot.arbitrage.model.exchange.ActivatedExchange;
import co.codingnomads.bot.arbitrage.exchange.ExchangeSpecs;
import co.codingnomads.bot.arbitrage.service.thread.GetExchangeThread;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.concurrent.*;


/**
 * Created by Thomas Leruth on 12/13/17
 *
 * A class with a method to get the exchanges correctly set up
 */
public class ExchangeGetter {

    ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    CompletionService<ActivatedExchange> pool = new ExecutorCompletionService<>(executor);

    /**
     * Turn a list of exchange with correct security parameters into a list of exchanges with enabled service
     * @param selectedExchanges a list of exchanges with their needed authentification set
     * @param tradingMode whether or not the action behavior is trading
     * @return list of exchange with all services loaded up
     */
    public ArrayList<ActivatedExchange> getAllSelectedExchangeServices(
            ArrayList<ExchangeSpecs> selectedExchanges,
            boolean tradingMode) {

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
                e.printStackTrace();
            }
        }
        executor.shutdown();

        if (tradingMode) {
            for (ActivatedExchange activatedExchange : list) {
                activatedExchange.setActivated(activatedExchange.isTradingMode());
            }
        }
        return list;
    }
}
