package co.codingnomads.bot.arbitrage;

import co.codingnomads.bot.arbitrage.model.ArbitrageActionSelection;
import co.codingnomads.bot.arbitrage.model.exchange.*;
import co.codingnomads.bot.arbitrage.service.Arbitrage;
import org.knowm.xchange.currency.CurrencyPair;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Thomas Leruth on 12/11/17
 */

/**
 * Controller for running the arbitrage bot
 */
public class Controller {

    // todo trading mode rework, trading allowed from Activted exchange work
    public static void main(String[] args) throws IOException, InterruptedException {
        Arbitrage arbitrage = new Arbitrage();

        ArrayList<ExchangeSpecs> selectedExchanges = new ArrayList<>();
        selectedExchanges.add(new KrakenSpecs()); // internal: good but slow
        selectedExchanges.add(new GDAXSpecs()); // internal: good but waiting limit increase
//        selectedExchanges.add(new BitfinexSpecs()); // internal: good but waiting limit increase
//        selectedExchanges.add(new BittrexSpecs()); // Need Pojo building (internal: all good)
//        selectedExchanges.add(new PoloniexSpecs()); // need Pojo building and CAPTCHA issue resolving (internal: verif)
//        selectedExchanges.add(new BitstampSpecs()); // need Pojo building but no key (internal: verif)

        ArbitrageActionSelection arbitrageActionSelection = new ArbitrageActionSelection(true,false, false);
        arbitrage.run(
                CurrencyPair.ETH_EUR,
                selectedExchanges,
                1.03,
                arbitrageActionSelection,
                200,
                250);
    }

}

