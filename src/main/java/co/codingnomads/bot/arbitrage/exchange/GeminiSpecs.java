package co.codingnomads.bot.arbitrage.exchange;

import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.gemini.v1.GeminiExchange;

/**
 * @author Kevin Neag
 */
public class GeminiSpecs  extends ExchangeSpecs {

    public GeminiSpecs(String apiKey, String secretKey) {
            super(apiKey, secretKey);
            if (null != apiKey && null != secretKey) {
                setTradingMode(true);
            }
        }

    public GeminiSpecs() {
            super();
        }

    @Override
    public ExchangeSpecification GetSetupedExchange() {
        ExchangeSpecification exSpec = new GeminiExchange().getDefaultExchangeSpecification();
            if (super.getTradingMode()) {
                exSpec.setApiKey(super.getApiKey());
                exSpec.setSecretKey(super.getSecretKey());
            }
            return exSpec;
        }


}
