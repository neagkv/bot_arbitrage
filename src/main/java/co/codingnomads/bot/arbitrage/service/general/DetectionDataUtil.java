package co.codingnomads.bot.arbitrage.service.general;

import co.codingnomads.bot.arbitrage.model.detection.DifferenceWrapper;
import co.codingnomads.bot.arbitrage.model.ticker.TickerData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author Kevin Neag
 */
@Service
public class DetectionDataUtil {

//    Add a new method in the print that run to the object
//    generated for each pairs and find the one with the best difference and only
//    print that one. Check the method find the best bid and best Ask, it is very similar


    public DifferenceWrapper bestDifferenceFinder(ArrayList<DifferenceWrapper> differenceWrapperList) {
        int highIndex = 0;
        if (differenceWrapperList.size() > 1) {
            for (int i = 1; i < differenceWrapperList.size(); i++) {
                if (differenceWrapperList.get(highIndex).getDifference().compareTo(differenceWrapperList.get(i).getDifference()) < 0) {
                    highIndex = i;
                }
            }
        }
        return differenceWrapperList.get(highIndex);
    }

    }

