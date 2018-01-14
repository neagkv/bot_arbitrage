package co.codingnomads.bot.arbitrage.service.general;

import co.codingnomads.bot.arbitrage.model.detection.DifferenceWrapper;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

/**
 * @author Kevin Neag
 */
@Service
public class DetectionDataUtil {

    /**
     * A method to find the greatest price difference between the selected currency pairs on the selected exchanges for the best
     * arbitrage opportunity.
     * @param differenceWrapperList a list of differenceWrapper information based on the exchanges and currency pairs selected
     * @return The difference wrapper object with the greatest price difference.
     */
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

