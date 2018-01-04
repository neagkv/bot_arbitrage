package co.codingnomads.bot.arbitrage.service.detection;

import co.codingnomads.bot.arbitrage.model.detectionAction.DifferenceWrapper;
import java.util.ArrayList;

/**
 * Created by Thomas Leruth on 12/18/17
 *
 * Class to define the potential acting behaviors of the detection bot
 */

public class DetectionAction {

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
