package co.codingnomads.bot.arbitrage.service;

import co.codingnomads.bot.arbitrage.mapper.EmailTimeMapper;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.email.EmailSendTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Kevin Neag
 */


@Service
public class MapperService {

    @Autowired
    EmailTimeMapper mapper;

    public void insertTimeOfCall(EmailSendTime emailSendTime){

        System.out.println(emailSendTime.getTimeofcall());

        mapper.insertValue(emailSendTime.getTimeofcall());

    }

    public void getALL(){
        mapper.getALL();
    }
}
