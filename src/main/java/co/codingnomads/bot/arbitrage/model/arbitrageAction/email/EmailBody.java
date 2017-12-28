package co.codingnomads.bot.arbitrage.model.arbitrageAction.email;
import co.codingnomads.bot.arbitrage.model.TickerData;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.ArbitragePrintAction;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Kevin Neag
 */

/**
 * Builder Pojo class that extends the ArbitragePrintAction class
 * provides the body in text as well as HTML format based on the parameters the user queries
 * Also provides an email subject
 */
public class EmailBody extends ArbitragePrintAction {

    String textBody;
    String HTMLBody;
    String subjectBody;

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

    public String getSubjectBody() {
        return subjectBody;
    }

    public void setSubjectBody(String subjectBody) {
        this.subjectBody = subjectBody;
    }


    /**
     * Prints the low asking price and high selling price as well as the difference in the body of each email.
     * @param lowAsk
     * @param highBid
     * @param difference
     * @param arbitrageMargin
     * @return
     */
    public String printTextBody(TickerData lowAsk, TickerData highBid, BigDecimal difference, double arbitrageMargin) {
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

    /**
     * Converts the email body into HTML format
     * @param lowAsk
     * @param highBid
     * @param difference
     * @param arbitrageMargin
     * @return
     */
    public String printHTMLBody(TickerData lowAsk, TickerData highBid, BigDecimal difference, double arbitrageMargin) {
        if (difference.compareTo(BigDecimal.valueOf(arbitrageMargin)) > 0) {
            setHTMLBody("<h1>ARBITRAGE DETECTED!!!<h1>"
                    + " <p>buy on " + lowAsk.getExchange().getDefaultExchangeSpecification().getExchangeName()
                    + " for " + lowAsk.getAsk()
                    + " and sell on " + highBid.getExchange().getDefaultExchangeSpecification().getExchangeName()
                    + " for " + highBid.getBid()
                    + " and make a return (before fees) of "
                    + (difference.add(BigDecimal.valueOf(-1))).multiply(BigDecimal.valueOf(100))
                    + "%<p>");
        } else {
            setHTMLBody("No arbitrage found");
        }
        return HTMLBody;
    }

    /**
     * Sets "Arbitrage Update" as the subject of each email
     * @return
     */
    public String printSubject(){
        setSubjectBody("Arbitrage Update");
        return subjectBody;
    }
}
