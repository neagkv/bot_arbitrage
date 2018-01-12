package co.codingnomads.bot.arbitrage.model.exchange;

import org.knowm.xchange.Exchange;

/**
 * Created by Thomas Leruth on 12/14/17
 *
 * POJO Class for Activated Exchanges having
 * Notes:
 * - activated = If it is currently active or not (can be desactivated if trading and without enough funds)
 * - tradingMode = if the exchange is set up for trading [private action] (if the auth param were provided)
 */
public class ActivatedExchange {

    private Exchange exchange;
    private boolean activated = true;
    private boolean tradingMode = false;

    public ActivatedExchange(Exchange exchange, boolean tradingMode) {
        this.exchange = exchange;
        this.tradingMode = tradingMode;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public boolean isTradingMode() {
        return tradingMode;
    }

    public void setTradingMode(boolean tradingMode) {
        this.tradingMode = tradingMode;
    }

}
