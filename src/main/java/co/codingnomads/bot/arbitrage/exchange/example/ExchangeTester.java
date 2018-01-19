package co.codingnomads.bot.arbitrage.exchange.example;

import co.codingnomads.bot.arbitrage.exchange.BitfinexSpecs;
import co.codingnomads.bot.arbitrage.exchange.ExchangeSpecs;
import co.codingnomads.bot.arbitrage.exchange.GDAXSpecs;
import co.codingnomads.bot.arbitrage.exchange.KrakenSpecs;
import co.codingnomads.bot.arbitrage.model.exchange.ActivatedExchange;
import co.codingnomads.bot.arbitrage.service.general.ExchangeGetter;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.kraken.KrakenExchange;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Thomas Leruth on 12/16/17
 * Use this method for testing the exchange POJO (seeing if that some have special needed Auth elements)
 */
public class ExchangeTester {
//
//    public static void main(String[] args) throws IOException {
//
//
//         //ExchangeSpecification exSpec = new KrakenExchange().getDefaultExchangeSpecification();
//
//        ArrayList<ExchangeSpecs> selected = new ArrayList<>();
//        selected.add(krakenSpecs);
//        selected.add(gdaxSpecs);
//
//        ExchangeGetter exchangeGetter = new ExchangeGetter();
//        ArrayList<ActivatedExchange> activatedExchanges = exchangeGetter.getAllSelectedExchangeServices(selected, false);
//
//        for (ActivatedExchange activatedExchange : activatedExchanges) {
//            if (activatedExchange.isActivated() && activatedExchange.isTradingMode()) {
//                Wallet wallet = activatedExchange.getExchange().getAccountService().getAccountInfo().getWallet();
//                //System.out.println(wallet);
//                System.out.println("==========================================================");
//                System.out.println("==========================================================");
//                System.out.println();
//                System.out.println("On"+ wallet.getName());
//                System.out.print("you have" + wallet.getBalance(Currency.USD).getAvailableForWithdrawal()+ " ");
//                System.out.print("you have" + wallet.getBalance(Currency.ETH).getAvailableForWithdrawal()+ " ");
//                System.out.println();
//                System.out.println("==========================================================");
//                System.out.println("==========================================================");
//
//            }
//        }
//    }
}


