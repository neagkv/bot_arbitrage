package co.codingnomads.bot.arbitrage.service.arbitrage;

import co.codingnomads.bot.arbitrage.model.BidAsk;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.ArbitrageEmailAction;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.ArbitrageTradingAction;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.email.Email;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.email.EmailBody;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.trading.OrderIDWrapper;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.trading.TradingData;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.trading.WalletWrapper;
import co.codingnomads.bot.arbitrage.service.arbitrage.trading.GetWalletWrapperThread;
import co.codingnomads.bot.arbitrage.service.arbitrage.trading.MakeOrderThread;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.*;

/**
 * Created by Thomas Leruth on 12/14/17
 */

/**
 * Class to define the potential action once after the difference in price is calculate
 */
@Service
public class ArbitrageAction {

    /**
     * A method to print the arbitrage action to the console
     * @param lowAsk the lowest ask found (buy)
     * @param highBid the highest bid found (sell)
     * @param difference the difference in prices
     * @param arbitrageMargin the margin difference accepted (not a valid arbitrage is below that value)
     */
    public void print(BidAsk lowAsk, BidAsk highBid, BigDecimal difference, double arbitrageMargin){
         if (difference.compareTo(BigDecimal.valueOf(arbitrageMargin)) > 0) {
            System.out.println("ARBITRAGE DETECTED!!!"
                    + " buy on " + lowAsk.getExchange().getDefaultExchangeSpecification().getExchangeName()
                    + " for " + lowAsk.getAsk()
                    + " and sell on " + highBid.getExchange().getDefaultExchangeSpecification().getExchangeName()
                    + " for " + highBid.getBid()
                    + " and make a return (before fees) of "
                    + (difference.add(BigDecimal.valueOf(-1))).multiply(BigDecimal.valueOf(100))
                    + "%");
        } else {
            System.out.println("No arbitrage found");
        }
    }

    public void email (ArbitrageEmailAction arbitrageEmailAction, Email email, EmailBody emailBody,
                       BidAsk lowAsk, BidAsk highBid, BigDecimal difference, double arbitrageMargin){

        try {

            AmazonSimpleEmailService client =
                    AmazonSimpleEmailServiceClientBuilder.standard()
                            // Replace US_EAST_1 with the AWS Region you're using for
                            // Amazon SES.
                            .withRegion(Regions.US_EAST_1).build();
            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(
                            new com.amazonaws.services.simpleemail.model.Destination().withToAddresses(arbitrageEmailAction.getEmail()))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withHtml(new Content()
                                            .withCharset("UTF-8").withData(emailBody.printTextBody(lowAsk,highBid,difference,arbitrageMargin)))
                                    .withText(new Content()
                                            .withCharset("UTF-8").withData(emailBody.printTextBody(lowAsk,highBid,difference,arbitrageMargin))))
                            .withSubject(new Content()
                                    .withCharset("UTF-8").withData(emailBody.printTextBody(lowAsk,highBid,difference,arbitrageMargin))))
                    .withSource(email.getFROM());
            // Comment or remove the next line if you are not using a
            // configuration set
            // .withConfigurationSetName(CONFIGSET);
            client.sendEmail(request);
            System.out.println("Email sent!");
        } catch (Exception ex) {
            System.out.println("The email was not sent. Error message: "
                    + ex.getMessage());
        }
    }

    public void trade(BidAsk lowAsk,
                      BidAsk highBid,
                      BigDecimal difference,
                      ArbitrageTradingAction arbitrageTradingAction)
    {

        if (difference.compareTo(BigDecimal.valueOf(arbitrageTradingAction.getArbitrageMargin())) > 0) {

            BigDecimal tradeAmount = BigDecimal.valueOf(arbitrageTradingAction.getTradeValueBase());
            CurrencyPair tradedPair = lowAsk.getCurrencyPair();
            BigDecimal expectedDifferenceFormated = difference.add(BigDecimal.valueOf(-1)).multiply(BigDecimal.valueOf(100));

            System.out.println("initiating trade of " + tradeAmount + tradedPair.base.toString() + " you should make a return (before fees) of "
                    + expectedDifferenceFormated + "%");

            boolean live = false; // just a security right now
            if (live) {
                MarketOrder marketOrderBuy = new MarketOrder(Order.OrderType.BID, tradeAmount, tradedPair);
                MarketOrder marketOrderSell = new MarketOrder(Order.OrderType.ASK, tradeAmount, tradedPair);

                String marketOrderBuyId = "failed";
                String marketOrderSellId = "failed";

                ExecutorService executorMakeOrder = Executors.newFixedThreadPool(2);
                CompletionService<OrderIDWrapper> poolMakeOrder = new ExecutorCompletionService<>(executorMakeOrder);
                poolMakeOrder.submit(new MakeOrderThread(marketOrderBuy, lowAsk));
                poolMakeOrder.submit(new MakeOrderThread(marketOrderSell, highBid));

                for (int i = 0; i < 2; i++) {
                    try {
                        OrderIDWrapper temp = poolMakeOrder.take().get();
                        if (temp.getExchangeName().equals(lowAsk.getExchange().getDefaultExchangeSpecification().getExchangeName())) {
                            marketOrderBuyId = temp.getOrderID();
                        }
                        if (temp.getExchangeName().equals(highBid.getExchange().getDefaultExchangeSpecification().getExchangeName())) {
                            marketOrderSellId = temp.getOrderID();
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
                executorMakeOrder.shutdown();

                // todo better handling of error (maybe while re-run it til we get an number OR checking if number is valid
                // with another API call?
                if (marketOrderBuyId.equals("failed")) System.out.println("marketOrderBuy failed");
                if (marketOrderSellId.equals("failed")) System.out.println("marketOrderSell failed");
                if (!marketOrderBuyId.equals("failed") && !marketOrderSellId.equals("failed")){
                    System.out.println("buy order " + marketOrderBuyId);
                    System.out.println("sell order " + marketOrderSellId);
                    System.out.println("trades successful");
                }
            }

            Wallet walletBuy = null;
            Wallet walletSell = null;

            ExecutorService executorWalletWrapper = Executors.newFixedThreadPool(2);
            CompletionService<WalletWrapper> poolWalletWrapper = new ExecutorCompletionService<>(executorWalletWrapper);

            // The issue with pool is that it comes out in unpredictable order so I have to check
            // not really pretty but I still think more efficient then run API consequently
            poolWalletWrapper.submit(new GetWalletWrapperThread(lowAsk));
            poolWalletWrapper.submit(new GetWalletWrapperThread(highBid));
            for (int i = 0; i < 2; i++) {
                try {
                    WalletWrapper temp = poolWalletWrapper.take().get();
                    if (temp.getExchangeName().equals(lowAsk.getExchange().getDefaultExchangeSpecification().getExchangeName())) {
                        walletBuy = temp.getWallet();
                    }
                    if (temp.getExchangeName().equals(highBid.getExchange().getDefaultExchangeSpecification().getExchangeName())) {
                        walletSell = temp.getWallet();
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            executorWalletWrapper.shutdown();

            TradingData tradingData = new TradingData(lowAsk, highBid, walletBuy, walletSell);

            // this need to be tested
            System.out.println();
            System.out.println("your base moved by (should be 0%) " + tradingData.getDifferenceCounterSell() + "%");
            System.out.println("real bid was " + tradingData.getRealBid()
                    + " and real ask was " + tradingData.getRealAsk()
                    + " for a difference (after fees) of " + tradingData.getRealDifferenceFormated()
                    + "% vs an expected of " + expectedDifferenceFormated + " %");

        } else {
            System.out.println("No arbitrage found");
        }
        // todo return flag true to continue as long as realDifference is positive and differenceTotalBase did not move
    }
}
