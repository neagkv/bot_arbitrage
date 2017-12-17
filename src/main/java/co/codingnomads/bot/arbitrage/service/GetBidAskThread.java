package co.codingnomads.bot.arbitrage.service;

import co.codingnomads.bot.arbitrage.model.ActivatedExchange;
import co.codingnomads.bot.arbitrage.model.BidAsk;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.Callable;

/**
 * Created by Thomas Leruth on 12/16/17
 */

/**
 * A runnable class using Callable<BidAsk> to be able to return a BidAsk
 */
public class GetBidAskThread implements Callable<BidAsk> {

    private ActivatedExchange activatedExchange;
    private CurrencyPair currencyPair;
    private String exchangeName;
    private boolean tradingEnvironment = false;
    private BigDecimal baseNeed; // base for bid
    private BigDecimal counterNeed; // counter is for ask

    /**
     * Find the BidAsk for every exchanges as a callable (runnable with return)
     * @return the BidAsk for the particular exchanges
     */
    @Override
    public BidAsk call() {

        if (tradingEnvironment) {
            try {
                Wallet wallet = activatedExchange.getExchange().getAccountService().getAccountInfo().getWallet();
                BigDecimal base =  wallet.getBalance(currencyPair.base).getTotal();
                BigDecimal counter = wallet.getBalance(currencyPair.counter).getTotal();
                if (base.compareTo(baseNeed) < 0) {
                    activatedExchange.setBidSuffisance(false);
                }
                else {
                    activatedExchange.setBidSuffisance(true);
                    // have this as it is possible the account is refunded (via trade or deposit)
                }
                if (counter.compareTo(counterNeed) < 0) {
                    activatedExchange.setAskSuffisance(false);
                }
                else {
                    activatedExchange.setAskSuffisance(true);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!activatedExchange.isAskSuffisance() & !activatedExchange.isBidSuffisance()) {
                System.out.println("No necessary fund to trade");
                activatedExchange.setActivated(false);
                return null;
            }
        }

        BidAsk bidAsk = ExchangeDataGetter.getBidAsk(activatedExchange.getExchange(), currencyPair);
        if(bidAsk == null) {
            System.out.println("pair not available for " + exchangeName);
        }

        if (tradingEnvironment && bidAsk != null) {
            if (!activatedExchange.isAskSuffisance()) {
                bidAsk.setAsk(bidAsk.getAsk().multiply(BigDecimal.valueOf(1000))); // (null creates issues)
                // 1000 should make sure we never use that one
            }
            if (!activatedExchange.isBidSuffisance()) {
                bidAsk.setBid(BigDecimal.valueOf(-1));
            }
        }
        return bidAsk;
    }

    /**
     * Constructor for the class
     * @param activatedExchange a pojo containing the exchange and some booleans
     * @param currencyPair the pair investigated
     */
    public GetBidAskThread(ActivatedExchange activatedExchange, CurrencyPair currencyPair, double baseNeed, double counterNeed) {
        this.activatedExchange = activatedExchange;
        this.currencyPair = currencyPair;
        exchangeName = activatedExchange.getExchange().getExchangeSpecification().getExchangeName();
        this.baseNeed = BigDecimal.valueOf(baseNeed);
        this.counterNeed = BigDecimal.valueOf(counterNeed);
        if (baseNeed > 0 && counterNeed > 0) tradingEnvironment = true; // a bit of a bad workaround to avoid overloading all methods
    }
}
