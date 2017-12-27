package co.codingnomads.bot.arbitrage.service.general.thread;

import co.codingnomads.bot.arbitrage.model.ActivatedExchange;
import co.codingnomads.bot.arbitrage.model.BidAsk;
import co.codingnomads.bot.arbitrage.service.general.ExchangeDataGetter;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.Wallet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

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
    private BigDecimal tradeAmountBase;

    /**
     * Find the BidAsk for every exchanges as a callable (runnable with return)
     * @return the BidAsk for the particular exchanges
     */
    @Override
    public BidAsk call() {

        BigDecimal baseFund = null;
        BigDecimal counterFund = null;
        BigDecimal baseNeed;
        BigDecimal counterNeed;

        if (tradingEnvironment) {
            try {
                Wallet wallet = activatedExchange.getExchange().getAccountService().getAccountInfo().getWallet();
                baseFund =  wallet.getBalance(currencyPair.base).getTotal();
                counterFund = wallet.getBalance(currencyPair.counter).getTotal();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        BidAsk bidAsk = null;
        try {
            bidAsk = ExchangeDataGetter.getBidAsk(activatedExchange.getExchange(), currencyPair);
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        if (tradingEnvironment && bidAsk != null) {

            baseNeed = tradeAmountBase;
            counterNeed = tradeAmountBase.multiply(bidAsk.getBid());

            System.out.println(baseNeed + " " + counterNeed);
            bidAsk.setBaseFund(baseFund);
            bidAsk.setCounterFund(counterFund);

            if (counterFund.compareTo(counterNeed) < 0) {
                bidAsk.setAsk(bidAsk.getAsk().multiply(BigDecimal.valueOf(1000))); // (null creates issues)
                // 1000 should make sure we never use that one
            }
            if (baseFund.compareTo(baseNeed) < 0) {
                bidAsk.setBid(BigDecimal.valueOf(-1));
            }
            // bad design that I pull bidAsk then turn it null but I need it to figure baseNeed
            if (counterFund.compareTo(counterNeed) < 0 && baseFund.compareTo(baseNeed) < 0) {
                System.out.println("No necessary fund to trade");
                activatedExchange.setActivated(false);
                return null;
            }
        }
        return bidAsk;
    }

    /**
     * Constructor for the class
     * @param activatedExchange a pojo containing the exchange and some booleans
     * @param currencyPair the pair investigated
     */
    public GetBidAskThread(ActivatedExchange activatedExchange, CurrencyPair currencyPair, double tradeAmountBase) {
        this.activatedExchange = activatedExchange;
        this.currencyPair = currencyPair;
        exchangeName = activatedExchange.getExchange().getExchangeSpecification().getExchangeName();
        this.tradeAmountBase = BigDecimal.valueOf(tradeAmountBase);
        if (tradeAmountBase > 0) tradingEnvironment = true;
    }
}
