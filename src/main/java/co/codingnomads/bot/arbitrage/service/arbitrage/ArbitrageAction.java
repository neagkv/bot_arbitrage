//package co.codingnomads.bot.arbitrage.service.arbitrage;
//
//import co.codingnomads.bot.arbitrage.model.TickerData;
//import co.codingnomads.bot.arbitrage.model.arbitrageAction.ArbitrageTradingAction;
//import co.codingnomads.bot.arbitrage.model.arbitrageAction.email.ArbitrageEmailAction;
//import co.codingnomads.bot.arbitrage.model.arbitrageAction.trading.OrderIDWrapper;
//import co.codingnomads.bot.arbitrage.model.arbitrageAction.trading.TickerDataTrading;
//import co.codingnomads.bot.arbitrage.model.arbitrageAction.trading.TradingData;
//import co.codingnomads.bot.arbitrage.model.arbitrageAction.trading.WalletWrapper;
//import co.codingnomads.bot.arbitrage.model.exceptions.EmailLimitException;
//import co.codingnomads.bot.arbitrage.service.EmailService;
//import co.codingnomads.bot.arbitrage.service.arbitrage.trading.GetWalletWrapperThread;
//import co.codingnomads.bot.arbitrage.service.arbitrage.trading.MakeOrderThread;
//import com.amazonaws.regions.Regions;
//import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
//import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
//import com.amazonaws.services.simpleemail.model.Body;
//import com.amazonaws.services.simpleemail.model.Content;
//import com.amazonaws.services.simpleemail.model.Message;
//import com.amazonaws.services.simpleemail.model.SendEmailRequest;
//import org.knowm.xchange.currency.CurrencyPair;
//import org.knowm.xchange.dto.Order;
//import org.knowm.xchange.dto.account.Wallet;
//import org.knowm.xchange.dto.trade.MarketOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.util.concurrent.*;
//
///**
// * Created by Thomas Leruth on 12/14/17
// * <p>
// * Class to define the potential acting behaviors of the arbitrage bot
// */
//@Service
//public class ArbitrageAction {
//
//    @Autowired
//    EmailService emailService;
//
//
//
//    // TODO add email rate limit monitor method
//
//
//    /**
//     * Method to enable trading
//     *
//     * @param lowAsk                 TickerData for the best buy
//     * @param highBid                TickerData for the best sell
//     * @param arbitrageTradingAction Some instance variables needed to trade
//     * @return true if no arbitrage found or trading worked as expected
//     */
//    public boolean trade(TickerData lowAsk,
//                         TickerData highBid,
//                         ArbitrageTradingAction arbitrageTradingAction) {
//
//        BigDecimal difference = highBid.getBid().divide(lowAsk.getAsk(), 5, RoundingMode.HALF_EVEN);
//
//        if (difference.compareTo(BigDecimal.valueOf(arbitrageTradingAction.getArbitrageMargin())) > 0) {
//
//            BigDecimal tradeAmount = BigDecimal.valueOf(arbitrageTradingAction.getTradeValueBase());
//            CurrencyPair tradedPair = lowAsk.getCurrencyPair();
//            BigDecimal expectedDifferenceFormated = difference.add(BigDecimal.valueOf(-1)).multiply(BigDecimal.valueOf(100));
//
//            // temp
//            System.out.println("initiating trade of " + tradeAmount + tradedPair.base.toString() + " you should make a return (before fees) of "
//                    + expectedDifferenceFormated + "%");
//
//            // temp
//            boolean live = true; // just a security right now
//            if (live) {
//                MarketOrder marketOrderBuy = new MarketOrder(Order.OrderType.BID, tradeAmount, tradedPair);
//                MarketOrder marketOrderSell = new MarketOrder(Order.OrderType.ASK, tradeAmount, tradedPair);
//
//                //todo make into action trade?
//                String marketOrderBuyId = "failed";
//                String marketOrderSellId = "failed";
//                ExecutorService executorMakeOrder = Executors.newFixedThreadPool(2);
//                CompletionService<OrderIDWrapper> poolMakeOrder = new ExecutorCompletionService<>(executorMakeOrder);
//                poolMakeOrder.submit(new MakeOrderThread(marketOrderBuy, lowAsk));
//                poolMakeOrder.submit(new MakeOrderThread(marketOrderSell, highBid));
//
//                for (int i = 0; i < 2; i++) {
//                    try {
//                        OrderIDWrapper temp = poolMakeOrder.take().get();
//                        if (temp.getExchangeName().equals(lowAsk.getExchange().getDefaultExchangeSpecification().getExchangeName())) {
//                            marketOrderBuyId = temp.getOrderID();
//                        }
//                        if (temp.getExchangeName().equals(highBid.getExchange().getDefaultExchangeSpecification().getExchangeName())) {
//                            marketOrderSellId = temp.getOrderID();
//                        }
//                    } catch (InterruptedException | ExecutionException e) {
//                        e.printStackTrace();
//                    }
//                }
//                executorMakeOrder.shutdown();
//
//                // todo better handling of error (maybe while re-run it til we get an number OR checking if number is valid
//                // with another API call, I'd say return number is better but we need to try to see what happens if order fail
//                // I am expecting a null pointer for temp we need to handle that with better exception handing in make order ?
//                // I do not think "failed" will ever stay (either replace by ID or exception before)
//                if (marketOrderBuyId.equals("failed")) System.out.println("marketOrderBuy failed");
//                if (marketOrderSellId.equals("failed")) System.out.println("marketOrderSell failed");
//                if (!marketOrderBuyId.equals("failed") && !marketOrderSellId.equals("failed")) {
//                    System.out.println("buy order " + marketOrderBuyId);
//                    System.out.println("sell order " + marketOrderSellId);
//                    System.out.println("trades successful");
//                }
//            }
//
//            // todo trade get wallet?
//            Wallet walletBuy = null;
//            Wallet walletSell = null;
//            ExecutorService executorWalletWrapper = Executors.newFixedThreadPool(2);
//            CompletionService<WalletWrapper> poolWalletWrapper = new ExecutorCompletionService<>(executorWalletWrapper);
//
//            poolWalletWrapper.submit(new GetWalletWrapperThread(lowAsk));
//            poolWalletWrapper.submit(new GetWalletWrapperThread(highBid));
//            for (int i = 0; i < 2; i++) {
//                try {
//                    WalletWrapper temp = poolWalletWrapper.take().get();
//                    if (temp.getExchangeName().equals(lowAsk.getExchange().getDefaultExchangeSpecification().getExchangeName())) {
//                        walletBuy = temp.getWallet();
//                    }
//                    if (temp.getExchangeName().equals(highBid.getExchange().getDefaultExchangeSpecification().getExchangeName())) {
//                        walletSell = temp.getWallet();
//                    }
//                } catch (InterruptedException | ExecutionException e) {
//                    e.printStackTrace();
//                }
//            }
//            executorWalletWrapper.shutdown();
//
//            // calculate a bunch of data
//            TradingData tradingData =
//                    new TradingData(
//                            (TickerDataTrading) lowAsk,
//                            (TickerDataTrading) highBid,
//                            walletBuy,
//                            walletSell);
//
//            // this need to be tested
//            System.out.println();
//            System.out.println("your base moved by (should be 0%) " + tradingData.getDifferenceCounterSell() + "%");
//            System.out.println("real bid was " + tradingData.getRealBid()
//                    + " and real ask was " + tradingData.getRealAsk()
//                    + " for a difference (after fees) of " + tradingData.getRealDifferenceFormated()
//                    + "% vs an expected of " + expectedDifferenceFormated + " %");
//        } else {
//            System.out.println("No arbitrage found");
//            return true;
//        }
//        return false;
//        // todo return flag true to continue as long as realDifference is positive and differenceTotalBase did not move
//    }
//}
