package co.codingnomads.bot.arbitrage.service;

import co.codingnomads.bot.arbitrage.mapper.EmailTimeMapper;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.email.EmailAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Kevin Neag
 */


@Service
public class MapperService {

    @Autowired
    EmailTimeMapper mapper;

    public void insertEmailStuff(EmailAction email){

        mapper.insertEmailRecord(email.getTimeEmailSent());
    }

    public String getLastEmailCallTime(){

        return mapper.getLastCallTime();

    }


}
