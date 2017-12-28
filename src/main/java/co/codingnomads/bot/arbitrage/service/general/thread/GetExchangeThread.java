package co.codingnomads.bot.arbitrage.service.general.thread;

import co.codingnomads.bot.arbitrage.model.ActivatedExchange;
import co.codingnomads.bot.arbitrage.model.exchange.ExchangeSpecs;
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

    @Override
    public ActivatedExchange call() {
        Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecs.GetSetupedExchange());
        return new ActivatedExchange(exchange, exchangeSpecs.getTradingMode());
    }

    public GetExchangeThread(ExchangeSpecs exchangeSpecs) {
        this.exchangeSpecs = exchangeSpecs;
    }
}
