package co.codingnomads.bot.arbitrage.model.arbitrageAction.email;

import co.codingnomads.bot.arbitrage.model.BidAsk;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.ArbitragePrintAction;

import java.math.BigDecimal;

/**
 * @author Kevin Neag
 */
public class EmailBody extends ArbitragePrintAction {

    String textBody;
    String HTMLBody;

    public EmailBody(double arbitrageMargin) {
        super(arbitrageMargin);
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public String getHTMLBody() {
        return HTMLBody;
    }

    public void setHTMLBody(String HTMLBody) {
        this.HTMLBody = HTMLBody;
    }

    public String printTextBody(BidAsk lowAsk, BidAsk highBid, BigDecimal difference, double arbitrageMargin) {
        if (difference.compareTo(BigDecimal.valueOf(arbitrageMargin)) > 0) {
            setTextBody("ARBITRAGE DETECTED!!!"
                    + " buy on " + lowAsk.getExchange().getDefaultExchangeSpecification().getExchangeName()
                    + " for " + lowAsk.getAsk()
                    + " and sell on " + highBid.getExchange().getDefaultExchangeSpecification().getExchangeName()
                    + " for " + highBid.getBid()
                    + " and make a return (before fees) of "
                    + (difference.add(BigDecimal.valueOf(-1))).multiply(BigDecimal.valueOf(100))
                    + "%");
        } else {
            setTextBody("No arbitrage found");
        }
        return textBody;
    }

    public String printHTMLBody(BidAsk lowAsk, BidAsk highBid, BigDecimal difference, double arbitrageMargin) {
        if (difference.compareTo(BigDecimal.valueOf(arbitrageMargin)) > 0) {
            setHTMLBody("ARBITRAGE DETECTED!!!"
                    + " buy on " + lowAsk.getExchange().getDefaultExchangeSpecification().getExchangeName()
                    + " for " + lowAsk.getAsk()
                    + " and sell on " + highBid.getExchange().getDefaultExchangeSpecification().getExchangeName()
                    + " for " + highBid.getBid()
                    + " and make a return (before fees) of "
                    + (difference.add(BigDecimal.valueOf(-1))).multiply(BigDecimal.valueOf(100))
                    + "%");
        } else {
            setHTMLBody("No arbitrage found");
        }
        return HTMLBody;
    }

}
