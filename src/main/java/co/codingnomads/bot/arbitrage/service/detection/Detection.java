package co.codingnomads.bot.arbitrage.service.detection;

import co.codingnomads.bot.arbitrage.model.ActivatedExchange;
import co.codingnomads.bot.arbitrage.model.BidAsk;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.ArbitrageEmailAction;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.ArbitragePrintAction;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.ArbitrageTradingAction;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.detection.DetectionActionSelection;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.detection.DetectionLogAction;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.detection.DetectionPrintAction;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.detection.DifferenceWrapper;
import co.codingnomads.bot.arbitrage.model.exchange.ExchangeSpecs;
import co.codingnomads.bot.arbitrage.service.DataUtil;
import co.codingnomads.bot.arbitrage.service.ExchangeDataGetter;
import co.codingnomads.bot.arbitrage.service.ExchangeGetter;
import org.knowm.xchange.currency.CurrencyPair;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Created by Thomas Leruth on 12/18/17
 */

public class Detection {

    public void run(ArrayList<CurrencyPair> currencyPairList,
                    ArrayList<ExchangeSpecs> selectedExchanges,
                    DetectionActionSelection detectionActionSelection) throws IOException, InterruptedException {

        Boolean logMode = detectionActionSelection instanceof DetectionLogAction;
        double tradeValueBase = -1;
        int waitInterval = 0;
        if (logMode) waitInterval = ((DetectionLogAction) detectionActionSelection).getWaitInterval();

        ExchangeGetter exchangeGetter = new ExchangeGetter();

        ArrayList<ActivatedExchange> activatedExchanges = exchangeGetter.getAllSelectedExchangeServices(selectedExchanges, false);

        // todo autowire it
        ExchangeDataGetter exchangeDataGetter = new ExchangeDataGetter();
        // todo autowire it
        DetectionAction detectionAction = new DetectionAction();
        // todo autowire it
        DataUtil dataUtil = new DataUtil();



        do {
            ArrayList<DifferenceWrapper> differenceWrapperList = new ArrayList<>();

            for (CurrencyPair currencyPair : currencyPairList) {

                ArrayList<BidAsk> listBidAsk = exchangeDataGetter.getAllBidAsk(
                        activatedExchanges,
                        currencyPair,
                        tradeValueBase);

                if (listBidAsk.size() == 0) {
                    differenceWrapperList.add(new DifferenceWrapper(currencyPair));
                    continue;
                }

                BidAsk lowAsk = dataUtil.lowAskFinder(listBidAsk);
                BidAsk highBid = dataUtil.highBidFinder(listBidAsk);

                //todo make it  etc etc
                BigDecimal difference = highBid.getBid().divide(lowAsk.getAsk(), 5, RoundingMode.HALF_EVEN);

                differenceWrapperList.add(new DifferenceWrapper(
                        currencyPair,
                        difference,
                        lowAsk.getAsk(),
                        lowAsk.getExchange().getDefaultExchangeSpecification().getExchangeName(),
                        highBid.getBid(),
                        highBid.getExchange().getDefaultExchangeSpecification().getExchangeName()));

                Thread.sleep(1000); // to avoid API rate limit issue
            }
            if (!logMode) {
                detectionAction.print(differenceWrapperList);
            }
            if (logMode) {
                detectionAction.log(differenceWrapperList);
            }
            Thread.sleep(waitInterval);
        } while (logMode); // make it infinite loop if log mode and 1 time if print

    }
}
