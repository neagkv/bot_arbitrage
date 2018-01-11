package co.codingnomads.bot.arbitrage.service.detection;

import co.codingnomads.bot.arbitrage.model.ActivatedExchange;
import co.codingnomads.bot.arbitrage.model.TickerData;
import co.codingnomads.bot.arbitrage.detectionAction.DetectionActionSelection;
import co.codingnomads.bot.arbitrage.detectionAction.DetectionLogAction;
import co.codingnomads.bot.arbitrage.detectionAction.DetectionPrintAction;
import co.codingnomads.bot.arbitrage.detectionAction.DifferenceWrapper;
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
    ExchangeDataGetter exchangeDataGetter = new ExchangeDataGetter();

    @Autowired
    DetectionAction detectionAction = new DetectionAction();

    @Autowired
    DataUtil dataUtil = new DataUtil();

    @Autowired
    DetectionDataUtil dectionDataUtil = new DetectionDataUtil();

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
                detectionAction.print(differenceWrapperList);
                DifferenceWrapper differenceWrapper = dectionDataUtil.lowestDifferenceFinder(differenceWrapperList);
                System.out.println(differenceWrapper);
                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

            }
            if (logMode) {
                detectionAction.log(differenceWrapperList);
            }
            //Thread.sleep(waitInterval);
        } while (printMode); // make it infinite loop if log mode and 1 time if print

    }
}
