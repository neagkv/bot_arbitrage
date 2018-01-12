package co.codingnomads.bot.arbitrage.action.detection;

import co.codingnomads.bot.arbitrage.model.detection.DifferenceWrapper;
import java.util.ArrayList;

/**
 * Created by Thomas Leruth on 12/18/17
 *
 * Class to define the potential acting behaviors of the detection bot
 */

public class DetectionAction {

    // Ryan: should these methods be split into their corresponding classes and this class removed?
    // just seeing some differences in the general patterns between this detection package and the
    // arbitrage package above. For instance, why do we have a DetectionAction class here, and no
    // ArbitrageAction class above. See note in DetectionPrintAction

    public void print(ArrayList<DifferenceWrapper> differenceWrapperList) {
        for (DifferenceWrapper differenceWrapper : differenceWrapperList) {
            System.out.println(differenceWrapper.toString());
        }
    }

    //todo implement a proper logger to database
    public void log(ArrayList<DifferenceWrapper> differenceWrapperList) {
        for (DifferenceWrapper differenceWrapper : differenceWrapperList) {
            System.out.println(differenceWrapper.toString());
        }
    }
}
