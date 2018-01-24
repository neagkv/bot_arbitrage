package co.codingnomads.bot.arbitrage.service.detection;

import co.codingnomads.bot.arbitrage.mapper.DetectionWrapperMapper;
import co.codingnomads.bot.arbitrage.model.detection.DifferenceWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;


/**
 * @author Kevin Neag
 */

@Service
public class DetectionService {

    @Autowired
    DetectionWrapperMapper mapper;

    public void insertDetectionRecords(ArrayList<DifferenceWrapper> differenceWrapperList) {

        for (DifferenceWrapper differenceWrapper : differenceWrapperList) {

            String currencyPairReformatted = differenceWrapper.getCurrencyPair().toString().replace("/", "");

            mapper.insert_DifferenceWrapper(differenceWrapper.getTimestamp(), currencyPairReformatted, differenceWrapper.getDifference(),

                    differenceWrapper.getLowAsk(), differenceWrapper.getLowAskExchange(), differenceWrapper.getHighBid(), differenceWrapper.getHighBidExchange());

        }
    }
}

