package co.codingnomads.bot.arbitrage.model;

/**
 * Created by Thomas Leruth on 12/14/17
 */

public class ArbitrageActionSelection {

    private boolean printActionFlag;
    private boolean tradeActionFlag;
    private boolean emailAction;

    public boolean isPrintActionFlag() {
        return printActionFlag;
    }

    public void setPrintActionFlag(boolean printActionFlag) {
        this.printActionFlag = printActionFlag;
    }

    public boolean isTradeActionFlag() {
        return tradeActionFlag;
    }

    public void setTradeActionFlag(boolean tradeActionFlag) {
        this.tradeActionFlag = tradeActionFlag;
    }

    public boolean isEmailAction() {
        return emailAction;
    }

    public void setEmailAction(boolean emailAction) {
        this.emailAction = emailAction;
    }

    public ArbitrageActionSelection(boolean printActionFlag, boolean tradeActionFlag, boolean emailAction) {
        this.printActionFlag = printActionFlag;
        this.tradeActionFlag = tradeActionFlag;
        this.emailAction = emailAction;
    }
}
