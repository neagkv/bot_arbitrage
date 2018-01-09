package co.codingnomads.bot.arbitrage.service.arbitrage;

import co.codingnomads.bot.arbitrage.model.ActivatedExchange;
import co.codingnomads.bot.arbitrage.model.TickerData;
import co.codingnomads.bot.arbitrage.arbitrageAction.ArbitrageActionSelection;
import co.codingnomads.bot.arbitrage.arbitrageAction.ArbitragePrintAction;
import co.codingnomads.bot.arbitrage.arbitrageAction.ArbitrageTradingAction;
import co.codingnomads.bot.arbitrage.arbitrageAction.ArbitrageEmailAction;
import co.codingnomads.bot.arbitrage.exceptions.EmailLimitException;
import co.codingnomads.bot.arbitrage.exchange.ExchangeSpecs;
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
 *
 * the arbitrage bot class
 */
@Service
public class Arbitrage {

    // todo make this method running every X minutes (Kevin)
    // todo fix the issue with autowired (ryan)
    // todo look more into the fee (Kevin, thom?)

    /**
     * Arbitrage bot with multiple arbitrage action
     *
     * @param currencyPair             the pair selected
     * @param selectedExchanges        an ArrayList of ExchangeEnum that will be looked in
     * @param arbitrageActionSelection A Pojo for selecting which action to be taken after an arbitrage detection
     * @throws IOException
     */

    public void run(CurrencyPair currencyPair,
                    ArrayList<ExchangeSpecs> selectedExchanges,
                    ArbitrageActionSelection arbitrageActionSelection) throws IOException, InterruptedException, EmailLimitException {

        // todo autowire them
        ExchangeDataGetter exchangeDataGetter = new ExchangeDataGetter();
        DataUtil dataUtil = new DataUtil();
       // ArbitrageAction arbitrageAction = new ArbitrageAction();
        ExchangeGetter exchangeGetter = new ExchangeGetter();

        Boolean tradingMode = arbitrageActionSelection instanceof ArbitrageTradingAction;
        Boolean emailMode = arbitrageActionSelection instanceof ArbitrageEmailAction;
        Boolean printMode = arbitrageActionSelection instanceof ArbitragePrintAction;

        double tradeValueBase = -1;
        if (tradingMode) tradeValueBase = ((ArbitrageTradingAction) arbitrageActionSelection).getTradeValueBase();

        ArrayList<ActivatedExchange> activatedExchanges = exchangeGetter.getAllSelectedExchangeServices(selectedExchanges, tradingMode);

        // temporary
        int i = 0;
        int loop = 1;
        while (i < loop) {


            ArrayList<TickerData> listTickerData = exchangeDataGetter.getAllTickerData(

                    activatedExchanges,
                    currencyPair,
                    tradeValueBase);

            // todo handle with a custom exception on the getAllBidAsk
            if (listTickerData.size() == 0) {
                System.out.println("This pair is not traded on the exchanged selected");
                // System.out.println("This pair is not traded on the exchange selected");
                return;
            }

            // temporary
            System.out.println();
            System.out.println("Pulled Data");

            for (TickerData tickerData : listTickerData) {

                System.out.println(tickerData.toString());
            }
            System.out.println();


            TickerData highBid = dataUtil.highBidFinder(listTickerData); // highBid is the best sell
            TickerData lowAsk = dataUtil.lowAskFinder(listTickerData); // lowAsk is the best buy


            // temporary
            System.out.println("Sorted result");
            System.out.println("the lowest ask is on " + lowAsk.getExchange().getDefaultExchangeSpecification().getExchangeName() +
                    " at " + lowAsk.getAsk());
            System.out.println("the highest bid is on " + highBid.getExchange().getDefaultExchangeSpecification().getExchangeName() +
                    " at " + highBid.getBid());
            System.out.println();

            BigDecimal difference = highBid.getBid().divide(lowAsk.getAsk(), 5, RoundingMode.HALF_EVEN);


            if (printMode) {
                ArbitragePrintAction arbitragePrintAction = (ArbitragePrintAction) arbitrageActionSelection;
                arbitragePrintAction.print(lowAsk, highBid, arbitrageActionSelection.getArbitrageMargin());
            }
            if (emailMode) {
                ArbitrageEmailAction arbitrageEmailAction = (ArbitrageEmailAction) arbitrageActionSelection;
                arbitrageEmailAction.email(arbitrageEmailAction.getEmail(), lowAsk, highBid, difference, arbitrageActionSelection.getArbitrageMargin());

           }
            if (tradingMode) {
                ArbitrageTradingAction arbitrageTradingAction = (ArbitrageTradingAction) arbitrageActionSelection;
                if (!(arbitrageTradingAction.trade(lowAsk, highBid, (ArbitrageTradingAction) arbitrageActionSelection))) return;
            }
            i++;
            if (loop != 1) Thread.sleep(5000);
        }
    }
}