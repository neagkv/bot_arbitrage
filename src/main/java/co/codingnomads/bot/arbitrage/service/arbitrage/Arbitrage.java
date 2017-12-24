package co.codingnomads.bot.arbitrage.service.arbitrage;

import co.codingnomads.bot.arbitrage.model.*;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.ArbitrageActionSelection;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.ArbitrageEmailAction;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.ArbitragePrintAction;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.ArbitrageTradingAction;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.email.Email;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.email.EmailBody;
import co.codingnomads.bot.arbitrage.model.exchange.ExchangeSpecs;
import co.codingnomads.bot.arbitrage.service.general.DataUtil;
import co.codingnomads.bot.arbitrage.service.general.ExchangeDataGetter;
import co.codingnomads.bot.arbitrage.service.general.ExchangeGetter;
import org.knowm.xchange.currency.CurrencyPair;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Created by Thomas Leruth on 12/14/17
 */

/**
 * the arbitrage bot class
 */
@Service
public class Arbitrage {

//    @Autowired
//    DataUtil dataUtil;

    // todo Email action (Kevin later)
    // todo trade action (Thomas)
    // todo make this method running every X minutes (Kevin)
    // todo fix the issue with autowired (ryan)
    // todo look more into the fee
    // Question: could thread the bid and ask finder but I guess gain would be minimum as list is very small

    // Margin at which we take the risk of running the arbitrage it covers
    // 1) Fee (which I can't seem to be able to pull from API, somebody?) (check Kevin and hack the code)
    // 2) Delay leading to movement in bid/ask spread

    /**
     * A Arbitrage arbitrage bot with multiple arbitrage action
     *
     * @param currencyPair             the pair selected
     * @param selectedExchanges        an ArrayList of ExchangeEnum has to be added
     * @param arbitrageActionSelection PXXX
     * @throws IOException
     */
    public void run(CurrencyPair currencyPair,
                    ArrayList<ExchangeSpecs> selectedExchanges,
                    ArbitrageActionSelection arbitrageActionSelection) throws IOException, InterruptedException {

        Boolean tradingMode = arbitrageActionSelection instanceof ArbitrageTradingAction;
        Boolean emailMode = arbitrageActionSelection instanceof ArbitrageEmailAction;
        Boolean printMode = arbitrageActionSelection instanceof ArbitragePrintAction;

        double tradeValueBase = -1;

        if (tradingMode) tradeValueBase = ((ArbitrageTradingAction) arbitrageActionSelection).getTradeValueBase();

        ExchangeGetter exchangeGetter = new ExchangeGetter();

        ArrayList<ActivatedExchange> activatedExchanges = exchangeGetter.getAllSelectedExchangeServices(selectedExchanges, tradingMode);

        // todo autowire it
        ExchangeDataGetter exchangeDataGetter = new ExchangeDataGetter();

        // temporary
        int i = 0;
        int loop = 1;

        while (i < loop) {

            ArrayList<BidAsk> listBidAsk = exchangeDataGetter.getAllBidAsk(
                    activatedExchanges,
                    currencyPair,
                    tradeValueBase);

            // todo handle with a custom exception on the getAllBidAsk
            if (listBidAsk.size() == 0) {
                System.out.println("This pair is not traded on the exchanged selected");
                // System.out.println("This pair is not traded on the exchange selected");
                return;
            }

            // temporary
            System.out.println();
            System.out.println("Pulled Data");
            for (BidAsk bidAsk : listBidAsk) {
                System.out.println(bidAsk.toString());
            }
            System.out.println();

            // todo autowire it
            DataUtil dataUtil = new DataUtil();

            BidAsk lowAsk = dataUtil.lowAskFinder(listBidAsk);
            BidAsk highBid = dataUtil.highBidFinder(listBidAsk);

            // temporary
            System.out.println("Sorted result");
            System.out.println("the lowest ask is on " + lowAsk.getExchange().getDefaultExchangeSpecification().getExchangeName() +
                    " at " + lowAsk.getAsk());
            System.out.println("the highest bid is on " + highBid.getExchange().getDefaultExchangeSpecification().getExchangeName() +
                    " at " + highBid.getBid());
            System.out.println();

            BigDecimal difference = highBid.getBid().divide(lowAsk.getAsk(), 5, RoundingMode.HALF_EVEN);

            // todo autowire it
            ArbitrageAction arbitrageAction = new ArbitrageAction();
            Email email = new Email(arbitrageActionSelection.getArbitrageMargin());

            if (printMode) {
                arbitrageAction.print(lowAsk, highBid, difference, arbitrageActionSelection.getArbitrageMargin());
            }
            if (emailMode) {
                arbitrageAction.email((ArbitrageEmailAction) arbitrageActionSelection, email, email, lowAsk, highBid, difference, arbitrageActionSelection.getArbitrageMargin());
            }
            if (tradingMode) {
                arbitrageAction.trade(lowAsk, highBid, difference, (ArbitrageTradingAction) arbitrageActionSelection);
            }
            i++;
            if (loop != 1) Thread.sleep(5000);
        }
    }
}