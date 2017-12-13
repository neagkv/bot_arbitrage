package co.codingnomads.bot.arbitrage;

import co.codingnomads.bot.arbitrage.model.BidAsk;
import co.codingnomads.bot.arbitrage.service.DataGetter;
import co.codingnomads.bot.arbitrage.service.DataUtil;
import com.sun.org.apache.xpath.internal.SourceTree;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitstamp.BitstampExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.gdax.GDAXExchange;
import org.knowm.xchange.kraken.KrakenExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Created by Thomas Leruth on 12/11/17
 */

public class Controller {

    // todo make this method more flexible (what pair, what percent, what to do, which exchange to enable) by moving it to another class
    // 1) what pair
    // 2) what percent
    // 3) what to do
    // A) Email
    // B) Buy sell
    // C) other
    // 4) What exchange
    // todo make this method as a thread running every X minutes
    // todo fix the issue with loger
    // think about the fee issue
    public static void main(String[] args) throws IOException {

        CurrencyPair currencyPair = CurrencyPair.BCH_EUR;
        double arbitrageMargin = 1.02;
        // Margin at which we take the risk of running the arbitrage it covers
        // 1) Fee (which I can't seem to be able to pull from API, somebody?)
        // 2) Delay leading to movement in bid/ask spread

        // What thinking about doing one method to do that the issue is we might need the Exchange object
        // for other services (trade)
        // some exception can be thrown by those, we need to be handling them (I have seen timeout from beloved 5xx-Kraken)
        Exchange gdax = ExchangeFactory.INSTANCE.createExchange(GDAXExchange.class.getName());
        Exchange kraken = ExchangeFactory.INSTANCE.createExchange(KrakenExchange.class.getName());
        Exchange bitstamp = ExchangeFactory.INSTANCE.createExchange(BitstampExchange.class.getName());
        MarketDataService krakenPublic = kraken.getMarketDataService();
        MarketDataService bitstampPublic = bitstamp.getMarketDataService();
        MarketDataService gdaxPublic = gdax.getMarketDataService();

        // autowire it
        DataGetter dataGetter = new DataGetter();

        // todo make this service into threads so we call cut the API delay
        // todo add an anti hang condition (if longer than X wait, return null)
        BidAsk gdaxData = dataGetter.getBidAsk(gdaxPublic, currencyPair, "GDAX");
        BidAsk krakenData = dataGetter.getBidAsk(krakenPublic, currencyPair, "Kraken");
        BidAsk bitstampData = dataGetter.getBidAsk(bitstampPublic, currencyPair, "Bitstamp");

        ArrayList<BidAsk> list = new ArrayList<>();
        if (null != gdaxData)
            list.add(gdaxData);
        if (null != krakenData)
            list.add(krakenData);
        if (null != bitstampData)
            list.add(bitstampData);

        if (list.size() == 0) {
            System.out.println("This pair is not traded on the exchange selected");
            return;
        }

        System.out.println("Pulled Data");
        for (BidAsk bidAsk : list) {
            System.out.println(bidAsk.toString());
        }
        System.out.println();

        DataUtil dataUtil = new DataUtil();
        BidAsk lowBid = dataUtil.lowBidFinder(list);
        BidAsk highAsk = dataUtil.highAskFinder(list);

        System.out.println("Sorted result");
        System.out.println("the lowest bid is on " + lowBid.getExchangeName() + " at " + lowBid.getBid());
        System.out.println("the highest ask is on " + highAsk.getExchangeName() + " at " + highAsk.getAsk());
        System.out.println();

        BigDecimal difference = highAsk.getAsk().divide(lowBid.getBid(),10, RoundingMode.HALF_UP);

        // this is definitely not the end action but nice to have at first
        if (difference.compareTo(BigDecimal.valueOf(arbitrageMargin)) > 0) {
            System.out.println("ARBITRAGE DETECTED!!!"
                    + " buy on " + lowBid.getExchangeName() + " for " + lowBid.getBid()
                    + " and sell on " + highAsk.getExchangeName() + " for " + highAsk.getAsk()
                    + " and make a return (before fees) of " +  difference.add(BigDecimal.valueOf(-1)));
        }

        // todo decide what do (email, actual order, other) could implement all and use a flag to activate the method

    }
}

