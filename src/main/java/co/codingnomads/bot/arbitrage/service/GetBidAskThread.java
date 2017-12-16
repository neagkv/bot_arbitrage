package co.codingnomads.bot.arbitrage.service;

import co.codingnomads.bot.arbitrage.model.BidAsk;
import co.codingnomads.bot.arbitrage.model.ExchangeServices;
import org.knowm.xchange.currency.CurrencyPair;

import java.util.concurrent.Callable;

/**
 * Created by Thomas Leruth on 12/16/17
 */
// I feel like a magician after writing this
// in my understanding this cool functional interface is runnable with a potential return type
public class GetBidAskThread implements Callable<BidAsk> {

    private ExchangeServices exchangeServices;
    private CurrencyPair currencyPair;
    private String exchangeName;

    @Override
    public BidAsk call() {
        System.out.println("start" + exchangeName);
        BidAsk bidAsk = ExchangeDataGetter.getBidAsk(exchangeServices, currencyPair);
        System.out.println("end" + exchangeName);
        if(bidAsk == null) {
            System.out.println("pair not available for " + exchangeName);
        }
        return bidAsk;
    }

    public GetBidAskThread(String exchangeName, ExchangeServices exchangeServices, CurrencyPair currencyPair) {
        this.exchangeName = exchangeName;
        this.exchangeServices = exchangeServices;
        this.currencyPair = currencyPair;
    }
}
