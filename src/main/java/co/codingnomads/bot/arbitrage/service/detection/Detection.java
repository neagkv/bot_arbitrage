package co.codingnomads.bot.arbitrage.service.detection;

import co.codingnomads.bot.arbitrage.action.detection.*;
import co.codingnomads.bot.arbitrage.action.detection.selection.DetectionActionSelection;
import co.codingnomads.bot.arbitrage.exception.ExchangeDataException;
import co.codingnomads.bot.arbitrage.exception.WaitTimeException;
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


    /**
     * run method for both detection print and log actions. Determines which action is being run and either prints
     * detection information to the console or log it to a database.
     * @param currencyPairList
     * @param selectedExchanges
     * @param detectionActionSelection
     * @throws IOException
     * @throws InterruptedException
     * @throws WaitTimeException
     * @throws ExchangeDataException
     */
    public void run(ArrayList<CurrencyPair> currencyPairList,
                    ArrayList<ExchangeSpecs> selectedExchanges,
                    DetectionActionSelection detectionActionSelection) throws IOException, InterruptedException, WaitTimeException, ExchangeDataException {

        Boolean logMode = detectionActionSelection instanceof DetectionLogAction;
        Boolean printMode = detectionActionSelection instanceof DetectionPrintAction;

        double tradeValueBase = -1;
        //convert the value of the trade value base to a big decimal
        BigDecimal valueOfTradeValueBase = BigDecimal.valueOf(tradeValueBase);
        //to count how many rounds log has printed into the database
        int logCounter = 1;

        //get all the selected exchanges
        ArrayList<ActivatedExchange> activatedExchanges =
                exchangeGetter.getAllSelectedExchangeServices(selectedExchanges, false);

        //for each differencewrapper in the the difference wrapper list
        //for each currency pair in the currency pair list
        //if the data is not null add it to a new differenceWrapper list
        do {
            ArrayList<DifferenceWrapper> differenceWrapperList = new ArrayList<>();

            for (CurrencyPair currencyPair : currencyPairList) {

                ArrayList<TickerData> listTickerData = exchangeDataGetter.getAllTickerData(
                        activatedExchanges,
                        currencyPair,
                        valueOfTradeValueBase);

                if (listTickerData.size() == 0) {
                    differenceWrapperList.add(new DifferenceWrapper(currencyPair));
                    continue;
                }

                //find the lowest ask and highest bid
                TickerData lowAsk = dataUtil.lowAskFinder(listTickerData);
                TickerData highBid = dataUtil.highBidFinder(listTickerData);

                //find the difference of the highbid and low ask and create a new big decimal
                BigDecimal difference = highBid.getBid().divide(lowAsk.getAsk(), 5, RoundingMode.HALF_EVEN);
                //multiple the difference by 100
                BigDecimal differenceFormatted = difference.add(BigDecimal.valueOf(-1)).multiply(BigDecimal.valueOf(100));

                //add the differenceWrapper to the differenceWrapperList
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
                //if it is a print action, print the differenceWrapperList
                DetectionPrintAction detectionPrintAction = (DetectionPrintAction) detectionActionSelection;
                detectionPrintAction.print(differenceWrapperList);

            }
            if (logMode) {
                //if it is a log action insert the differenceWrapperList into to the database
                //at the set waitTime interval
                //print how many times the differenceWrapper has been inserted into the database
                DetectionLogAction detectionLogAction = (DetectionLogAction) detectionActionSelection;

                int dbInsertWaitTime = detectionLogAction.getWaitInterval();
                if (dbInsertWaitTime >= 60000) {
                    detectionService.insertDetectionRecords(differenceWrapperList);
                    System.out.println("====================================================");
                    System.out.println("====================================================");
                    System.out.println();
                    System.out.println("Inserted detection data into database: Round " + logCounter);
                    System.out.println();
                    System.out.println("====================================================");
                    System.out.println("====================================================");
                    Thread.sleep(detectionLogAction.getWaitInterval());
                    logCounter++;
                }
                else throw  new WaitTimeException ("Please enter a wait time of over 1 minute, to prevent overloading the database");


            }

        } while (logMode); // make it infinite loop if log mode and 1 time if print


    }
}
