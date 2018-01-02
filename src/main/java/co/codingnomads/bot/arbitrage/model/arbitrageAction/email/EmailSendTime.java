package co.codingnomads.bot.arbitrage.model.arbitrageAction.email;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Kevin Neag
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailSendTime {


     int timeofcall = 33;

    public EmailSendTime() {

    }

    public int getTimeofcall() {
        return timeofcall;
    }

    public void setTimeofcall(int timeofcall) {
        this.timeofcall = timeofcall;
    }

    @Override
    public String toString() {
        return "EmailSendTime{" +
                "timeofcall=" + timeofcall +
                '}';
    }
}




