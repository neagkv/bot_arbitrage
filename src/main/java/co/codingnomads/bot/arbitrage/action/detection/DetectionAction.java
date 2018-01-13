package co.codingnomads.bot.arbitrage.action.detection;


import co.codingnomads.bot.arbitrage.detectionAction.DifferenceWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.codingnomads.bot.arbitrage.model.detection.DifferenceWrapper;
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


    // Ryan: should these methods be split into their corresponding classes and this class removed?
    // just seeing some differences in the general patterns between this detection package and the
    // arbitrage package above. For instance, why do we have a DetectionAction class here, and no
    // ArbitrageAction class above. See note in DetectionPrintAction

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
