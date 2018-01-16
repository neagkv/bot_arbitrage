package co.codingnomads.bot.arbitrage.service.detection;

import co.codingnomads.bot.arbitrage.action.detection.*;
import co.codingnomads.bot.arbitrage.action.detection.selection.DetectionActionSelection;
import co.codingnomads.bot.arbitrage.exception.WaitTimeException;
import co.codingnomads.bot.arbitrage.mapper.DetectionWrapperMapper;
import co.codingnomads.bot.arbitrage.model.detection.DifferenceWrapper;
import co.codingnomads.bot.arbitrage.model.exchange.ActivatedExchange;
import co.codingnomads.bot.arbitrage.model.ticker.TickerData;
import co.codingnomads.bot.arbitrage.exchange.ExchangeSpecs;
import co.codingnomads.bot.arbitrage.service.general.DataUtil;
import co.codingnomads.bot.arbitrage.service.general.ExchangeDataGetter;
import co.codingnomads.bot.arbitrage.service.general.ExchangeGetter;
import org.knowm.xchange.currency.CurrencyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Created by Thomas Leruth on 12/18/17
 *
 * Detection bot class
 */
@Service
public class Detection {

    @Autowired
    DetectionService detectionService;

    ExchangeDataGetter exchangeDataGetter = new ExchangeDataGetter();

    ExchangeGetter exchangeGetter = new ExchangeGetter();

    DataUtil dataUtil = new DataUtil();


    public void run(ArrayList<CurrencyPair> currencyPairList,
                    ArrayList<ExchangeSpecs> selectedExchanges,
                    DetectionActionSelection detectionActionSelection) throws IOException, InterruptedException, WaitTimeException {

        Boolean logMode = detectionActionSelection instanceof DetectionLogAction;
        Boolean printMode = detectionActionSelection instanceof DetectionPrintAction;

        double tradeValueBase = -1;
        int waitInterval;
        //if (logMode) waitInterval = ((DetectionLogAction) detectionActionSelection).getWaitInterval();

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

                DetectionPrintAction detectionPrintAction = (DetectionPrintAction) detectionActionSelection;
                detectionPrintAction.print(differenceWrapperList);

            }
            if (logMode) {

                DetectionLogAction detectionLogAction = (DetectionLogAction) detectionActionSelection;
                detectionService.insertDetectionRecords(differenceWrapperList);
                System.out.println("Inserted difference wrapper into the database");
                Thread.sleep(detectionLogAction.getWaitInterval());


            }

        } while (logMode); // make it infinite loop if log mode and 1 time if print


    }
}
