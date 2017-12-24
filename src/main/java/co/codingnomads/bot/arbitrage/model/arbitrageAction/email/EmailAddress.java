package co.codingnomads.bot.arbitrage.model.arbitrageAction.email;

import co.codingnomads.bot.arbitrage.model.arbitrageAction.ArbitrageEmailAction;

/**
 * @author Kevin Neag
 */
public abstract class EmailAddress extends ArbitrageEmailAction{

    public EmailAddress(double arbitrageMargin, String email) {
        super(arbitrageMargin, email);
    }
}
