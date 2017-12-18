package co.codingnomads.bot.arbitrage.model.arbitrageAction.trading;

/**
 * Created by Thomas Leruth on 12/17/17
 */

public class OrderIDWrapper {

    String orderID;
    String ExchangeName;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getExchangeName() {
        return ExchangeName;
    }

    public void setExchangeName(String exchangeName) {
        ExchangeName = exchangeName;
    }

    public OrderIDWrapper(String orderID, String exchangeName) {
        this.orderID = orderID;
        ExchangeName = exchangeName;
    }
}
