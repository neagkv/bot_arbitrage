package co.codingnomads.bot.arbitrage;

import co.codingnomads.bot.arbitrage.model.arbitrageAction.ArbitrageActionSelection;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.EmailAction;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.PrintAction;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.TradingAction;
import co.codingnomads.bot.arbitrage.model.exchange.*;
import co.codingnomads.bot.arbitrage.service.Arbitrage;
import co.codingnomads.bot.arbitrage.service.ArbitrageAction;
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

    public static void main(String[] args) throws IOException, InterruptedException {
        Arbitrage arbitrage = new Arbitrage();

        ArrayList<ExchangeSpecs> selectedExchanges = new ArrayList<>();

        selectedExchanges.add(new KrakenSpecs()); // internal: good but slow
        selectedExchanges.add(new GDAXSpecs()); // internal: good but waiting limit increase
//        selectedExchanges.add(new BitfinexSpecs()); // internal: good but waiting limit increase
//        selectedExchanges.add(new BittrexSpecs()); // Need Pojo building (internal: all good)
//        selectedExchanges.add(new PoloniexSpecs()); // need Pojo building and CAPTCHA issue resolving (internal: verif)
//        selectedExchanges.add(new BitstampSpecs()); // need Pojo building but no key (internal: verif)

        //ArbitrageActionSelection arbitrageActionSelection = new TradingAction(1.01,0.02);
        // do not use anything else than Kraken or GDAX for trading up to now. Kraken min ETH is 0.02 and GDAX: 0.01
        ArbitrageActionSelection arbitrageActionSelection = new PrintAction(1.01);
        // ArbitrageActionSelection arbitrageActionSelection = new EmailAction(1.03, "Kevin@loves.tortugas");

        arbitrage.run(
                CurrencyPair.ETH_EUR,
                selectedExchanges,
                arbitrageActionSelection);
    }

}

