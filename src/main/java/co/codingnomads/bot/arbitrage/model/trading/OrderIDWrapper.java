package co.codingnomads.bot.arbitrage.model.trading;

/**
 * Created by Thomas Leruth on 12/17/17
 *
 * POJO class to wrap the orderID with an item to recognize the orderID (this class is needed because of the
 * drawback of using Callable<> being that they come back in unpredictable order
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
