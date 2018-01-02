package co.codingnomads.bot.arbitrage.model.arbitrageAction.email;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Kevin Neag
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeOfCall {

    static final int timeOfCall = 33;


    public TimeOfCall() {
    }

    public int getTimeOfCall() {
        return timeOfCall;
    }

    public void setTimeOfCall(int timeOfCall) {
        timeOfCall = timeOfCall;
    }

    @Override
    public String toString() {
        return "TimeOfCall{}";
    }
}
