package co.codingnomads.bot.arbitrage.model.arbitrageAction;

import co.codingnomads.bot.arbitrage.model.TickerData;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.email.Email;
import co.codingnomads.bot.arbitrage.model.exceptions.EmailLimitException;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.service.EmailService;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

/**
 * @author Kevin Neag
 */

/**
 * Email pojo class than extends ArbitrageActionSelection class
 * Class contains and autowired EmailService objects and a new Email object
 * Class also has an email method witch uses the Amazon AWS SES API, in order to use
 * this api you must create an aws account at aws.amazon.com, very your email address
 * at SES services, and also obtain credentials to be saved in a .aws directory.
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Service
public class ArbitrageEmailAction extends ArbitrageActionSelection {

    @Autowired
    EmailService emailService;

    Email email = new Email();


    public ArbitrageEmailAction(double arbitrageMargin) {
        super(arbitrageMargin);
    }

    /**
     * Fully qualified constructor
     * @param arbitrageMargin the margin difference accepted (not a valid arbitrage if below that value)
     * @param FROM  the email address that will send email (set at default to cryptoarbitragebot25@gmail.com
     * @param TO    the email address where email alerts will be sent
     * @param SUBJECT   the subject of the email
     * @param HTMLBODY  the email message body in HTML format
     * @param TEXTBODY  the email message body
     * @param timeEmailSent the time that the email is sent
     */
    public ArbitrageEmailAction(double arbitrageMargin, String FROM, String TO, String SUBJECT, String HTMLBODY, String TEXTBODY, String timeEmailSent) {
        super(arbitrageMargin);
        email.setFROM(FROM);
        email.setTO(TO);
        email.setSUBJECT(SUBJECT);
        email.setHTMLBODY(HTMLBODY);
        email.setTEXTBODY(TEXTBODY);
        email.setTimeEmailSent(timeEmailSent);

    }

    /**
     * Constructor with the arbitrageMargin and TO email address to be set by the user
     * @param arbitrageMargin the margin difference accepted (not a valid arbitrage if below that value)
     * @param TO    the email address where email alerts will be sent
     */
    public ArbitrageEmailAction(double arbitrageMargin, String TO) {
        super(arbitrageMargin);
        email.setTO(TO);

    }

    /**
     *Empty constructor
     */
    public ArbitrageEmailAction() {

    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    /**
     * Email method that checks to see if the email is under the email rate limit before sending.
     * the method uses an email being taken in to call the methods buildHTMLBody, buildTEXTBody and setSubject method to
     * set custom messages based on the highbid, lowask and difference. Then sends the email using the Amazon SES api.
     * @param email     object from Email Class
     * @param lowAsk    the lowest ask found (buy)
     * @param highBid   the highest bid found (sell)
     * @param difference    price difference between the lowest ask and highest bid
     * @param margin    the margin difference accepted (not a valid arbitrage if below that value)
     * @throws EmailLimitException
     */
    public void email(Email email,
                      TickerData lowAsk, TickerData highBid, BigDecimal difference, double margin) throws EmailLimitException {


        if (!emailService.underEmailLimit()) {


            throw new EmailLimitException("Email limit reached for the day, please try again tomorrow");
        }
        email.buildHTMLBody(lowAsk,highBid,difference,margin);
        email.buildTextBody(lowAsk,highBid,difference,margin);
        email.setSUBJECT("Arbitrage Update");
        try {

            AmazonSimpleEmailService client =
                    AmazonSimpleEmailServiceClientBuilder.standard()
                            // Replace US_EAST_1 with the AWS Region you're using for
                            // Amazon SES.
                            .withRegion(Regions.US_EAST_1).build();
            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(
                            new com.amazonaws.services.simpleemail.model.Destination().withToAddresses(email.getTO()))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withHtml(new Content()
                                            .withCharset("UTF-8").withData(email.HTMLBODY))
                                    .withText(new Content()
                                            .withCharset("UTF-8").withData(email.TEXTBODY)))
                            .withSubject(new Content()
                                    .withCharset("UTF-8").withData(email.getSUBJECT())))
                    .withSource(email.getFROM());
            client.sendEmail(request);
            System.out.println("Email sent!");
        } catch (Exception ex) {
            System.out.println("The email was not sent. Error message: "
                    + ex.getMessage());
        }
    }

}
