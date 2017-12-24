package co.codingnomads.bot.arbitrage.model.arbitrageAction.email;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Kevin Neag
 */
public class Email extends EmailBody{


    // Replace sender@example.com with your "From" address.
    // This address must be verified with Amazon SES.
    String FROM = "cryptoarbitragebot25@gmail.com";

    // The configuration set to use for this email. If you do not want to use a
    // configuration set, comment the following variable and the
    // .withConfigurationSetName(CONFIGSET); argument below.
    // static final String CONFIGSET = "ConfigSet";

    // The subject line for the email.
    String SUBJECT;

    // The HTML body for the email.
   String HTMLBODY;

    // The email body for recipients with non-HTML email clients.
    //TODO print action
    static  String TEXTBODY;


    public Email(double arbitrageMargin) {
        super(arbitrageMargin);
    }

    public Email(double arbitrageMargin, String FROM, String SUBJECT, String HTMLBODY) {
        super(arbitrageMargin);
        this.FROM = FROM;
        this.SUBJECT = SUBJECT;
        this.HTMLBODY = HTMLBODY;
    }

    public String getFROM() {
        return FROM;
    }

    public void setFROM(String FROM) {
        this.FROM = FROM;
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

    public static String getTEXTBODY() {
        return TEXTBODY;
    }

    public static void setTEXTBODY(String TEXTBODY) {
        Email.TEXTBODY = TEXTBODY;
    }


}
