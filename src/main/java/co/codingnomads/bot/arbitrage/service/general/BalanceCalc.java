package co.codingnomads.bot.arbitrage.service.general;

import co.codingnomads.bot.arbitrage.exchange.ExchangeSpecs;
import co.codingnomads.bot.arbitrage.exchange.GDAXSpecs;
import co.codingnomads.bot.arbitrage.exchange.KrakenSpecs;
import co.codingnomads.bot.arbitrage.model.exchange.ActivatedExchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Wallet;
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
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Kevin Neag
 */
public class BalanceCalc {

    public void Balance(ArrayList<ExchangeSpecs> selected) throws IOException {

            ExchangeGetter exchangeGetter = new ExchangeGetter();
            ArrayList<ActivatedExchange> activatedExchanges = exchangeGetter.getAllSelectedExchangeServices(selected, false);

            for (ActivatedExchange activatedExchange : activatedExchanges) {
                if (activatedExchange.isActivated() && activatedExchange.isTradingMode()) {
                    Wallet wallet = activatedExchange.getExchange().getAccountService().getAccountInfo().getWallet();
                    //System.out.println(wallet);
                    System.out.println("==========================================================");
                    System.out.println("==========================================================");
                    System.out.println();
                    System.out.println("On "+ " " + activatedExchange.getExchange().getExchangeSpecification().getExchangeName());
                    System.out.print("you have " + wallet.getBalance(Currency.USD).getAvailableForWithdrawal()+ " ");
                    System.out.print("and " + wallet.getBalance(Currency.ETH).getAvailableForWithdrawal()+ " ");
                    System.out.println();
                    System.out.println("==========================================================");
                    System.out.println("==========================================================");
                    System.out.println();

                }
            }
        }
    }
