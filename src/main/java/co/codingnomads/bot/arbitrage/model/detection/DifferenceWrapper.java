package co.codingnomads.bot.arbitrage.model.detection;

import org.knowm.xchange.currency.CurrencyPair;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by Thomas Leruth on 12/18/17
 *
 * POJO class to hold various information needed from the detection
 */

public class DifferenceWrapper {

    private Timestamp timestamp;
    private CurrencyPair currencyPair;
    private BigDecimal difference;
    private BigDecimal lowAsk;
    private String lowAskExchange;
    private BigDecimal highBid;
    private String highBidExchange;

    public DifferenceWrapper(CurrencyPair currencyPair, BigDecimal difference, BigDecimal lowAsk, String lowAskExchange, BigDecimal highBid, String highBidExchange) {
        timestamp = new Timestamp(System.currentTimeMillis());
        this.currencyPair = currencyPair;
        this.difference = difference;
        this.lowAsk = lowAsk;
        this.lowAskExchange = lowAskExchange;
        this.highBid = highBid;
        this.highBidExchange = highBidExchange;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(CurrencyPair currencyPair) {
        this.currencyPair = currencyPair;
    }

    public BigDecimal getDifference() {
        return difference;
    }

    public void setDifference(BigDecimal difference) {
        this.difference = difference;
    }

    public BigDecimal getLowAsk() {
        return lowAsk;
    }

    public void setLowAsk(BigDecimal lowAsk) {
        this.lowAsk = lowAsk;
    }

    public String getLowAskExchange() {
        return lowAskExchange;
    }

    public void setLowAskExchange(String lowAskExchange) {
        this.lowAskExchange = lowAskExchange;
    }

    public BigDecimal getHighBid() {
        return highBid;
    }

    public void setHighBid(BigDecimal highBid) {
        this.highBid = highBid;
    }

    public String getHighBidExchange() {
        return highBidExchange;
    }

    public void setHighBidExchange(String highBidExchange) {
        this.highBidExchange = highBidExchange;
    }

    public DifferenceWrapper(CurrencyPair currencyPair) {
        this.currencyPair = currencyPair;
    }

    @Override
    public String toString() {
        return "DifferenceWrapper{" +
                "timestamp=" + timestamp +
                ", currencyPair=" + currencyPair +
                ", difference=" + difference +
                ", lowAsk=" + lowAsk +
                ", lowAskExchange='" + lowAskExchange + '\'' +
                ", highBid=" + highBid +
                ", highBidExchange='" + highBidExchange + '\'' +
                '}';
    }
}
