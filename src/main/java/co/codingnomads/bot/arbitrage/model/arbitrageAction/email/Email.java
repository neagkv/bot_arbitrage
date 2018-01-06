package co.codingnomads.bot.arbitrage.model.arbitrageAction.email;

import co.codingnomads.bot.arbitrage.model.TickerData;
import java.math.BigDecimal;

/**
 * @author Kevin Neag
 */
public class Email {

    // This address must be verified with Amazon SES.
    String FROM = "cryptoarbitragebot25@gmail.com";

    String TO;

    // The subject line for the email.
    String SUBJECT;

    // The HTML body for the email.
    String HTMLBODY;

    // The email body for recipients with non-HTML email clients.
    String TEXTBODY;

    java.util.Date dt = new java.util.Date();

    java.text.SimpleDateFormat sdf =
            new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    String timeEmailSent = sdf.format(dt);

    /**
     *
     */
    public Email() {

    }

    public String getFROM() {
        return FROM;
    }

    public void setFROM(String FROM) {
        this.FROM = FROM;
    }

    public String getTO() {
        return TO;
    }

    public void setTO(String TO) {
        this.TO = TO;
    }

    public String getSUBJECT() {
        return SUBJECT;
    }

    public void setSUBJECT(String SUBJECT) {
        this.SUBJECT = SUBJECT;
    }

    public String getHTMLBODY() {
        return HTMLBODY;
    }

    public void setHTMLBODY(String HTMLBODY) {
        this.HTMLBODY = HTMLBODY;
    }

    public String getTEXTBODY() {
        return TEXTBODY;
    }

    public void setTEXTBODY(String TEXTBODY) {
        this.TEXTBODY = TEXTBODY;
    }

    public String getTimeEmailSent() {
        return timeEmailSent;
    }

    public void setTimeEmailSent(String timeEmailSent) {
        this.timeEmailSent = timeEmailSent;
    }

    /**
     * Converts the email body into HTML format
     * @param lowAsk
     * @param highBid
     * @param difference
     * @param arbitrageMargin
     * @return
     */
    public void buildHTMLBody(TickerData lowAsk, TickerData highBid, BigDecimal difference, double arbitrageMargin) {
        if (difference.compareTo(BigDecimal.valueOf(arbitrageMargin)) > 0) {
            setHTMLBODY("<h1>ARBITRAGE DETECTED!!!<h1>"
                    + " <p>buy on " + lowAsk.getExchange().getDefaultExchangeSpecification().getExchangeName()
                    + " for " + lowAsk.getAsk()
                    + " and sell on " + highBid.getExchange().getDefaultExchangeSpecification().getExchangeName()
                    + " for " + highBid.getBid()
                    + " and make a return (before fees) of "
                    + (difference.add(BigDecimal.valueOf(-1))).multiply(BigDecimal.valueOf(100))
                    + "%<p>");
        } else {
            setHTMLBODY("No arbitrage found");
        }

    }

    /**
     * Prints the low asking price and high selling price as well as the difference in the body of each email.
     * @param lowAsk
     * @param highBid
     * @param difference
     * @param arbitrageMargin
     * @return
     */
    public void buildTextBody(TickerData lowAsk, TickerData highBid, BigDecimal difference, double arbitrageMargin) {
        if (difference.compareTo(BigDecimal.valueOf(arbitrageMargin)) > 0) {

            setTEXTBODY("ARBITRAGE DETECTED!!!"
                    + " buy on " + lowAsk.getExchange().getDefaultExchangeSpecification().getExchangeName()
                    + " for " + lowAsk.getAsk()
                    + " and sell on " + highBid.getExchange().getDefaultExchangeSpecification().getExchangeName()
                    + " for " + highBid.getBid()
                    + " and make a return (before fees) of "
                    + (difference.add(BigDecimal.valueOf(-1))).multiply(BigDecimal.valueOf(100))
                    + "%");

        } else {

            setTEXTBODY("No arbitrage found");
        }

    }
}
