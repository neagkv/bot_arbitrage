package co.codingnomads.bot.arbitrage.model;

import java.math.BigDecimal;

/**
 * Created by Thomas Leruth on 12/11/17
 */
//todo add pair name in the class
public class BidAsk {

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

    public BidAsk(String exchangeName, BigDecimal bid, BigDecimal ask) {
        this.exchangeName = exchangeName;
        this.bid = bid;
        this.ask = ask;
    }

    @Override
    public String toString() {
        return "BidAsk{" +
                "exchangeName='" + exchangeName + '\'' +
                ", bid=" + bid +
                ", ask=" + ask +
                '}';
    }
}
