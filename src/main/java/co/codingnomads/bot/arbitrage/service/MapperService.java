package co.codingnomads.bot.arbitrage.service;

import co.codingnomads.bot.arbitrage.mapper.EmailTimeMapper;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.email.ArbitrageEmailAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Kevin Neag
 */


@Service
public class MapperService {

    @Autowired
    EmailTimeMapper mapper;

    public void insertEmailRecords(ArbitrageEmailAction emailRecords){

        mapper.insertEmailRecord(emailRecords.getTimeEmailSent(), emailRecords.getTO(), emailRecords.printSubject());
    }

    public String getLastEmailCallTime(){

        return mapper.getLastCallTime();

    }


}
