package co.codingnomads.bot.arbitrage.exchange;

import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.BinanceExchange;


/**
 * @author Kevin Neag
 *
 * Extension of the specs for Binance
 */
public class BinanceSpecs extends ExchangeSpecs {


        public BinanceSpecs(String apiKey, String secretKey) {
            super(apiKey, secretKey);
            if (null != apiKey && null != secretKey) {
                setTradingMode(true);
            }
        }

        public BinanceSpecs() {
            super();
        }

        @Override
        public ExchangeSpecification GetSetupedExchange() {
            ExchangeSpecification exSpec = new BinanceExchange().getDefaultExchangeSpecification();
            if (super.getTradingMode()) {
                exSpec.setApiKey(super.getApiKey());
                exSpec.setSecretKey(super.getSecretKey());
            }
            return exSpec;
        }

}
