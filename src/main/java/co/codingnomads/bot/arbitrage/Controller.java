package co.codingnomads.bot.arbitrage;

import co.codingnomads.bot.arbitrage.model.ArbitrageActionSelection;
import co.codingnomads.bot.arbitrage.model.ExchangeDetailsEnum;
import co.codingnomads.bot.arbitrage.service.WIP;
import org.knowm.xchange.currency.CurrencyPair;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Thomas Leruth on 12/11/17
 */

public class Controller {

    // issue with Poloniex (can't create an instance - Bug reported, due to Captcha on their endpoint)
    public static void main(String[] args) throws IOException {
        WIP wip = new WIP();

        ArrayList<ExchangeDetailsEnum> selectedExchanges = new ArrayList<>();
        selectedExchanges.add(ExchangeDetailsEnum.GDAXEXCHANGE); // internal: waiting limit increase
        selectedExchanges.add(ExchangeDetailsEnum.KRAKENEXCHANGE); // internal: good limit but slow
        selectedExchanges.add(ExchangeDetailsEnum.BITSTAMPEXCHANGE); // internal: waiting limit increase
        selectedExchanges.add(ExchangeDetailsEnum.BITFINEXEXCHANGE); // internal: waiting limit increase
        selectedExchanges.add(ExchangeDetailsEnum.BITTREXEXCHANGE); // internal all good
//        selectedExchanges.add(ExchangeDetailsEnum.POLONIEXEXCHANGE); // internal need to wait for verification

        ArbitrageActionSelection arbitrageActionSelection = new ArbitrageActionSelection(true,false, false);
        wip.test(CurrencyPair.ETH_EUR,selectedExchanges,1.02, arbitrageActionSelection);
    }

}

