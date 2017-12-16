package co.codingnomads.bot.arbitrage.service;

import co.codingnomads.bot.arbitrage.model.ActivatedExchange;
import co.codingnomads.bot.arbitrage.model.exchange.ExchangeSpecs;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;

import java.util.ArrayList;


/**
 * Created by Thomas Leruth on 12/13/17
 */
public class ExchangeGetter {

    public ArrayList<ActivatedExchange> getAllSelectedExchangeServices(ArrayList<ExchangeSpecs> selectedExchanges) {
        ArrayList<ActivatedExchange> list = new ArrayList<>();

        for (ExchangeSpecs selected : selectedExchanges) {
            //todo make next line threadable
            Exchange exchange = ExchangeFactory.INSTANCE.createExchange(selected.GetSetupedExchange());

            if (exchange.getAccountService() != null) {
                list.add(new ActivatedExchange(exchange, selected.getTradingMode()));
            }
            else {
                System.out.println(selected.GetSetupedExchange().getExchangeName() + " was not successfully added");
            }
        }
        return list;
    }
}
