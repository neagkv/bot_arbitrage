package co.codingnomads.bot.arbitrage.configuration;

import co.codingnomads.bot.arbitrage.service.DataUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Thomas Leruth on 12/14/17
 */
@Configuration
public class BeanConfiguration {

    @Bean
    DataUtil dataUtil() {
        return new DataUtil();
    }
}
