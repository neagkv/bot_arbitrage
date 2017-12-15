package co.codingnomads.bot.arbitrage.model;

import org.knowm.xchange.bitfinex.v1.BitfinexExchange;
import org.knowm.xchange.bitstamp.BitstampExchange;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.gdax.GDAXExchange;
import org.knowm.xchange.kraken.KrakenExchange;
import org.knowm.xchange.poloniex.PoloniexExchange;

/**
 * Created by Thomas Leruth on 12/13/17
 */

public enum ExchangeDetailsEnum {

    GDAXEXCHANGE("GDAXExchange", GDAXExchange.class),
    KRAKENEXCHANGE("KrakenExchange", KrakenExchange.class),
    BITSTAMPEXCHANGE("BitstampExchange", BitstampExchange.class),
    POLONIEXEXCHANGE("PoloniexExchange", PoloniexExchange.class),
    BITTREXEXCHANGE("BitrexExchange", BittrexExchange.class),
    BITFINEXEXCHANGE("BitfinexExchange", BitfinexExchange.class);

    private String exchangeName;
    private Class exchangeClass;

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public Class getExchangeClass() {
        return exchangeClass;
    }

    public void setExchangeClass(Class exchangeClass) {
        this.exchangeClass = exchangeClass;
    }

    ExchangeDetailsEnum(String exchangeName, Class exchangeClass) {
        this.exchangeName = exchangeName;
        this.exchangeClass = exchangeClass;
    }
}
