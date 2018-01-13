package co.codingnomads.bot.arbitrage.service.detection;

import co.codingnomads.bot.arbitrage.action.detection.*;
import co.codingnomads.bot.arbitrage.action.detection.selection.DetectionActionSelection;
import co.codingnomads.bot.arbitrage.model.detection.DifferenceWrapper;
import co.codingnomads.bot.arbitrage.model.exchange.ActivatedExchange;
import co.codingnomads.bot.arbitrage.model.ticker.TickerData;
import co.codingnomads.bot.arbitrage.exchange.ExchangeSpecs;
import co.codingnomads.bot.arbitrage.service.general.DataUtil;
import co.codingnomads.bot.arbitrage.service.general.DetectionDataUtil;
import co.codingnomads.bot.arbitrage.service.general.ExchangeDataGetter;
import co.codingnomads.bot.arbitrage.service.general.ExchangeGetter;
import org.knowm.xchange.currency.CurrencyPair;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Created by Thomas Leruth on 12/18/17
 *
 * Detection bot class
 */

public class Detection {

    @Autowired
    ExchangeDataGetter exchangeDataGetter;

    @Autowired
    DataUtil dataUtil;

    @Autowired
    DetectionDataUtil dectionDataUtil;


    // Ryan: this doesn't need to be autowired, nor does it need to be a class variable. It can be defined once in the run method
    // and assigned in the if statement where it is used below. I think autowiring might break it. See Arbitrage.java
    // for example on line ~107
    // todo autowire it
    //DetectionAction detectionAction = new DetectionAction();

    // Ryan: missing comments - a method like this should have a nice comment explaining what it does
    // a little in-line commenting in the method is also very nice to have

    public void run(ArrayList<CurrencyPair> currencyPairList,
                    ArrayList<ExchangeSpecs> selectedExchanges,
                    DetectionActionSelection detectionActionSelection) throws IOException, InterruptedException {

        Boolean logMode = detectionActionSelection instanceof DetectionLogAction;
        Boolean printMode = detectionActionSelection instanceof DetectionPrintAction;

        double tradeValueBase = -1;
        int waitInterval = 0;
        if (logMode) waitInterval = ((DetectionLogAction) detectionActionSelection).getWaitInterval();

        ExchangeGetter exchangeGetter = new ExchangeGetter();

        ArrayList<ActivatedExchange> activatedExchanges =
                exchangeGetter.getAllSelectedExchangeServices(selectedExchanges, false);

        do {
            ArrayList<DifferenceWrapper> differenceWrapperList = new ArrayList<>();

            for (CurrencyPair currencyPair : currencyPairList) {

                ArrayList<TickerData> listTickerData = exchangeDataGetter.getAllTickerData(
                        activatedExchanges,
                        currencyPair,
                        tradeValueBase);

                if (listTickerData.size() == 0) {
                    differenceWrapperList.add(new DifferenceWrapper(currencyPair));
                    continue;
                }

                TickerData lowAsk = dataUtil.lowAskFinder(listTickerData);
                TickerData highBid = dataUtil.highBidFinder(listTickerData);


                BigDecimal difference = highBid.getBid().divide(lowAsk.getAsk(), 5, RoundingMode.HALF_EVEN);
                BigDecimal differenceFormatted = difference.add(BigDecimal.valueOf(-1)).multiply(BigDecimal.valueOf(100));

                differenceWrapperList.add(new DifferenceWrapper(
                        currencyPair,
                        differenceFormatted,
                        lowAsk.getAsk(),
                        lowAsk.getExchange().getDefaultExchangeSpecification().getExchangeName(),
                        highBid.getBid(),
                        highBid.getExchange().getDefaultExchangeSpecification().getExchangeName()));

                //Thread.sleep(1000); // to avoid API rate limit issue
            }
            if (printMode) {
                // Ryan: you might want to do the following in a very similar way as to line ~111 in Arbitrage.java
                // this speaks to the same notes I made in DetectionAction.java and DetectionPrintAction
                // The arbitrage actions and the detection actions are being handled slwightly differently.
                // I think the way the arbitrage actions are working is the better way.
                detectionAction.print(differenceWrapperList);
                DifferenceWrapper differenceWrapper = dectionDataUtil.lowestDifferenceFinder(differenceWrapperList);
                System.out.println(differenceWrapper);
                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

            }
            if (logMode) {
                // Ryan: see above
                detectionAction.log(differenceWrapperList);
            }

            //Thread.sleep(waitInterval);
        } while (printMode); // make it infinite loop if log mode and 1 time if print


    }
}
