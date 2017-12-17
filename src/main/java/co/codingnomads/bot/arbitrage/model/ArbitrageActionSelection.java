package co.codingnomads.bot.arbitrage.model;

/**
 * Created by Thomas Leruth on 12/14/17
 */

/**
 * Pojo for the three flags of potential actions implemented
 */
public class ArbitrageActionSelection {

    private boolean printActionFlag;
    private boolean tradeActionFlag;
    private boolean emailActionFlag;

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

    public boolean isEmailActionFlag() {
        return emailActionFlag;
    }

    public void setEmailActionFlag(boolean emailActionFlag) {
        this.emailActionFlag = emailActionFlag;
    }

    public ArbitrageActionSelection(boolean printActionFlag, boolean tradeActionFlag, boolean emailActionFlag) {
        this.printActionFlag = printActionFlag;
        this.tradeActionFlag = tradeActionFlag;
        this.emailActionFlag = emailActionFlag;
    }
}
