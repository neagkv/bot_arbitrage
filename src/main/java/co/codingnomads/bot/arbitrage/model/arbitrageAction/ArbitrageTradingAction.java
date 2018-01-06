package co.codingnomads.bot.arbitrage.model.arbitrageAction;

/**
 * Created by Thomas Leruth on 12/17/17
 *
 * POJO class for the information needed to use the trading action as behavior action
 */

public class ArbitrageTradingAction extends ArbitrageActionSelection {

    private double tradeValueBase;

    public ArbitrageTradingAction(double arbitrageMargin, double tradeValueBase) {
        super(arbitrageMargin);
        this.tradeValueBase = tradeValueBase;
    }

    public double getTradeValueBase() {
        return tradeValueBase;
    }

    public void setTradeValueBase(double tradeValueBase) {
        this.tradeValueBase = tradeValueBase;
    }

}
