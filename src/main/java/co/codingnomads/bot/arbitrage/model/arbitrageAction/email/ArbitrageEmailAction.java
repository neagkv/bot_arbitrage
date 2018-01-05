package co.codingnomads.bot.arbitrage.model.arbitrageAction.email;
import co.codingnomads.bot.arbitrage.model.TickerData;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.ArbitrageActionSelection;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.ArbitragePrintAction;
import co.codingnomads.bot.arbitrage.model.exceptions.EmailLimitException;
import co.codingnomads.bot.arbitrage.service.EmailService;
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
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author Kevin Neag
 */

/**
 * Email pojo class than extends the EmailBody class
 * Provides the From email address to a pre-verified email address that is allowed to send 200 emails per day
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


    public ArbitrageEmailAction(double arbitrageMargin, String FROM, String TO, String SUBJECT, String HTMLBODY, String TEXTBODY, String timeEmailSent) {
        super(arbitrageMargin);
        email.setFROM(FROM);
        email.setTO(TO);
        email.setSUBJECT(SUBJECT);
        email.setHTMLBODY(HTMLBODY);
        email.setTEXTBODY(TEXTBODY);
        email.setTimeEmailSent(timeEmailSent);

    }

    public ArbitrageEmailAction(double arbitrageMargin, String TO) {
        super(arbitrageMargin);
        email.setTO(TO);

    }

    public ArbitrageEmailAction() {
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }




    public void email(Email email,
                      TickerData lowAsk, TickerData highBid, BigDecimal difference, double margin) throws EmailLimitException {


        if (!emailService.underEmailLimit()) {


            throw new EmailLimitException("Email limit reached for the Day, please try again tomorrow");
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
