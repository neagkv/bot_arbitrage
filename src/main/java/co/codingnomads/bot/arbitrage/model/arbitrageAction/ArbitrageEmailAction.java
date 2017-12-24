package co.codingnomads.bot.arbitrage.model.arbitrageAction;

/**
 * Created by Thomas Leruth on 12/17/17
 *
 * POJO class for the information needed to use the email method as acting behavior
 */

public class ArbitrageEmailAction extends ArbitrageActionSelection {

    private String email;

    public ArbitrageEmailAction(double arbitrageMargin, String email) {
        super(arbitrageMargin);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
