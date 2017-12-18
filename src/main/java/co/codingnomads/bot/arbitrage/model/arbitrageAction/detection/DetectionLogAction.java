package co.codingnomads.bot.arbitrage.model.arbitrageAction.detection;

/**
 * Created by Thomas Leruth on 12/18/17
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
