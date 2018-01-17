package co.codingnomads.bot.arbitrage.action.arbitrage;

import co.codingnomads.bot.arbitrage.action.arbitrage.selection.ArbitrageActionSelection;
import co.codingnomads.bot.arbitrage.model.ticker.TickerData;
import co.codingnomads.bot.arbitrage.model.trading.OrderIDWrapper;
import co.codingnomads.bot.arbitrage.model.ticker.TickerDataTrading;
import co.codingnomads.bot.arbitrage.model.trading.TradingData;
import co.codingnomads.bot.arbitrage.model.trading.WalletWrapper;
import co.codingnomads.bot.arbitrage.service.thread.GetWalletWrapperThread;
import co.codingnomads.bot.arbitrage.service.thread.MakeOrderThread;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.*;

/**
 * Created by Thomas Leruth on 12/17/17
 *
 * class for the information needed to use the trading action as behavior action
 */
@Service
public class ArbitrageTradingAction extends ArbitrageActionSelection {

    private double tradeValueBase;

    public ArbitrageTradingAction(){

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


    /**
     * Method to enable trading
     *
     * @param lowAsk                 TickerData for the best buy
     * @param highBid                TickerData for the best sell
     * @param arbitrageTradingAction Some instance variables needed to trade
     * @return true if no arbitrage found or trading worked as expected
     */
    public boolean trade(TickerData lowAsk,
                         TickerData highBid,
                         ArbitrageTradingAction arbitrageTradingAction) {

        // Ryan: general note - is there any way this method could be broken into 2 or 3 simpler, more single-focus methods?
        // anytime you see a method getting near or over the 100+ lines mark it's definitely worth considering how to break into
        // smaller, more concise methods

        BigDecimal difference = highBid.getBid().divide(lowAsk.getAsk(), 5, RoundingMode.HALF_EVEN);

        if (difference.compareTo(BigDecimal.valueOf(arbitrageTradingAction.getArbitrageMargin())) > 0) {

            BigDecimal tradeAmount = BigDecimal.valueOf(arbitrageTradingAction.getTradeValueBase());
            CurrencyPair tradedPair = lowAsk.getCurrencyPair();
            BigDecimal expectedDifferenceFormated = difference.add(BigDecimal.valueOf(-1)).multiply(BigDecimal.valueOf(100));

            // temp
            System.out.println("initiating trade of " + tradeAmount + tradedPair.base.toString() + " you should make a return (before fees) of "
                    + expectedDifferenceFormated + "%");

            // temp
            boolean live = true; // just a security right now
            if (live) {
                MarketOrder marketOrderBuy = new MarketOrder(Order.OrderType.BID, tradeAmount, tradedPair);
                MarketOrder marketOrderSell = new MarketOrder(Order.OrderType.ASK, tradeAmount, tradedPair);

                //todo make into action trade?
                // Ryan: not sure about action trade. But I do think some in-line commenting is due here just so it's
                // 100% clear what's happening line by line
                String marketOrderBuyId = "failed";
                String marketOrderSellId = "failed";
                ExecutorService executorMakeOrder = Executors.newFixedThreadPool(2);
                CompletionService<OrderIDWrapper> poolMakeOrder = new ExecutorCompletionService<>(executorMakeOrder);
                poolMakeOrder.submit(new MakeOrderThread(marketOrderBuy, lowAsk));
                poolMakeOrder.submit(new MakeOrderThread(marketOrderSell, highBid));


                // Ryan: Does this always work? It appears as though you submit the tasks above and then immediate use the
                // return values below, but outside the task thread. Is there anything that guarantees the lines below will not
                // execute before the tasks above are complete?
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
                // with another API call, I'd say return number is better but we need to try to see what happens if order fail
                // I am expecting a null pointer for temp we need to handle that with better exception handing in make order ?
                // I do not think "failed" will ever stay (either replace by ID or exception before)
                // Ryan: I'd need to be able to run this to test how it's all working. See previous note.
                if (marketOrderBuyId.equals("failed")) System.out.println("marketOrderBuy failed");
                if (marketOrderSellId.equals("failed")) System.out.println("marketOrderSell failed");
                if (!marketOrderBuyId.equals("failed") && !marketOrderSellId.equals("failed")) {
                    System.out.println("buy order " + marketOrderBuyId);
                    System.out.println("sell order " + marketOrderSellId);
                    System.out.println("trades successful");
                }
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
            System.out.println("your base moved by (should be 0%) " + tradingData.getDifferenceCounterSell() + "%");
            System.out.println("real bid was " + tradingData.getRealBid()
                    + " and real ask was " + tradingData.getRealAsk()
                    + " for a difference (after fees) of " + tradingData.getRealDifferenceFormated()
                    + "% vs an expected of " + expectedDifferenceFormated + " %");
        } else {
            System.out.println("No arbitrage found");
            return true;
        }
        return false;
        // todo return flag true to continue as long as realDifference is positive and differenceTotalBase did not move
    }

}
