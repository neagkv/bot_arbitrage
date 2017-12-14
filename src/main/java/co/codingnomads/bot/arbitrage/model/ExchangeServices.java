package co.codingnomads.bot.arbitrage.model;

import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.TradeService;

/**
 * Created by Thomas Leruth on 12/13/17
 */

public class ExchangeServices {

    private String exchangeName;
    private MarketDataService marketDataService;
    private TradeService tradeService;
    private AccountService accountService;

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public MarketDataService getMarketDataService() {
        return marketDataService;
    }

    public void setMarketDataService(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    public TradeService getTradeService() {
        return tradeService;
    }

    public void setTradeService(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }
}
