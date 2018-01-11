package co.codingnomads.bot.arbitrage.service.detection;

import co.codingnomads.bot.arbitrage.detectionAction.DifferenceWrapper;
import co.codingnomads.bot.arbitrage.service.general.DectionDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by Thomas Leruth on 12/18/17
 *
 * Class to define the potential acting behaviors of the detection bot
 */

public class DetectionAction {


    ArrayList<DifferenceWrapper> myList;

    public void setMyList(ArrayList<DifferenceWrapper> myList) {
        this.myList = myList;
    }

    public ArrayList<DifferenceWrapper> print(ArrayList<DifferenceWrapper> differenceWrapperList) {
        for (DifferenceWrapper differenceWrapper : differenceWrapperList) {
//            System.out.println(dectionDataUtil.lowestDifferenceFinder(differenceWrapperList));
//            System.out.println("********************************************");
            System.out.println(differenceWrapper.toString());
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        }
        System.out.println("jghjgjhgjgjgjgjg");
        System.out.println(myList.toString());
        return myList;
    }




    //todo implement a proper logger to database
    public void log(ArrayList<DifferenceWrapper> differenceWrapperList) {
        for (DifferenceWrapper differenceWrapper : differenceWrapperList) {
            detectionService.insertDetectionRecords(differenceWrapper);
            //System.out.println(differenceWrapper.toString());
        }
    }
}
