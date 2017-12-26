package co.codingnomads.bot.arbitrage.model.exchange;

import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.poloniex.PoloniexExchange;

/**
 * Created by Thomas Leruth on 12/16/17
 *
 * Extension of the specs for Poloniex
 */
public class PoloniexSpecs extends ExchangeSpecs {

    public PoloniexSpecs(String apiKey, String secretKey) {
        super(apiKey, secretKey);
        if (null != apiKey && null != secretKey) {
            setTradingMode(true);
        }
    }

    public PoloniexSpecs() {
        super();
    }

    @Override
    public ExchangeSpecification GetSetupedExchange() {
        ExchangeSpecification exSpec = new PoloniexExchange().getDefaultExchangeSpecification();
        if (super.getTradingMode()) {
            exSpec.setApiKey(super.getApiKey());
            exSpec.setSecretKey(super.getSecretKey());
        }
        return exSpec;
    }
}