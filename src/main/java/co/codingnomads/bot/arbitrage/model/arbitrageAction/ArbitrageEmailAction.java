package co.codingnomads.bot.arbitrage.model.arbitrageAction;

/**
 * Created by Thomas Leruth on 12/17/17
 */

public class ArbitrageEmailAction extends ArbitrageActionSelection {

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArbitrageEmailAction(double arbitrageMargin, String email) {
        super(arbitrageMargin);
        this.email = email;
    }
}
