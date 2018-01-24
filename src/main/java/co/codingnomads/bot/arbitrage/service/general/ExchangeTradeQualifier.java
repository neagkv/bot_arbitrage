package co.codingnomads.bot.arbitrage.service.general;

import co.codingnomads.bot.arbitrage.exchange.ExchangeSpecs;
import co.codingnomads.bot.arbitrage.model.exchange.ActivatedExchange;
import co.codingnomads.bot.arbitrage.service.thread.GetExchangeThread;
import org.knowm.xchange.currency.CurrencyPair;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * @author Kevin Neag
 */
public class ExchangeTradeQualifier {

    ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    CompletionService<ActivatedExchange> pool = new ExecutorCompletionService<>(executor);

    public ArrayList<ActivatedExchange> exchangeQualifier(ArrayList<ActivatedExchange> activatedExchanges, BigDecimal valueOfTradeValueBase, CurrencyPair currencyPair) throws IOException {

        ArrayList<ActivatedExchange> qualifiedExchanges = new ArrayList<ActivatedExchange>();

        for (ActivatedExchange activatedExchange : activatedExchanges) {

            Callable<ActivatedExchange> activatedExchangeCallable = new Callable<ActivatedExchange>() {

                @Override
                public ActivatedExchange call() throws Exception {
                    return null;
                }
            };

            pool.submit(activatedExchangeCallable);

            for (int i = 0; i < qualifiedExchanges.size(); i++) {

                try {
                    qualifiedExchange = pool.take().get();
                    if (null != activatedExchange.getExchange()) {
                        qualifiedExchanges.add(activatedExchange);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            executor.shutdown();

            System.out.println(activatedExchange.getExchange().getExchangeSpecification().getExchangeName());
        }

    return activatedExchanges;

    }

}
