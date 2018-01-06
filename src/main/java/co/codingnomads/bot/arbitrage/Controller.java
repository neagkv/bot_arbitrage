package co.codingnomads.bot.arbitrage;

import co.codingnomads.bot.arbitrage.model.arbitrageAction.ArbitragePrintAction;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.ArbitrageTradingAction;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.email.ArbitrageEmailAction;
import co.codingnomads.bot.arbitrage.model.detectionAction.DetectionActionSelection;
import co.codingnomads.bot.arbitrage.model.detectionAction.DetectionPrintAction;
import co.codingnomads.bot.arbitrage.model.exceptions.EmailLimitException;
import co.codingnomads.bot.arbitrage.model.exchange.*;
import co.codingnomads.bot.arbitrage.service.EmailService;
import co.codingnomads.bot.arbitrage.service.arbitrage.Arbitrage;
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

    @Autowired
    Arbitrage arbitrage;

    @Autowired
    ArbitrageTradingAction arbitrageTradingAction;

    @Autowired
    EmailService emailService;

    @Autowired
    ArbitrageEmailAction arbitrageEmailAction;

    @Autowired
    ArbitragePrintAction arbitragePrintAction;

    public void runBot() throws IOException, InterruptedException, EmailLimitException {

        ArrayList<ExchangeSpecs> ExchangeList = new ArrayList<>();

        ExchangeList.add(new KrakenSpecs()); // internal: good but slow
        ExchangeList.add(new GDAXSpecs()); // internal: good but waiting limit increase

//      do not use anything else than Kraken or GDAX for Arbitrage up to now. Kraken min ETH is 0.02 and GDAX: 0.01
//      ExchangeList.add(new BitfinexSpecs()); // internal: good but waiting limit increase
//      ExchangeList.add(new BittrexSpecs()); // Need Pojo building (internal: all good)
//      ExchangeList.add(new BitstampSpecs()); // need Pojo building but no key (internal: verif)// ExchangeList.add(new PoloniexSpecs()); // need Pojo building and CAPTCHA issue resolving (internal: verif)

//        Example of a trade action
//        arbitrageTradingAction.setArbitrageMargin(1.01);
//        arbitrageTradingAction.setTradeValueBase(0.02);
//        arbitrage.run(
//                CurrencyPair.ETH_EUR,
//                ExchangeList,
//                arbitrageTradingAction);


//        Example of a print action
//        arbitragePrintAction.setArbitrageMargin(1.01);
//        arbitrage.run(
//                CurrencyPair.ETH_EUR,
//                ExchangeList,
//                arbitragePrintAction);

//        Example Of a email action
//        arbitrageEmailAction.setArbitrageMargin(1.03);
//        arbitrageEmailAction.getEmail().setTO("neagkv@gmail.com");
//        arbitrage.run(
//                CurrencyPair.ETH_EUR,
//                ExchangeList,
//                arbitrageEmailAction);


//      start for detection

        ArrayList<CurrencyPair> currencyPairList = new ArrayList<>();
        currencyPairList.add(CurrencyPair.BCH_EUR);
        currencyPairList.add(CurrencyPair.ETH_EUR);
        currencyPairList.add(CurrencyPair.BTC_EUR);

        emailService.insertEmailRecords(arbitrageEmailAction.getEmail());

         DetectionActionSelection detectionActionSelection = new DetectionPrintAction();
        //DetectionActionSelection detectionActionSelection = new DetectionLogAction(4000);

        // detection.run(currencyPairList, ExchangeList, detectionActionSelection);
//        end for detection

//        ExchangeList.add(new GDAXSpecs("qqq", "", "")); // internal: good but waiting limit increase
//        ExchangeList.add(new KrakenSpecs("bbbb", "dadad")); // internal: good but slow
//        ExchangeList.add(new BitfinexSpecs()); // internal: good but waiting limit increase
//        ExchangeList.add(new BittrexSpecs()); // Need Pojo building (internal: all good)
//        ExchangeList.add(new BitstampSpecs()); // need Pojo building but no key (internal: verif)
//        ExchangeList.add(new PoloniexSpecs()); // need Pojo building and CAPTCHA issue resolving (internal: verif)


//        start for detection
        //  Detection detection = new Detection();

        // ArrayList<CurrencyPair> currencyPairList = new ArrayList<>();
//        currencyPairList.add(CurrencyPair.BCH_EUR);
        //currencyPairList.add(CurrencyPair.ETH_EUR);
//        currencyPairList.add(CurrencyPair.BTC_EUR);

        //DetectionActionSelection detectionActionSelection = new DetectionPrintAction();
//        DetectionActionSelection detectionActionSelection = new DetectionLogAction(4000);
//
//        detection.run(currencyPairList, ExchangeList, detectionActionSelection);
//        end for detection

    }


}

