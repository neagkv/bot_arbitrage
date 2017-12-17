package co.codingnomads.bot.arbitrage.service;

import co.codingnomads.bot.arbitrage.model.BidAsk;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;

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
                    + " buy on " + lowAsk.getExchangeName() + " for " + lowAsk.getAsk()
                    + " and sell on " + highBid.getExchangeName() + " for " + highBid.getBid()
                    + " and make a return (before fees) of "
                    + (difference.add(BigDecimal.valueOf(-1))).multiply(BigDecimal.valueOf(100))
                    + "%");
        } else {
            System.out.println("No arbitrage found");
        }
    }

    public void email (){
        // todo to implement
    }

    public void trade() {
        // todo to implement
    }
}
