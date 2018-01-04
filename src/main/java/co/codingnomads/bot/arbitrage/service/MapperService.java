package co.codingnomads.bot.arbitrage.service;

import co.codingnomads.bot.arbitrage.mapper.EmailTimeMapper;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.email.EmailBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Kevin Neag
 */


@Service
public class MapperService {

    @Autowired
    EmailTimeMapper mapper;

    public void insertEmailStuff(EmailBody emailBody){

        mapper.insertEmailTime(emailBody.getCurrentTime());
    }

    public void getLastEmailCallTime(EmailBody emailBody){

        System.out.println("++++++++++++++++++++++++++++++++++++++++");
        mapper.getLastCallTime(emailBody.getCurrentTime());
        System.out.println(emailBody.getCurrentTime());
        System.out.println("++++++++++++++++++++++++++++++++++++++++");
        System.out.println(emailBody.getCurrentTime());
        System.out.println("++++++++++++++++++++++++++++++++++++++++");
    }

}
