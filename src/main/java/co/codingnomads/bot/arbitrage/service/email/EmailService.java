package co.codingnomads.bot.arbitrage.service.email;

import co.codingnomads.bot.arbitrage.mapper.EmailTimeMapper;
import co.codingnomads.bot.arbitrage.model.email.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Kevin Neag
 */


@Service
public class EmailService {

    @Autowired
    EmailTimeMapper mapper;

    /**
     * Takes the autowired mapper from the EmailTimeMapper Class and
     * passes it the getTimeEmailSent, getTo and getSUBJECT so that it can
     * insert the variables into the database.
     * @param email
     */
    public void insertEmailRecords(Email email){

        mapper.insertEmailRecord(email.getTimeEmailSent(), email.getTO(), email.getSUBJECT());
    }

    /**
     * Takes the autowired mapper from the EmailTImeMapper Class and passes it the
     * getTotalCallsToday method in order to find how many email's have been sent in the
     * current day
     * @param day -today's date in yyyy-MM-dd format
     * @return number of emails made for the current day
     */
    public int getTotalCallsToday(String day){
        return mapper.getTotalCallsToday(day);
    }

    /**
     * Method to calculate if the email is under the rate limit,
     * AWS SES allows for 200 free emails per today, the method
     * compares the number of emails sent today with 200
     * @return false if less than 200 emails have been sent for today,
     * and true if over 200 emails have been sent for the day.
     */
    public boolean underEmailLimit(){

        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd");

        String currentDay = sdf.format(dt);

        int count = getTotalCallsToday(currentDay);

        if(count >= 200){

            return false;
        }

        return true;

    }


}
