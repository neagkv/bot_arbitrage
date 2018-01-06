package co.codingnomads.bot.arbitrage.service.detection;

import co.codingnomads.bot.arbitrage.model.ActivatedExchange;
import co.codingnomads.bot.arbitrage.model.TickerData;
import co.codingnomads.bot.arbitrage.model.detectionAction.DetectionActionSelection;
import co.codingnomads.bot.arbitrage.model.detectionAction.DetectionLogAction;
import co.codingnomads.bot.arbitrage.model.detectionAction.DetectionPrintAction;
import co.codingnomads.bot.arbitrage.model.detectionAction.DifferenceWrapper;
import co.codingnomads.bot.arbitrage.model.exchange.ExchangeSpecs;
import co.codingnomads.bot.arbitrage.service.general.DataUtil;
import co.codingnomads.bot.arbitrage.service.general.ExchangeDataGetter;
import co.codingnomads.bot.arbitrage.service.general.ExchangeGetter;
import org.knowm.xchange.currency.CurrencyPair;
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

    // todo autowire it
    ExchangeDataGetter exchangeDataGetter = new ExchangeDataGetter();
    // todo autowire it
    DetectionAction detectionAction = new DetectionAction();
    // todo autowire it
    DataUtil dataUtil = new DataUtil();

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

                Thread.sleep(1000); // to avoid API rate limit issue
            }
            if (printMode) {
                detectionAction.print(differenceWrapperList);
            }
            if (logMode) {
                detectionAction.log(differenceWrapperList);
            }
            Thread.sleep(waitInterval);
        } while (logMode); // make it infinite loop if log mode and 1 time if print

    }
}
