package co.codingnomads.bot.arbitrage.model.email;

import co.codingnomads.bot.arbitrage.model.ticker.TickerData;
import co.codingnomads.bot.arbitrage.service.general.MarginDiffCompare;

import java.math.BigDecimal;

/**
 * @author Kevin Neag
 */
public class Email {

    // This address must be verified with Amazon SES.
    String FROM = "ENTER THE EMAIL ADDRESS YOU WOULD LIKE TO SEND EMAILS HERE";

    String TO;

    // The subject line for the email.
    String SUBJECT;

    // The HTML body for the email.
    public String HTMLBODY;

    // The email body for recipients with non-HTML email clients.
    public String TEXTBODY;

    java.util.Date dt = new java.util.Date();

    java.text.SimpleDateFormat sdf =
            new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    String timeEmailSent = sdf.format(dt);

    /**
     *  Empty Constructor
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
     * @param lowAsk    the lowest ask found (buy)
     * @param highBid   the highest bid found (sell)
     * @param arbitrageMargin the margin difference accepted (not a valid arbitrage if below that value)
     */
    public void buildHTMLBody(TickerData lowAsk, TickerData highBid, double arbitrageMargin) {

        //find the difference between the arbitrage margin and the percent difference of the highBid and lowAsk
        MarginDiffCompare marginDiffCompare = new MarginDiffCompare();

        //percentage of returns you will make
        BigDecimal difference = marginDiffCompare.findDiff(lowAsk,highBid);

        //the difference between the arbitrage margin and percentage of returns
        BigDecimal marginSubDiff = marginDiffCompare.diffWithMargin(lowAsk,highBid,arbitrageMargin);

        //if the arbitrage margin - the percent difference of highBid and lowAsk is greater than zero
        if (marginSubDiff.compareTo(BigDecimal.ZERO) > 0) {
            setHTMLBODY("<h1>ARBITRAGE DETECTED!!!<h1>"
                    + " <p>buy on " + lowAsk.getExchange().getDefaultExchangeSpecification().getExchangeName()
                    + " for " + lowAsk.getAsk()
                    + " and sell on " + highBid.getExchange().getDefaultExchangeSpecification().getExchangeName()
                    + " for " + highBid.getBid()
                    + " and make a return (before fees) of "
                    + difference
                    + "%<p>");
        } else {
            setHTMLBODY("No arbitrage found");
        }

    }

    /**
     * Prints the low asking price and high selling price as well as the difference in the body of each email.
     * @param lowAsk    the lowest ask found (buy)
     * @param highBid   the highest bid found (sell)
     * @param arbitrageMargin the margin difference accepted (not a valid arbitrage if below that value)
     */
    public void buildTextBody(TickerData lowAsk, TickerData highBid, double arbitrageMargin) {

        //find the difference between the arbitrage margin and the percent difference of the highBid and lowAsk
        MarginDiffCompare marginDiffCompare = new MarginDiffCompare();

        //percentage of returns you will make
        BigDecimal difference = marginDiffCompare.findDiff(lowAsk,highBid);

        //the difference between the arbitrage margin and percentage of returns
        BigDecimal marginSubDiff = marginDiffCompare.diffWithMargin(lowAsk,highBid,arbitrageMargin);

        //if the arbitrage margin - the percent difference of highBid and lowAsk is greater than zero
        if (marginSubDiff.compareTo(BigDecimal.ZERO) > 0) {

            setTEXTBODY("ARBITRAGE DETECTED!!!"
                    + " buy on " + lowAsk.getExchange().getDefaultExchangeSpecification().getExchangeName()
                    + " for " + lowAsk.getAsk()
                    + " and sell on " + highBid.getExchange().getDefaultExchangeSpecification().getExchangeName()
                    + " for " + highBid.getBid()
                    + " and make a return (before fees) of "
                    + difference
                    + "%");

        } else {

            setTEXTBODY("No arbitrage found");
        }

    }
}
