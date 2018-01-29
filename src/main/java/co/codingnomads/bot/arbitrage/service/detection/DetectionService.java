package co.codingnomads.bot.arbitrage.service.detection;

import co.codingnomads.bot.arbitrage.mapper.DetectionWrapperMapper;
import co.codingnomads.bot.arbitrage.model.detection.DifferenceWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;


/**
 * @author Kevin Neag
 */


/**
 * A Detection Service class for detection log action
 *
 */
@Service
public class DetectionService {

    @Autowired
    DetectionWrapperMapper mapper;

    /**
     * insertDetectionRecords that takes an array list of differenceWrapperList and
     * uses the DetectionWrapper mapper to submit into the database
     * @param differenceWrapperList
     */
    public void insertDetectionRecords(ArrayList<DifferenceWrapper> differenceWrapperList) {

        for (DifferenceWrapper differenceWrapper : differenceWrapperList) {

            //seperate the currency pair into seperate symbols, Ie ETH_BTC = ETH & BTC
            String currencyPairReformatted = differenceWrapper.getCurrencyPair().toString().replace("/", "");
            //use the mapper to insert the time, currency and other diference wrapper info into the database
            mapper.insert_DifferenceWrapper(differenceWrapper.getTimestamp(), currencyPairReformatted, differenceWrapper.getDifference(),

                    differenceWrapper.getLowAsk(), differenceWrapper.getLowAskExchange(), differenceWrapper.getHighBid(), differenceWrapper.getHighBidExchange());

        }
    }
}

