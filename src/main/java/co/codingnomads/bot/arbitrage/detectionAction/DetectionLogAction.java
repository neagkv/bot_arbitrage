package co.codingnomads.bot.arbitrage.detectionAction;

/**
 * Created by Thomas Leruth on 12/18/17
 *
 * POJO class for the information needed to use the log method as acting behavior
 */

public class DetectionLogAction extends DetectionActionSelection {

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
}
