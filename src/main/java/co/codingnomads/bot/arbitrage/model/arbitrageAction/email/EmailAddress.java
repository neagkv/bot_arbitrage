package co.codingnomads.bot.arbitrage.model.arbitrageAction.email;

import co.codingnomads.bot.arbitrage.model.arbitrageAction.ArbitrageEmailAction;

/**
 * @author Kevin Neag
 */

/**
 * EmailAddress class that extends the ArbitrageEmailAction class
 * allows the user to set the email address they wish to receive alerts from
 */
public abstract class EmailAddress extends ArbitrageEmailAction{

    public EmailAddress(double arbitrageMargin, String email) {
        super(arbitrageMargin, email);
    }
}
