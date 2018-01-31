package co.codingnomads.bot.arbitrage.service.general;

import co.codingnomads.bot.arbitrage.model.ticker.TickerData;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 * @author Kevin Neag
 */

/**
 * A Utill class for big decimal comparisons
 *
 */
public class MarginDiffCompare {


    /**
     * find the difference between the arbitrageMargin and the percent difference of the highBid and lowAsk
     * (((highBid - lowAsk)/highBid) * 100) - arbitrageMargin
     * @param lowAsk lowestAsk price
     * @param highBid highest BidPrice
     * @param arbitrageMargin percentage of returns you want to make
     * @return the difference between the arbitrage margin and percent difference highBid and lowAsk
     */
    public BigDecimal diffWithMargin(TickerData lowAsk, TickerData highBid, double arbitrageMargin) {

        MathContext mc = new MathContext(10);

        BigDecimal highBidMinusLowAsk = highBid.getBid().subtract(lowAsk.getAsk());

        BigDecimal difference = highBidMinusLowAsk.divide(highBid.getBid(), mc);

        BigDecimal diffPercent = difference.multiply(BigDecimal.valueOf(100));

        BigDecimal differenceOfMarginFormatted = BigDecimal.valueOf(arbitrageMargin);

        BigDecimal arbitragePerDiffSubMarg = diffPercent.subtract(differenceOfMarginFormatted);

        return arbitragePerDiffSubMarg;

    }


    /**
     * percent difference between highBid and lowAsk
     *(highBid - lowAsk)/highBid) * 100)
     * @param lowAsk lowest ask price
     * @param highBid highest bid price
     * @return percent difference highBid and lowAsk
     */
    public BigDecimal findDiff(TickerData lowAsk, TickerData highBid){

        MathContext mc = new MathContext(10);

        BigDecimal highBidMinusLowAsk = highBid.getBid().subtract(lowAsk.getAsk());

        BigDecimal difference = highBidMinusLowAsk.divide(highBid.getBid(), mc);

        BigDecimal diffPercent = difference.multiply(BigDecimal.valueOf(100));

        return diffPercent;

    }
}
