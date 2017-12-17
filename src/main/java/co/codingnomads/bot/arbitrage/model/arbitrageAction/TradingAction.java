package co.codingnomads.bot.arbitrage.model.arbitrageAction;

/**
 * Created by Thomas Leruth on 12/17/17
 */

public class TradingAction extends ArbitrageActionSelection {

    private double baseMin;
    private double counterMin;

    public double getBaseMin() {
        return baseMin;
    }

    public void setBaseMin(double baseMin) {
        this.baseMin = baseMin;
    }

    public double getCounterMin() {
        return counterMin;
    }

    public void setCounterMin(double counterMin) {
        this.counterMin = counterMin;
    }

    public TradingAction(double arbitrageMargin, double baseMin, double counterMin) {
        super(arbitrageMargin);
        this.baseMin = baseMin;
        this.counterMin = counterMin;
    }
}
