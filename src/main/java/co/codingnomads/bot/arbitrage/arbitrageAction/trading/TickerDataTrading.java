package co.codingnomads.bot.arbitrage.arbitrageAction.trading;

import co.codingnomads.bot.arbitrage.model.TickerData;
import java.math.BigDecimal;

/**
 * Created by Thomas Leruth on 12/21/17
 *
 * POJO class extending TickerData adding two additional info needed for trading (funds available)
 */

public class TickerDataTrading extends TickerData {

    BigDecimal baseFund;
    BigDecimal counterFund;

    public TickerDataTrading(TickerData tickerData, BigDecimal baseFund, BigDecimal counterFund) {
        super(tickerData.getCurrencyPair(), tickerData.getExchange(), tickerData.getBid(), tickerData.getAsk());
        this.baseFund = baseFund;
        this.counterFund = counterFund;
    }

    public BigDecimal getBaseFund() {
        return baseFund;
    }

    public void setBaseFund(BigDecimal baseFund) {
        this.baseFund = baseFund;
    }

    public BigDecimal getCounterFund() {
        return counterFund;
    }

    public void setCounterFund(BigDecimal counterFund) {
        this.counterFund = counterFund;
    }

    @Override
    public String toString() {
        return "TickerDataTrading{" +
                super.toString() +
                "baseFund=" + baseFund +
                ", counterFund=" + counterFund +
                '}';
    }
}
