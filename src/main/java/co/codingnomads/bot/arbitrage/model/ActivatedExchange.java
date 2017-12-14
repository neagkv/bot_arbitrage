package co.codingnomads.bot.arbitrage.model;

/**
 * Created by Thomas Leruth on 12/14/17
 */

public class ActivatedExchange {

    private ExchangeServices exchangeServices;
    private boolean activated = true;

    public ExchangeServices getExchangeServices() {
        return exchangeServices;
    }

    public void setExchangeServices(ExchangeServices exchangeServices) {
        this.exchangeServices = exchangeServices;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public ActivatedExchange(ExchangeServices exchangeServices) {
        this.exchangeServices = exchangeServices;
    }
}
