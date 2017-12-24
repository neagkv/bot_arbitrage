package co.codingnomads.bot.arbitrage.model.exchange;

import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.kraken.KrakenExchange;

/**
 * Created by Thomas Leruth on 12/16/17
 *
 * Abstract class to be implemeted for all set up exchanged (needed for Auth params)
 * The implementation should have an empty constructor (for a version without Auth params) and one fully loaded
 */
public abstract class ExchangeSpecs {

    private String apiKey;
    private String secretKey;
    private Boolean tradingMode = false;

    protected ExchangeSpecs(String apiKey, String secretKey) {
        this.apiKey = apiKey;
        this.secretKey = secretKey;
    }

    protected ExchangeSpecs() {
    }

    protected String getApiKey() {
        return apiKey;
    }

    protected String getSecretKey() {
        return secretKey;
    }

    public Boolean getTradingMode() {
        return tradingMode;
    }

    protected void setTradingMode(Boolean tradingMode) {
        this.tradingMode = tradingMode;
    }

    public abstract ExchangeSpecification GetSetupedExchange ();

// should be implemented like that
//    ExchangeSpecification exSpec = new -EXCHANGENAME-Exchange().getDefaultExchangeSpecification();
//        if (apiKey != null || secretKey != null || username != null) {
//        exSpec.setApiKey(apiKey);
//        exSpec.setSecretKey(secretKey);
//        exSpec.setUsername(username)
//    }
//        return exSpec;
}
