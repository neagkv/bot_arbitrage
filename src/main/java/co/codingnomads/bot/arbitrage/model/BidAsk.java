package co.codingnomads.bot.arbitrage.model;

import org.knowm.xchange.currency.CurrencyPair;

import java.math.BigDecimal;

/**
 * Created by Thomas Leruth on 12/11/17
 */

/**
 * Pojo class to get the bid/ask and the exchangeName as well as the currency pair
 */
public class BidAsk {

    private CurrencyPair currencyPair;
    private String exchangeName;
    private BigDecimal bid;
    private BigDecimal ask;

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchageName(String exchageName) {
        this.exchangeName = exchageName;
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

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public BidAsk(CurrencyPair currencyPair, String exchangeName, BigDecimal bid, BigDecimal ask) {
        this.currencyPair = currencyPair;
        this.exchangeName = exchangeName;
        this.bid = bid;
        this.ask = ask;
    }

    @Override
    public String toString() {
        return "BidAsk{" +
                "currencyPair=" + currencyPair +
                ", exchangeName='" + exchangeName + '\'' +
                ", bid=" + bid +
                ", ask=" + ask +
                '}';
    }
}
