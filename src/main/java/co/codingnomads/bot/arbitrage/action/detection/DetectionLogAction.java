package co.codingnomads.bot.arbitrage.action.detection;

import co.codingnomads.bot.arbitrage.action.detection.selection.DetectionActionSelection;
import co.codingnomads.bot.arbitrage.model.detection.DifferenceWrapper;
import co.codingnomads.bot.arbitrage.service.detection.DetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

/**
 * Created by Thomas Leruth on 12/18/17
 *
 * POJO class for the information needed to use the log method as acting behavior
 */

@Service
public class DetectionLogAction extends DetectionActionSelection {

    @Autowired
    DetectionService detectionService;


    int waitInterval;

    public int getWaitInterval() {
        return waitInterval;
    }

    public void setWaitInterval(int waitInterval) {
        this.waitInterval = waitInterval;
    }

    public DetectionLogAction(int waitInterval) {
        this.waitInterval = waitInterval;
    }

    public DetectionLogAction() {
    }


    public void log(ArrayList<DifferenceWrapper> differenceWrapperList) {
        for (DifferenceWrapper differenceWrapper : differenceWrapperList) {
            detectionService.insertDetectionRecords(differenceWrapper);
            System.out.println(differenceWrapper.toString());
        }
    }
}
