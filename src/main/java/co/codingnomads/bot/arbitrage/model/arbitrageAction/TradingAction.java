package co.codingnomads.bot.arbitrage.model.arbitrageAction;

/**
 * Created by Thomas Leruth on 12/17/17
 */

public class TradingAction extends ArbitrageActionSelection {

    private double tradeValueBase;

    public double getTradeValueBase() {
        return tradeValueBase;
    }

    public void setTradeValueBase(double tradeValueBase) {
        this.tradeValueBase = tradeValueBase;
    }

    public TradingAction(double arbitrageMargin, double tradeValueBase) {
        super(arbitrageMargin);
        this.tradeValueBase = tradeValueBase;
    }
}
