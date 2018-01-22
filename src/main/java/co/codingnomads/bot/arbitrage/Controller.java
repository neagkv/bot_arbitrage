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
import co.codingnomads.bot.arbitrage.service.general.BalanceCalc;
import co.codingnomads.bot.arbitrage.service.tradehistory.TradeHistoryService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
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


    public void runBot() throws IOException, InterruptedException, EmailLimitException, WaitTimeException, ExchangeDataException{

//set the exchanges you wish to use, you may optionally set the specific exchange specifications to enable trading action
        ArrayList<ExchangeSpecs> ExchangeList = new ArrayList<>();
        ExchangeList.add(new KrakenSpecs());//enter api key and secret for arbitrage trade action
        ExchangeList.add(new GDAXSpecs());//enter api key secret and passphrase for arbitrage trade action
        ExchangeList.add(new PoloniexSpecs());


        //choose one and only one of the following Arbitrage or Detection trade actions

//Arbitrage

        //optional set how many times you would like the arbitrage action to run, if null will run once
        //arbitrage.setLoopIterations(5);

        //optional set to the time interval you would like the arbitrage action to rerun in milliseconds
        //if not set, the action will run every 5 seconds untill the loopIteration is complete
        //arbitrage.setTimeIntervalRepeater(5000);


//        Example of an Arbitrage trade action
          arbitrageTradingAction.setArbitrageMargin(0.90);
          arbitrageTradingAction.setTradeValueBase(0.02);
          arbitrage.run(
                CurrencyPair.ETH_USD,
                ExchangeList,
                arbitrageTradingAction);


//      Example of an Arbitrage print action that finds the best trading pair every hour
//        set arbitragemargin
//        arbitragePrintAction.setArbitrageMargin(1.00);
//          arbitrage.run(
//                    CurrencyPair.ETH_BTC,
//                    ExchangeList,
//                    arbitragePrintAction);



//    Example of an Arbitrage email action
//    arbitrageEmailAction.setArbitrageMargin(1.00);
//    arbitrageEmailAction.getEmail().setTO("neagkv@gmail.com");
//    emailService.insertEmailRecords(arbitrageEmailAction.getEmail());
//    arbitrage.run(
//                CurrencyPair.BTC_USD,
//                ExchangeList,
//                arbitrageEmailAction);



//Detection


        //List of currencyPairs you would like to check, for Detection only
//        ArrayList<CurrencyPair> currencyPairList = new ArrayList<>();
//        currencyPairList.add(CurrencyPair.ETH_USD);
//        currencyPairList.add(CurrencyPair.BTC_USD);
//        currencyPairList.add(CurrencyPair.BTC_USD);
//        currencyPairList.add(CurrencyPair.ETC_BTC);

//    Example of a Detection print action
//    DetectionActionSelection detectionActionSelection = new DetectionPrintAction();
//    detection.run(currencyPairList, ExchangeList, detectionActionSelection);

//    Example of a Detection log action
//    DetectionActionSelection detectionActionSelection1 = new DetectionLogAction(60000);
//    detection.run(currencyPairList, ExchangeList, detectionActionSelection1);


    }
}

