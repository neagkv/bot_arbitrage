package co.codingnomads.bot.arbitrage.service;

import co.codingnomads.bot.arbitrage.model.BidAsk;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.OrderIDWrapper;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.WalletWrapper;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.trade.MarketOrder;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * Created by Thomas Leruth on 12/17/17
 */

public class MakeOrderThread implements Callable<OrderIDWrapper> {

    MarketOrder marketOrder;
    BidAsk bidAsk;

    @Override
    public OrderIDWrapper call() {
        try {
            String orderID = bidAsk.getExchange().getTradeService().placeMarketOrder(marketOrder);
            String exchangeName = bidAsk.getExchange().getExchangeSpecification().getExchangeName();
            return new OrderIDWrapper(orderID, exchangeName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public MakeOrderThread(MarketOrder marketOrder, BidAsk bidAsk) {
        this.marketOrder = marketOrder;
        this.bidAsk = bidAsk;
    }
}
