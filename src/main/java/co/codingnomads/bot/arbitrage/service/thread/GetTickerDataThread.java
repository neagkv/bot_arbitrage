package co.codingnomads.bot.arbitrage.service.thread;

import co.codingnomads.bot.arbitrage.exception.ExchangeDataException;
import co.codingnomads.bot.arbitrage.model.exchange.ActivatedExchange;
import co.codingnomads.bot.arbitrage.model.ticker.TickerData;
import co.codingnomads.bot.arbitrage.model.ticker.TickerDataTrading;
import co.codingnomads.bot.arbitrage.service.general.ExchangeDataGetter;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.Wallet;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

/**
 * Created by Thomas Leruth on 12/16/17
 *
 * A runnable class using Callable<TickerData> to be able to return a TickerData
 */
public class GetTickerDataThread implements Callable<TickerData> {

    private ActivatedExchange activatedExchange;
    private CurrencyPair currencyPair;
    private boolean tradingEnvironment = false;
    private BigDecimal tradeAmountBase;

    /**
     * Find the TickerData for every exchanges as a callable (runnable with return)
     * @return the TickerData for the particular exchanges
     */
    @Override
    public TickerData call() throws TimeoutException, ExchangeDataException {

        BigDecimal baseFund = null;
        BigDecimal counterFund = null;
        BigDecimal baseNeed;
        BigDecimal counterNeed;

        TickerData tickerData = ExchangeDataGetter.getTickerData(activatedExchange.getExchange(), currencyPair);

        // the part below format specially TickerData for the trading action having one more check (sufficient fund)
        if (tradingEnvironment && tickerData != null) {
            try {
                Wallet wallet = activatedExchange.getExchange().getAccountService().getAccountInfo().getWallet();
                baseFund = wallet.getBalance(currencyPair.base).getTotal();
                counterFund = wallet.getBalance(currencyPair.counter).getTotal();

            } catch (IOException e) { e.printStackTrace(); }

            baseNeed = tradeAmountBase;
            counterNeed = tradeAmountBase.multiply(tickerData.getBid());

            TickerDataTrading tickerDataTrading = new TickerDataTrading(tickerData, baseFund, counterFund);

            if (counterFund.compareTo(counterNeed) < 0) {
                tickerDataTrading.setAsk(tickerDataTrading.getAsk().multiply(BigDecimal.valueOf(1000))); // (null creates issues)
            }
            if (baseFund.compareTo(baseNeed) < 0) {
                tickerDataTrading.setBid(BigDecimal.valueOf(-1));
            }
            // bad design that I pull tickerData then turn it null but I need it to figure baseNeed
            if (counterFund.compareTo(counterNeed) < 0 && baseFund.compareTo(baseNeed) < 0) {

                throw new ExchangeDataException("You do not have the funds to complete this trade");
            }
            return tickerDataTrading;
        }
        return tickerData;
    }

    /**
     * Constructor for the class
     *
     * @param activatedExchange a pojo containing the exchange and some booleans
     * @param currencyPair      the pair investigated
     */
    public GetTickerDataThread(ActivatedExchange activatedExchange, CurrencyPair currencyPair, BigDecimal tradeAmountBase) {
        this.activatedExchange = activatedExchange;
        this.currencyPair = currencyPair;
        this.tradeAmountBase = tradeAmountBase;
        if (tradeAmountBase.doubleValue() > 0) tradingEnvironment = true;
    }
}
