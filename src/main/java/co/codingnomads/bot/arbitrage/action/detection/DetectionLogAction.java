package co.codingnomads.bot.arbitrage.action.detection;

import co.codingnomads.bot.arbitrage.action.detection.selection.DetectionActionSelection;
import co.codingnomads.bot.arbitrage.mapper.DetectionWrapperMapper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Thomas Leruth on 12/18/17
 *
 * POJO class for the information needed to use the log method as acting behavior
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Service
public class DetectionLogAction extends DetectionActionSelection {

    @Autowired
    DetectionWrapperMapper mapper;


    int waitInterval;

    public DetectionLogAction() {
    }

    public DetectionLogAction(int waitInterval) {
        this.waitInterval = waitInterval;
    }

    public int getWaitInterval() {
        return waitInterval;
    }

    public void setWaitInterval(int waitInterval) {
        this.waitInterval = waitInterval;
    }
}
