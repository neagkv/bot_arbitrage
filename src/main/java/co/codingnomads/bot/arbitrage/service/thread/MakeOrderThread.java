package co.codingnomads.bot.arbitrage.service.thread;
import co.codingnomads.bot.arbitrage.model.ticker.TickerData;
import co.codingnomads.bot.arbitrage.model.trading.OrderIDWrapper;
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

    // Ryan: general note some in-line commenting could be nice in here

    /**
     *
     * @return
     */
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

    /**
     *
     * @param marketOrder
     * @param tickerData
     */
    public MakeOrderThread(MarketOrder marketOrder, TickerData tickerData) {
        this.marketOrder = marketOrder;
        this.tickerData = tickerData;
    }
}
