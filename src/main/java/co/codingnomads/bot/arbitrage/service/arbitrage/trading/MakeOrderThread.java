package co.codingnomads.bot.arbitrage.service.arbitrage.trading;

import co.codingnomads.bot.arbitrage.model.TickerData;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.trading.OrderIDWrapper;
import org.knowm.xchange.dto.trade.MarketOrder;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * Created by Thomas Leruth on 12/17/17
 *
 * Callable class to get the OrderIDWrapper
 */

public class MakeOrderThread implements Callable<OrderIDWrapper> {

    MarketOrder marketOrder;
    TickerData tickerData;

    @Override
    public OrderIDWrapper call() {
        try {
            String orderID = tickerData.getExchange().getTradeService().placeMarketOrder(marketOrder);
            String exchangeName = tickerData.getExchange().getExchangeSpecification().getExchangeName();
            return new OrderIDWrapper(orderID, exchangeName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public MakeOrderThread(MarketOrder marketOrder, TickerData tickerData) {
        this.marketOrder = marketOrder;
        this.tickerData = tickerData;
    }
}
