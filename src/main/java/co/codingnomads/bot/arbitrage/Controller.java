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
import org.knowm.xchange.currency.CurrencyPair;
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

    // Ryan - general note - I'm sure you're already on the same page, but this file is a bit of a mess

    @Autowired
    Arbitrage arbitrage;

    @Autowired
    Detection detection;

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



    // Ryan: the general manner in which this app is called, and whether or not it is called dynamically with various
    // currency pairs and exchanges etc needs to be ironed out. I'm sure you guys are on that. But as of yet, it appears
    // unfinished in this regard.

    public void runBot() throws IOException, InterruptedException, EmailLimitException, WaitTimeException, ExchangeDataException{

        ArrayList<ExchangeSpecs> ExchangeList = new ArrayList<>();
        ExchangeList.add(new KrakenSpecs()); // internal: good but slow
        ExchangeList.add(new GDAXSpecs()); // internal: good but waiting limit increase

//      do not use anything else than Kraken or GDAX for Arbitrage up to now. Kraken min ETH is 0.02 and GDAX: 0.01
      //ExchangeList.add(new BitfinexSpecs()); // internal: good but waiting limit increase
      //ExchangeList.add(new BittrexSpecs()); // Need Pojo building (internal: all good)
      //ExchangeList.add(new BitstampSpecs()); // need Pojo building but no key (internal: verif)// ExchangeList.add(new PoloniexSpecs()); // need Pojo building and CAPTCHA issue resolving (internal: verif)

        //List of currencyPairs you would like to check, for Detection only
        ArrayList<CurrencyPair> currencyPairList = new ArrayList<>();
        currencyPairList.add(CurrencyPair.ETH_USD);
        currencyPairList.add(CurrencyPair.BTC_USD);
        currencyPairList.add(CurrencyPair.BTC_USD);


//        Example of an Arbitrage trade action
//          arbitrageTradingAction.setArbitrageMargin(1.00);
//          arbitrageTradingAction.setTradeValueBase(0.02);
//          arbitrage.run(
//                CurrencyPair.ETH_USD,
//                ExchangeList,
//                arbitrageTradingAction);


//      Example of an Arbitrage print action that finds the best trading pair every hour
        //set arbitragemargin and optionally set loopIterations, and time interval repeater
        arbitragePrintAction.setArbitrageMargin(1.00);
//        arbitrage.setLoopIterations(5);
//        arbitrage.setTimeIntervalRepeater(5000);
        arbitrage.run(
                    CurrencyPair.ETH_USD,
                    ExchangeList,
                    arbitragePrintAction);



//    Example of an Arbitrage email action
//      arbitrageEmailAction.setArbitrageMargin(1.00);
//      arbitrageEmailAction.getEmail().setTO("neagkv@gmail.com");
//      emailService.insertEmailRecords(arbitrageEmailAction.getEmail());
//      arbitrage.run(
//                CurrencyPair.BTC_USD,
//                ExchangeList,
//                arbitrageEmailAction);


//    Example of a Detection print action
//      DetectionActionSelection detectionActionSelection = new DetectionPrintAction();
//      detection.run(currencyPairList, ExchangeList, detectionActionSelection);

//      Example of a Detection log action
//      DetectionActionSelection detectionActionSelection1 = new DetectionLogAction(60000);
//      detection.run(currencyPairList, ExchangeList, detectionActionSelection1);


    }
}

