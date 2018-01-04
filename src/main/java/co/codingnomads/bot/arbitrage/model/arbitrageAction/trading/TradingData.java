package co.codingnomads.bot.arbitrage.model.arbitrageAction.trading;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Wallet;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Thomas Leruth on 12/19/17
 *
 * POJO class setting a lot of needed data for the trading action
 */

public class TradingData {

    Currency base;
    Currency counter;
    BigDecimal oldBaseBuy;
    BigDecimal oldCounterBuy;
    BigDecimal oldBaseSell;
    BigDecimal oldCounterSell;
    BigDecimal newBaseBuy;
    BigDecimal newCounterBuy;
    BigDecimal newBaseSell;
    BigDecimal newCounterSell;
    BigDecimal oldTotalBase;
    BigDecimal newTotalBase;
    BigDecimal differenceBaseBuy;
    BigDecimal differenceCounterBuy;
    BigDecimal differenceBaseSell;
    BigDecimal differenceCounterSell;
    BigDecimal realAsk;
    BigDecimal realBid;
    BigDecimal differenceBidAsk;
    BigDecimal realDifferenceFormated;
    BigDecimal differenceTotalBase;


    public TradingData(TickerDataTrading lowAsk, TickerDataTrading highBid, Wallet walletBuy, Wallet walletSell) {

        this.base = lowAsk.getCurrencyPair().base;
        this.counter = lowAsk.getCurrencyPair().counter;
        this.oldBaseBuy = lowAsk.getBaseFund();
        this.oldCounterBuy = lowAsk.getCounterFund();
        this.oldBaseSell = highBid.getBaseFund();
        this.oldCounterSell = highBid.getCounterFund();
        this.newBaseBuy = walletBuy.getBalance(base).getTotal();
        this.newCounterBuy = walletBuy.getBalance(counter).getTotal();
        this.newBaseSell = walletSell.getBalance(base).getTotal();
        this.newCounterSell = walletSell.getBalance(counter).getTotal();
        this.oldTotalBase = oldBaseBuy.add(oldBaseSell);
        this.newTotalBase = newBaseBuy.add(newBaseSell);
        this.differenceBaseBuy = newBaseBuy.subtract(oldBaseBuy);
        this.differenceCounterBuy = newCounterBuy.subtract(oldCounterBuy);
        this.differenceBaseSell = newBaseSell.subtract(oldBaseSell);
        this.differenceCounterSell = newCounterSell.subtract(oldCounterSell);
        this.realAsk = differenceCounterBuy.divide(differenceBaseBuy, 5, BigDecimal.ROUND_HALF_EVEN).abs();
        this.realBid = differenceCounterSell.divide(differenceBaseSell, 5, BigDecimal.ROUND_HALF_EVEN).abs();
        this.differenceBidAsk = realBid.divide(realAsk, 5, RoundingMode.HALF_EVEN);
        this.realDifferenceFormated = differenceBidAsk.add(BigDecimal.valueOf(-1)).multiply(BigDecimal.valueOf(100));
        this.differenceTotalBase = newTotalBase.divide(oldTotalBase, 5, BigDecimal.ROUND_HALF_EVEN).add(BigDecimal.valueOf(-1)).multiply(BigDecimal.valueOf(100));
    }

    public BigDecimal getDifferenceCounterSell() {
        return differenceCounterSell;
    }

    public BigDecimal getRealAsk() {
        return realAsk;
    }

    public BigDecimal getRealBid() {
        return realBid;
    }

    public BigDecimal getRealDifferenceFormated() {
        return realDifferenceFormated;
    }
}
