package co.codingnomads.bot.arbitrage.model.exchange;

import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.gdax.GDAXExchange;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Thomas Leruth on 12/16/17
*
 * Extension of the specs for GDAX
 */
public class GDAXSpecs extends ExchangeSpecs {

    private String passphrase;

    public GDAXSpecs(String apiKey, String secretKey, String passphrase) {
        super(apiKey, secretKey);
        this.passphrase = passphrase;
        if (null != apiKey && null != secretKey && null != passphrase) {
            setTradingMode(true);
        }
    }

    public GDAXSpecs() {
        super();
    }

    @Override
    public ExchangeSpecification GetSetupedExchange() {
        ExchangeSpecification exSpec = new GDAXExchange().getDefaultExchangeSpecification();
        if (super.getTradingMode()) {
            Map<String, Object> map = new HashMap<>();
            map.put("passphrase", passphrase);
            exSpec.setApiKey(getApiKey());
            exSpec.setSecretKey(getSecretKey());
            exSpec.setExchangeSpecificParameters(map);
        }
        return exSpec;
    }

}

