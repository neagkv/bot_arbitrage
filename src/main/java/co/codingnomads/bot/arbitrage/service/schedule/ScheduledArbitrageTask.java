package co.codingnomads.bot.arbitrage.service.schedule;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * @author Kevin Neag
 */

@Component
public class ScheduledArbitrageTask {

    int count;

    @Scheduled(fixedRate = 60000)
    public void executeCron(){


    }
}

