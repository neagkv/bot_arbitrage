package co.codingnomads.bot.arbitrage.service.detection;

import co.codingnomads.bot.arbitrage.detectionAction.DifferenceWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by Thomas Leruth on 12/18/17
 *
 * Class to define the potential acting behaviors of the detection bot
 */
@Service
public class DetectionAction {

    @Autowired
    DetectionService detectionService = new DetectionService();

    ArrayList<DifferenceWrapper> myList;

    public void setMyList(ArrayList<DifferenceWrapper> myList) {
        this.myList = myList;
    }

    public ArrayList<DifferenceWrapper> print(ArrayList<DifferenceWrapper> differenceWrapperList) {
        for (DifferenceWrapper differenceWrapper : differenceWrapperList) {
//      System.out.println(dectionDataUtil.lowestDifferenceFinder(differenceWrapperList));
            myList = differenceWrapperList;
        }
        System.out.println(myList.toString());
        return myList;
    }


    //todo implement a proper logger to database
    public void log(ArrayList<DifferenceWrapper> differenceWrapperList) {
        for (DifferenceWrapper differenceWrapper : differenceWrapperList) {
            detectionService.insertDetectionRecords(differenceWrapper);
            System.out.println(differenceWrapper.toString());
        }
    }
}
