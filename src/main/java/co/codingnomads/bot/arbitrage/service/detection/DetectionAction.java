package co.codingnomads.bot.arbitrage.service.detection;

import co.codingnomads.bot.arbitrage.model.arbitrageAction.detection.DifferenceWrapper;

import java.util.ArrayList;

/**
 * Created by Thomas Leruth on 12/18/17
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
