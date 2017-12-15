package co.codingnomads.bot.arbitrage.service;

import co.codingnomads.bot.arbitrage.model.BidAsk;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by Thomas Leruth on 12/14/17
 */
@Service
public class ArbitrageAction {

    public void print(BidAsk lowAsk, BidAsk highBid, BigDecimal difference, double arbitrageMargin){
         if (difference.compareTo(BigDecimal.valueOf(arbitrageMargin)) > 0) {
            System.out.println("ARBITRAGE DETECTED!!!"
                    + " buy on " + lowAsk.getExchangeName() + " for " + lowAsk.getAsk()
                    + " and sell on " + highBid.getExchangeName() + " for " + highBid.getBid()
                    + " and make a return (before fees) of " + difference.add(BigDecimal.valueOf(-1)));
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
