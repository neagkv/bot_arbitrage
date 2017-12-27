package co.codingnomads.bot.arbitrage.model;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;

import java.math.BigDecimal;

/**
 * Created by Thomas Leruth on 12/11/17
 */

/**
 * Pojo class to get the bid/ask and the exchangeName as well as the currency pair
 */
// todo not a big fan of the name
public class TickerData {

    private CurrencyPair currencyPair;
    Exchange exchange;
    private BigDecimal bid;
    private BigDecimal ask;
    private BigDecimal baseFund;
    private BigDecimal counterFund;

    public Exchange getExchange() {
        return exchange;
    }

    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }

    public BigDecimal getAsk() {
        return ask;
    }

    public void setAsk(BigDecimal ask) {
        this.ask = ask;
    }

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(CurrencyPair currencyPair) {
        this.currencyPair = currencyPair;
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

    public TickerData(CurrencyPair currencyPair, Exchange exchange, BigDecimal bid, BigDecimal ask) {
        this.currencyPair = currencyPair;
        this.exchange = exchange;
        this.bid = bid;
        this.ask = ask;
    }

    @Override
    public String toString() {
        return "BidAsk{" +
                "currencyPair=" + currencyPair +
                ", exchangeName='" + exchange.getDefaultExchangeSpecification().getExchangeName() +
                ", bid=" + bid +
                ", ask=" + ask +
                ", baseFund=" + baseFund +
                ", counterFund=" + counterFund +
                '}';
    }
}
