package co.codingnomads.bot.arbitrage;

import co.codingnomads.bot.arbitrage.action.arbitrage.ArbitragePrintAction;
import co.codingnomads.bot.arbitrage.action.arbitrage.ArbitrageTradingAction;
import co.codingnomads.bot.arbitrage.action.arbitrage.ArbitrageEmailAction;
import co.codingnomads.bot.arbitrage.action.detection.DetectionLogAction;
import co.codingnomads.bot.arbitrage.action.detection.DetectionPrintAction;
import co.codingnomads.bot.arbitrage.action.detection.selection.DetectionActionSelection;
import co.codingnomads.bot.arbitrage.exception.EmailLimitException;
import co.codingnomads.bot.arbitrage.exchange.*;
import co.codingnomads.bot.arbitrage.service.detection.Detection;
import co.codingnomads.bot.arbitrage.service.detection.DetectionService;
import co.codingnomads.bot.arbitrage.service.email.EmailService;
import co.codingnomads.bot.arbitrage.service.arbitrage.Arbitrage;
import co.codingnomads.bot.arbitrage.service.general.ExchangeDataGetter;
import co.codingnomads.bot.arbitrage.service.general.ExchangeGetter;
import org.knowm.xchange.currency.CurrencyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Thomas Leruth on 12/11/17
 * <p>
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

    public void runBot() throws IOException, InterruptedException, EmailLimitException {

        ArrayList<ExchangeSpecs> ExchangeList = new ArrayList<>();
        ExchangeList.add(new KrakenSpecs()); // internal: good but slow
        ExchangeList.add(new GDAXSpecs()); // internal: good but waiting limit increase

//      do not use anything else than Kraken or GDAX for Arbitrage up to now. Kraken min ETH is 0.02 and GDAX: 0.01
//      ExchangeList.add(new BitfinexSpecs()); // internal: good but waiting limit increase
//      ExchangeList.add(new BittrexSpecs()); // Need Pojo building (internal: all good)
//      ExchangeList.add(new BitstampSpecs()); // need Pojo building but no key (internal: verif)// ExchangeList.add(new PoloniexSpecs()); // need Pojo building and CAPTCHA issue resolving (internal: verif)

        ArrayList<CurrencyPair> currencyPairList = new ArrayList<>();
        currencyPairList.add(CurrencyPair.ETH_USD);
        currencyPairList.add(CurrencyPair.BTC_EUR);
        currencyPairList.add(CurrencyPair.BTC_USD);


//        Example of an Arbitrage trade action
//        arbitrageTradingAction.setArbitrageMargin(1.01);
//        arbitrageTradingAction.setTradeValueBase(0.02);
//        arbitrage.run(
//                CurrencyPair.ETH_EUR,
//                ExchangeList,
//                arbitrageTradingAction);


//        Example of an Arbitrage print action that finds the best trading pair every hour
//        arbitragePrintAction.setArbitrageMargin(1.01);
//        arbitrage.run(
//                CurrencyPair.ETH_EUR,
//                ExchangeList,
//                arbitragePrintAction);

//        Example Of an Arbitrage email action
//        arbitrageEmailAction.setArbitrageMargin(1.03);
//        arbitrageEmailAction.getEmail().setTO("neagkv@gmail.com");
//        emailService.insertEmailRecords(arbitrageEmailAction.getEmail());
//        arbitrage.run(
//                CurrencyPair.ETH_EUR,
//                ExchangeList,
//                arbitrageEmailAction);


//        Example of a Detection print action
//        DetectionActionSelection detectionActionSelection = new DetectionPrintAction();
//        detection.run(currencyPairList, ExchangeList, detectionActionSelection);

//        Detection log action not working yet
//        DetectionActionSelection detectionActionSelection = new DetectionLogAction();
//        detection.run(currencyPairList, ExchangeList, detectionActionSelection);


    }
}

