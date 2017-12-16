package co.codingnomads.bot.arbitrage.service;

import co.codingnomads.bot.arbitrage.model.BidAsk;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.util.concurrent.Callable;

/**
 * Created by Thomas Leruth on 12/16/17
 */
// I feel like a magician after writing this
// in my understanding this cool functional interface is runnable with a potential return type
public class GetBidAskThread implements Callable<BidAsk> {

    private Exchange exchange;
    private CurrencyPair currencyPair;
    private String exchangeName;

    //todo make the call to balance here and make the bid ask wrong if so
    @Override
    public BidAsk call() {
        System.out.println("start " + exchangeName);
        BidAsk bidAsk = ExchangeDataGetter.getBidAsk(exchange, currencyPair);
        System.out.println("end " + exchangeName);
        if(bidAsk == null) {
            System.out.println("pair not available for " + exchangeName);
        }
        return bidAsk;
    }

    public GetBidAskThread(Exchange exchange, CurrencyPair currencyPair) {
        this.exchange = exchange;
        this.currencyPair = currencyPair;
        exchangeName = exchange.getExchangeSpecification().getExchangeName();
    }
}
