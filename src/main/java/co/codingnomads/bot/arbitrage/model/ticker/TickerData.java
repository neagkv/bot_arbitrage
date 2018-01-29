package co.codingnomads.bot.arbitrage.model.ticker;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import java.math.BigDecimal;

/**
 * Created by Thomas Leruth on 12/11/17
 *
 * POJO class to get the bid/ask and the exchangeName as well as the currency pair
 */

public class TickerData {

    private CurrencyPair currencyPair;
    Exchange exchange;
    private BigDecimal bid;
    private BigDecimal ask;

    /**
     * Constructor for the TickerData class
     * @param currencyPair
     * @param exchange
     * @param bid
     * @param ask
     */
    public TickerData(CurrencyPair currencyPair, Exchange exchange, BigDecimal bid, BigDecimal ask) {
        this.currencyPair = currencyPair;
        this.exchange = exchange;
        this.bid = bid;
        this.ask = ask;
    }

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

    @Override
    public String toString() {
        return "TickerData{" +
                "currencyPair=" + currencyPair +
                ", exchangeName='" + exchange.getDefaultExchangeSpecification().getExchangeName() +
                ", bid=" + bid +
                ", ask=" + ask +
                '}';
    }
}
