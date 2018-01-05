package co.codingnomads.bot.arbitrage.service;

import co.codingnomads.bot.arbitrage.mapper.EmailTimeMapper;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.email.ArbitrageEmailAction;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.email.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;

/**
 * @author Kevin Neag
 */


@Service
public class EmailService {

    @Autowired
    EmailTimeMapper mapper;

    public void insertEmailRecords(Email email){

        mapper.insertEmailRecord(email.getTimeEmailSent(), email.getTO(), email.getSUBJECT());
    }

    public int getTotalCallsToday(String day){
        return mapper.getTotalCallsToday(day);
    }

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
