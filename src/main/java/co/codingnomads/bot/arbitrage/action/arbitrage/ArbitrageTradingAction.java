package co.codingnomads.bot.arbitrage.action.arbitrage;

import co.codingnomads.bot.arbitrage.action.arbitrage.selection.ArbitrageActionSelection;
import co.codingnomads.bot.arbitrage.exception.ExchangeDataException;
import co.codingnomads.bot.arbitrage.model.ticker.TickerData;
import co.codingnomads.bot.arbitrage.model.trading.OrderIDWrapper;
import co.codingnomads.bot.arbitrage.model.ticker.TickerDataTrading;
import co.codingnomads.bot.arbitrage.model.trading.TradingData;
import co.codingnomads.bot.arbitrage.model.trading.WalletWrapper;
import co.codingnomads.bot.arbitrage.service.thread.GetWalletWrapperThread;
import co.codingnomads.bot.arbitrage.service.thread.MakeOrderThread;
import co.codingnomads.bot.arbitrage.service.tradehistory.TradeHistoryService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.*;

/**
 * Created by Thomas Leruth on 12/17/17
 * <p>
 * class for the information needed to use the trading action as behavior action
 */
@Service
public class ArbitrageTradingAction extends ArbitrageActionSelection {

    @Autowired
    TradeHistoryService tradeHistoryService;

    private double tradeValueBase;

    int round =0;

    public ArbitrageTradingAction() {

    }

    public ArbitrageTradingAction(double arbitrageMargin, double tradeValueBase) {
        super(arbitrageMargin);
        this.tradeValueBase = tradeValueBase;
    }

    public double getTradeValueBase() {
        return tradeValueBase;
    }

    public void setTradeValueBase(double tradeValueBase) {
        this.tradeValueBase = tradeValueBase;
    }


    public boolean canTrade(TickerData lowAsk,
                            TickerData highBid,
                            ArbitrageTradingAction arbitrageTradingAction) {


        if (highBid.getExchange().getExchangeSpecification().getExchangeName() == lowAsk.getExchange().getExchangeSpecification().getExchangeName()) {

            System.out.println("###########################################################");
            System.out.println("low ask exchange is the same as high bid exchange");
            System.out.println("###########################################################");
        }


            //find the difference between the highest bid and lowest ask
            BigDecimal difference = highBid.getBid().divide(lowAsk.getAsk(), 5, RoundingMode.HALF_EVEN);
            System.out.println("difference" + difference);



            //if the best price difference is greater than the value of the arbitrage margin you want
            if (difference.compareTo(BigDecimal.valueOf(arbitrageTradingAction.getArbitrageMargin())) < 0) {

                System.out.println("congrats you made it inside this if statement");

                System.out.println(lowAsk.getExchange().getExchangeSpecification().getExchangeName());

                System.out.println("buy is  " + lowAsk.getAsk());

                System.out.println("sell is  " + highBid.getBid());

                System.out.println("differenc is " + difference);

                System.out.println("big decimal arbitrage margin" + BigDecimal.valueOf(arbitrageTradingAction.getArbitrageMargin()));
            }

            //amount chosen to trade
            BigDecimal tradeAmount = BigDecimal.valueOf(arbitrageTradingAction.getTradeValueBase());
            System.out.println("trade amount" + tradeAmount);

            //currency pair of the lowest ask
            CurrencyPair tradedPair = lowAsk.getCurrencyPair();
            System.out.println(tradedPair);

            //expected difference from trade
            BigDecimal expectedDifferenceFormated = difference.add(BigDecimal.valueOf(1)).multiply(BigDecimal.valueOf(100));
            System.out.println("difference formatted " + expectedDifferenceFormated);

            if (expectedDifferenceFormated.compareTo(BigDecimal.ZERO) > 100) {

                //print the expected trade
                System.out.println("==========================================================");
                System.out.println();
                System.out.println("ARBITRAGE DETECTED!!!"
                        + " buy on " + lowAsk.getExchange().getDefaultExchangeSpecification().getExchangeName()
                        + " for " + lowAsk.getAsk()
                        + " and sell on " + highBid.getExchange().getDefaultExchangeSpecification().getExchangeName()
                        + " for " + highBid.getBid());
                System.out.println("initiating trade of " + tradeAmount + " " + tradedPair.base.toString() + " you should make a return (before fees) of "
                        + expectedDifferenceFormated + "%");
                System.out.println();
                System.out.println("==========================================================");
                round++;
                System.out.println("round:" + round);
                return true;

            } else {
                //else not a good trade, return false
                System.out.println("==========================================================");
                System.out.println();
                System.out.println("No profitable arbitrage found");
                System.out.println("==========================================================");
                round++;
                System.out.println("round:" + round);
            }
            return false;
        }



    public void makeTrade(TickerData lowAsk,
                          TickerData highBid,
                          ArbitrageTradingAction arbitrageTradingAction) {

        //difference between the highest ask and lowest sell
        BigDecimal difference = highBid.getBid().divide(lowAsk.getAsk(), 5, RoundingMode.HALF_EVEN);
        //
        BigDecimal tradeAmount = BigDecimal.valueOf(arbitrageTradingAction.getTradeValueBase());
        //currency pair of the lowest ask
        CurrencyPair tradedPair = lowAsk.getCurrencyPair();
        //expected difference from trade
        BigDecimal expectedDifferenceFormated = difference.add(BigDecimal.valueOf(-1)).multiply(BigDecimal.valueOf(100));

        //MarketOrder object for the buy and sell
        MarketOrder marketOrderBuy = new MarketOrder(Order.OrderType.BID, tradeAmount, tradedPair);
        MarketOrder marketOrderSell = new MarketOrder(Order.OrderType.ASK, tradeAmount, tradedPair);

//        System.out.println();
//        System.out.println(marketOrderBuy.toString());
//        System.out.println(marketOrderSell.toString());
//        System.out.println();


        //set marketOrderBuyId, and marketOrderSellId to failed
        String marketOrderBuyId = "failed";
        String marketOrderSellId = "failed";

        //make a fixed thread pool of 2  to submit the order for the low ask and high bid
        ExecutorService executorMakeOrder = Executors.newFixedThreadPool(2);
        CompletionService<OrderIDWrapper> poolMakeOrder = new ExecutorCompletionService<>(executorMakeOrder);
        poolMakeOrder.submit(new

                MakeOrderThread(marketOrderBuy, lowAsk));

        poolMakeOrder.submit(new

                MakeOrderThread(marketOrderSell, highBid));


//                 Ryan: Does this always work? It appears as though you submit the tasks above and then immediate use the
//                 return values below, but outside the task thread. Is there anything that guarantees the lines below will not
//                 execute before the tasks above are complete?

        //for two loops insert the thread
        for (
                int i = 0;
                i < 2; i++)

        {
            try {

                OrderIDWrapper temp = poolMakeOrder.take().get();
                if (temp.getExchangeName().equals(lowAsk.getExchange().getDefaultExchangeSpecification().getExchangeName())) {
                    marketOrderBuyId = temp.getOrderID();
                    System.out.println(temp.getOrderID());
                }
                if (temp.getExchangeName().equals(highBid.getExchange().getDefaultExchangeSpecification().getExchangeName())) {
                    marketOrderSellId = temp.getOrderID();
                    System.out.println(temp.getOrderID());
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        executorMakeOrder.shutdown();

        //if either the buy order or the sell order did not fail, continue. If they did fail print which order failed
        if (marketOrderBuyId.equals("failed")) System.out.println("marketOrderBuy failed");
        System.out.println("===============================================================");
        if (marketOrderSellId.equals("failed")) System.out.println("marketOrderSell failed");
        System.out.println("===============================================================");
        if (!marketOrderBuyId.equals("failed") && !marketOrderSellId.equals("failed"))

        {   //print the order id for buyId and sellId
            System.out.println("===============================================================");
            System.out.println("buy order " + marketOrderBuyId);
            System.out.println("===============================================================");
            System.out.println("sell order " + marketOrderSellId);
            System.out.println("===============================================================");
            System.out.println("trades successful");
            System.out.println("===============================================================");
        }
        // todo trade get wallet?
        Wallet walletBuy = null;
        Wallet walletSell = null;
        ExecutorService executorWalletWrapper = Executors.newFixedThreadPool(2);
        CompletionService<WalletWrapper> poolWalletWrapper = new ExecutorCompletionService<>(executorWalletWrapper);

        poolWalletWrapper.submit(new GetWalletWrapperThread(lowAsk));
        poolWalletWrapper.submit(new GetWalletWrapperThread(highBid));

        //Ryan: same comment as above
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

        // calculate a bunch of data
        TradingData tradingData =
                new TradingData(
                        (TickerDataTrading) lowAsk,
                        (TickerDataTrading) highBid,
                        walletBuy,
                        walletSell);

        // this need to be tested
        System.out.println();
        System.out.println("real bid was " + tradingData.getRealBid()
                + " and real ask was " + tradingData.getRealAsk()
                + " for a difference (after fees) of " + tradingData.getRealDifferenceFormated()
                + "% vs an expected of " + expectedDifferenceFormated + " %");

    }
}


