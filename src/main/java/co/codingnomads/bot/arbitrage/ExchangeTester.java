package co.codingnomads.bot.arbitrage;

import co.codingnomads.bot.arbitrage.model.ActivatedExchange;
import co.codingnomads.bot.arbitrage.model.exchange.BitfinexSpecs;
import co.codingnomads.bot.arbitrage.model.exchange.ExchangeSpecs;
import co.codingnomads.bot.arbitrage.model.exchange.KrakenSpecs;
import co.codingnomads.bot.arbitrage.service.general.ExchangeGetter;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Wallet;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Thomas Leruth on 12/16/17
 * Use this method for testing the exchange POJO (seeing if that some have special needed Auth elements)
 */
//public class ExchangeTester {
//
//    public static void main(String[] args) throws IOException {
//
//        ExchangeSpecs krakenSpecs = new KrakenSpecs(
//                null,
//                null);
//
////        ExchangeSpecs krakenSpecs = new KrakenSpecs();
//
//        ExchangeSpecs bitfinexSpecs = new BitfinexSpecs("",
//                "");
//
//        // ExchangeSpecification exSpec = new KrakenExchange().getDefaultExchangeSpecification();
//
//        ArrayList<ExchangeSpecs> selected = new ArrayList<>();
////        selected.add(krakenSpecs);
//        selected.add(bitfinexSpecs);
//
//        ExchangeGetter exchangeGetter = new ExchangeGetter();
//
//        ArrayList<ActivatedExchange> activatedExchanges = exchangeGetter.getAllSelectedExchangeServices(selected, false);
//
//        for (ActivatedExchange activatedExchange : activatedExchanges) {
//            if (activatedExchange.isActivated() && activatedExchange.isTradingMode()) {
//                Wallet wallet = activatedExchange.getExchange().getAccountService().getAccountInfo().getWallet();
//                System.out.println(wallet);
//                System.out.println(wallet.getBalance(Currency.EUR));
//                System.out.println(wallet.getBalance(Currency.ETH));
//            }
//        }
//    }
//}
