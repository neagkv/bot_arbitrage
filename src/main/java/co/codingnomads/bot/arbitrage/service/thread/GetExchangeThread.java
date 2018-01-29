package co.codingnomads.bot.arbitrage.service.thread;

import co.codingnomads.bot.arbitrage.model.exchange.ActivatedExchange;
import co.codingnomads.bot.arbitrage.exchange.ExchangeSpecs;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import java.util.concurrent.Callable;

/**
 * Created by Thomas Leruth on 12/16/17
 *
 * Runnable class getting Exchange (set-up) and putting into Activated Exchange which has 2 more boolean
 */
public class GetExchangeThread implements Callable<ActivatedExchange> {

    ExchangeSpecs exchangeSpecs;


    /**
     * call method to get exchange specifications for exchange
     * @return ActivatedExchange
     */
    @Override
    public ActivatedExchange call() {
        Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecs.GetSetupedExchange());
        return new ActivatedExchange(exchange, exchangeSpecs.getTradingMode());
    }

    /**
     * Constructor for getExchangeThread
     * @param exchangeSpecs
     */
    public GetExchangeThread(ExchangeSpecs exchangeSpecs) {
        this.exchangeSpecs = exchangeSpecs;
    }
}
