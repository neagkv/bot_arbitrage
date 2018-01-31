package co.codingnomads.bot.arbitrage;

import co.codingnomads.bot.arbitrage.action.arbitrage.ArbitragePrintAction;
import co.codingnomads.bot.arbitrage.action.arbitrage.ArbitrageTradingAction;
import co.codingnomads.bot.arbitrage.action.arbitrage.ArbitrageEmailAction;
import co.codingnomads.bot.arbitrage.action.detection.DetectionLogAction;
import co.codingnomads.bot.arbitrage.action.detection.DetectionPrintAction;
import co.codingnomads.bot.arbitrage.action.detection.selection.DetectionActionSelection;
import co.codingnomads.bot.arbitrage.exception.ExchangeDataException;
import co.codingnomads.bot.arbitrage.exception.EmailLimitException;
import co.codingnomads.bot.arbitrage.exception.WaitTimeException;
import co.codingnomads.bot.arbitrage.exchange.*;
import co.codingnomads.bot.arbitrage.service.detection.Detection;
import co.codingnomads.bot.arbitrage.service.detection.DetectionService;
import co.codingnomads.bot.arbitrage.service.email.EmailService;
import co.codingnomads.bot.arbitrage.service.arbitrage.Arbitrage;
import co.codingnomads.bot.arbitrage.service.tradehistory.TradeHistoryService;
import org.knowm.xchange.currency.CurrencyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Thomas Leruth on 12/11/17
 *
 * Controller for running the arbitrage or detection bot
 */

@Service
public class Controller {


    @Autowired
    Arbitrage arbitrage;

    @Autowired
    Detection detection;

    @Autowired
    TradeHistoryService tradeHistoryService;

    @Autowired
    EmailService emailService;

    @Autowired
    DetectionService detectionService;

    @Autowired
    ArbitrageTradingAction arbitrageTradingAction;

    @Autowired
    ArbitrageEmailAction arbitrageEmailAction;

    @Autowired
    ArbitragePrintAction arbitragePrintAction;

    @Autowired
    DetectionLogAction detectionLogAction;

    @Autowired
    DetectionPrintAction detectionPrintAction;


    /**
     * runBot method, choose one of the three arbitrage actions or two detection actions to run
     * choose the exchange specifications you would like to use, for arbitrage trade action you will need to manually add your api key,
     * api secret key and other exchange specifications depending on the exchange. For all arbitrage actions you have to option to manually set
     * the number of times you would like the method to run (loopIterations) and the time interval you would like the action to repeat at in milliseconds,
     * must be at least 5000. for all arbitrage actions you must set the arbitrage margin or percentage of return you would like to check for form the arbitrage.
     * For example a 0.03 would equal a 0.03 percent price difference between the exchanges.You must also pass the arbitrage method the currency pair you would like
     * to check for. For arbitrage trade action you must set the trade value base or amount of base currency you would like to trade, for example the currency
     * pair ETH_BTC the base currency is ETH. For best results please make sure you are trading the minimum amount of base currency set by your exchange, and you
     * have enough counter currency to purchase the min amount of base currency. Check the exchanges you are attempting to use. For arbitrage email action, enter your
     * email address that you would like to receive, must be verified by aws. for Detection actions, in addition to setting exchange list, choose currency pairs, for
     * best results make sure that each exchange you selects supports the currency pairs you select. For detection log action set the time interval
     * you want the send results to the database.
     * @throws IOException
     * @throws InterruptedException
     * @throws EmailLimitException
     * @throws WaitTimeException
     * @throws ExchangeDataException
     */

    public void runBot() throws IOException, InterruptedException, EmailLimitException, WaitTimeException, ExchangeDataException{

//      set the exchanges you wish to use, you may optionally set the specific exchange specifications to enable trading action
        ArrayList<ExchangeSpecs> ExchangeList = new ArrayList<>();
        ExchangeList.add(new KrakenSpecs());
        ExchangeList.add(new GDAXSpecs());
        ExchangeList.add(new BittrexSpecs());
        ExchangeList.add(new BinanceSpecs());
        ExchangeList.add(new PoloniexSpecs());
        ExchangeList.add(new BittrexSpecs());
        ExchangeList.add(new GeminiSpecs());

        //choose one and only one of the following Arbitrage or Detection trade actions

//Arbitrage

        //optional set how many times you would like the arbitrage action to run, if null will run once
//        arbitrage.setLoopIterations(5);

        //optional set to the time interval you would like the arbitrage action to rerun in milliseconds
        //if not set, the action will run every 5 seconds untill the loopIteration is complete
//        arbitrage.setTimeIntervalRepeater(5000);


//        Example of an Arbitrage trade action
//        arbitrageTradingAction.setArbitrageMargin(0.03);
//        arbitrageTradingAction.setTradeValueBase(0.020);
//        arbitrage.run(
//                CurrencyPair.ETH_USD,
//                ExchangeList,
//                arbitrageTradingAction);


//      Example of an Arbitrage print action that finds the best trading pair every hour
      arbitragePrintAction.setArbitrageMargin(0.03);  //0.03 = 0.03 %
      arbitrage.run(
                    CurrencyPair.ETH_USD,
                    ExchangeList,
                    arbitragePrintAction);

//    Example of an Arbitrage email action
//    arbitrageEmailAction.setArbitrageMargin(0.03);
//    arbitrageEmailAction.getEmail().setTO("your-email-address");
//    emailService.insertEmailRecords(arbitrageEmailAction.getEmail());
//    arbitrage.run(
//                CurrencyPair.ETH_USD,
//                ExchangeList,
//                arbitrageEmailAction);



//Detection

        //List of currencyPairs you would like to check, for Detection only
//      ArrayList<CurrencyPair> currencyPairList = new ArrayList<>();
//      currencyPairList.add(CurrencyPair.ETH_USD);
//      currencyPairList.add(CurrencyPair.ETH_BTC);
//      currencyPairList.add(CurrencyPair.BTC_USD);

//    Example of a Detection print action
//    DetectionActionSelection detectionActionSelection = new DetectionPrintAction();
//    detection.run(currencyPairList, ExchangeList, detectionActionSelection);

//    Example of a Detection log action
//    DetectionActionSelection detectionActionSelection1 = new DetectionLogAction(60000);
//    detection.run(currencyPairList, ExchangeList, detectionActionSelection1);


    }
}

