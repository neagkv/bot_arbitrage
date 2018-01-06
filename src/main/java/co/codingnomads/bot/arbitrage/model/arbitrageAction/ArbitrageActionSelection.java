package co.codingnomads.bot.arbitrage.model.arbitrageAction;

/**
 * Created by Thomas Leruth on 12/14/17
 *
 * abstract method to be extended for each arbitrage action
 */

public abstract class ArbitrageActionSelection {

    private double arbitrageMargin;

    public ArbitrageActionSelection() {
    }

    public double getArbitrageMargin() {
        return arbitrageMargin;
    }

    public void setArbitrageMargin(double arbitrageMargin) {
        this.arbitrageMargin = arbitrageMargin;
    }

    public ArbitrageActionSelection(double arbitrageMargin) {
        this.arbitrageMargin = arbitrageMargin;
    }


}
