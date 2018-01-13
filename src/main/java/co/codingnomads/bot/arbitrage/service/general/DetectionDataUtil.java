package co.codingnomads.bot.arbitrage.service.general;

import co.codingnomads.bot.arbitrage.model.detection.DifferenceWrapper;
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


        public DifferenceWrapper lowestDifferenceFinder(ArrayList<DifferenceWrapper> differenceWrapperlist) {
            int lowIndex = 0;
            if (differenceWrapperlist.size() > 1) {
                for (int i = 1; i < differenceWrapperlist.size(); i++) {
                    if (differenceWrapperlist.get(lowIndex).getDifference().compareTo(differenceWrapperlist.get(i).getDifference()) > 0) {
                        lowIndex = i;
                    }
                }
            }
            return differenceWrapperlist.get(lowIndex);
        }
    }

