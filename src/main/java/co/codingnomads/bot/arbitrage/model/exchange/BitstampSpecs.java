package co.codingnomads.bot.arbitrage.model.exchange;

import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitstamp.BitstampExchange;

/**
 * Created by Thomas Leruth on 12/16/17
 *
 * Extension of the specs for Bistamp
 */
// todo implement but have no key yet
public class BitstampSpecs extends ExchangeSpecs {

    protected BitstampSpecs(String apiKey, String secretKey) {
        super(apiKey, secretKey);
        if (null != apiKey && null != secretKey) {
            setTradingMode(true);
        }
    }

    public BitstampSpecs() {
        super();
    }

    @Override
    public ExchangeSpecification GetSetupedExchange() {
        ExchangeSpecification exSpec = new BitstampExchange().getDefaultExchangeSpecification();
        if (super.getTradingMode()) {
            exSpec.setApiKey(super.getApiKey());
            exSpec.setSecretKey(super.getSecretKey());
        }
        return exSpec;
    }
}
