package co.codingnomads.bot.arbitrage;

import co.codingnomads.bot.arbitrage.model.BidAsk;
import co.codingnomads.bot.arbitrage.model.ActivatedExchange;
import co.codingnomads.bot.arbitrage.model.ExchangeDetailsEnum;
import co.codingnomads.bot.arbitrage.model.ExchangeServices;
import co.codingnomads.bot.arbitrage.service.DataGetter;
import co.codingnomads.bot.arbitrage.service.DataUtil;
import co.codingnomads.bot.arbitrage.service.ExchangeServiceGetter;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Created by Thomas Leruth on 12/11/17
 */

public class Controller {

    // todo make this method more flexible (what pair, what percent, what to do, which exchange to enable) by moving it to another class
    // 1) what pair (done)
    // 2) what percent (done)
    // 3) what to do
    // A) Email
    // B) Buy sell
    // C) simple print (done)
    // D) other
    // 4) What exchange (done)
    // todo make this method as a thread running every X minutes
    // todo fix the issue with loger
    // todo think about the fee issue
    public static void main(String[] args) throws IOException {

        // todo external param
        CurrencyPair currencyPair = CurrencyPair.ETH_EUR;

        //todo external param (could be via a map to decrease the argument amount)
        boolean PrintActionFlag = true;
        boolean tradeActionFlag = false;
        boolean emailAction = false;

        // Margin at which we take the risk of running the arbitrage it covers
        // 1) Fee (which I can't seem to be able to pull from API, somebody?)
        // 2) Delay leading to movement in bid/ask spread
        //todo external param
        double arbitrageMargin = 1.02;

        //todo external param (could be via a map to decrease the argument amount)
        boolean gdaxFlag = true;
        boolean krakenFlag = false; //slowwwwwwww
        boolean bitstampFlag = true;
        boolean bitfinexFlag = true;
        boolean poloniexFlag = true;

        ArrayList<ExchangeDetailsEnum> selectedExchanges = new ArrayList<>();
        if (gdaxFlag)
            selectedExchanges.add(ExchangeDetailsEnum.GDAXEXCHANGE);
        if (krakenFlag)
            selectedExchanges.add(ExchangeDetailsEnum.KRAKENEXCHANGE);
        if (bitstampFlag)
            selectedExchanges.add(ExchangeDetailsEnum.BITSTAMPEXCHANGE);
        if (bitfinexFlag)
            selectedExchanges.add(ExchangeDetailsEnum.BITFINEXEXCHANGE);
        if (poloniexFlag)
            selectedExchanges.add(ExchangeDetailsEnum.POLONIEXEXCHANGE);

        //todo autowire
        ExchangeServiceGetter exchangeServiceGetter = new ExchangeServiceGetter();

        //wondering if a hashmap would be more suitable
        ArrayList<ActivatedExchange> activatedExchanges = new ArrayList<>();
        // load all exchanges with preloaded services in this HashMap
        for (ExchangeDetailsEnum selected : selectedExchanges) {
            ExchangeServices exchangeServices = exchangeServiceGetter.getExchangeService(selected.getExchangeClass());
            activatedExchanges.add(new ActivatedExchange(exchangeServices));
        }

        // the part above should run one time and could be external in a loader method or so

        // todo get a method checking if enough fund (using currencypair.counter and .base) and desactivating exchange if not
        // todo only checking if exchange is not desactivated yet

        // todo autowire it
        DataGetter dataGetter = new DataGetter();

        ArrayList<BidAsk> listBidAsk = new ArrayList<>();

        for (ActivatedExchange activatedExchange : activatedExchanges) {
            //todo make this external and a thread-able
            if (activatedExchange.isActivated()) {
                ExchangeServices exchangeServices = activatedExchange.getExchangeServices();
                BidAsk bidAskData = dataGetter.getBidAsk(exchangeServices, currencyPair);
                if (null != bidAskData) {
                    listBidAsk.add(bidAskData);
                } else {
                    System.out.println("no pair available for " + exchangeServices.getExchangeName());
                }
            }
        }
        System.out.println();

        if (listBidAsk.size() == 0) {
            System.out.println("This pair is not traded on the exchange selected");
            return;
        }

        // temporary
        System.out.println("Pulled Data");
        for (BidAsk bidAsk : listBidAsk) {
            System.out.println(bidAsk.toString());
        }
        System.out.println();

        DataUtil dataUtil = new DataUtil();
        BidAsk lowAsk = dataUtil.lowAskFinder(listBidAsk);
        BidAsk highBid = dataUtil.highBidFinder(listBidAsk);

        // temporary
        System.out.println("Sorted result");
        System.out.println("the lowest ask is on " + lowAsk.getExchangeName() + " at " + lowAsk.getAsk());
        System.out.println("the highest bid is on " + highBid.getExchangeName() + " at " + highBid.getBid());
        System.out.println();

        BigDecimal difference = highBid.getBid().divide(lowAsk.getAsk(), 10, RoundingMode.HALF_UP);

        // todo make it an external method
        if (PrintActionFlag) {
            if (difference.compareTo(BigDecimal.valueOf(arbitrageMargin)) > 0) {
                System.out.println("ARBITRAGE DETECTED!!!"
                        + " buy on " + lowAsk.getExchangeName() + " for " + lowAsk.getAsk()
                        + " and sell on " + highBid.getExchangeName() + " for " + highBid.getBid()
                        + " and make a return (before fees) of " + difference.add(BigDecimal.valueOf(-1)));
            } else {
                System.out.println("No arbitrage found");
            }
        }

        // todo decide what do email and trading

    }
}

