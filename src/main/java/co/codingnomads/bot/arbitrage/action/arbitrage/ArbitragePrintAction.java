package co.codingnomads.bot.arbitrage.action.arbitrage;

import co.codingnomads.bot.arbitrage.action.arbitrage.selection.ArbitrageActionSelection;
import co.codingnomads.bot.arbitrage.model.ticker.TickerData;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Thomas Leruth on 12/17/17
 * class for the information to use the print method as acting behavior
 */
@Service
public class ArbitragePrintAction extends ArbitrageActionSelection {


    public ArbitragePrintAction(double arbitrageMargin) {
        super(arbitrageMargin);
    }

    public ArbitragePrintAction(){

    }

    /**
     * Method to print the arbitrage action to the console
     *
     * @param lowAsk          the lowest ask found (buy)
     * @param highBid         the highest bid found (sell)
     * @param arbitrageMargin the margin difference accepted (not a valid arbitrage if below that value)
     */
    public void print(TickerData lowAsk, TickerData highBid, double arbitrageMargin) {

        //
        BigDecimal difference = highBid.getBid().divide(lowAsk.getAsk(), 5, RoundingMode.HALF_EVEN);

        if (difference.compareTo(BigDecimal.valueOf(arbitrageMargin)) > 0) {
            BigDecimal differenceFormated = (difference.add(BigDecimal.valueOf(-1))).multiply(BigDecimal.valueOf(100));
            System.out.println("=======================================================================================");
            System.out.println("=======================================================================================");
            System.out.println();
            System.out.println("ARBITRAGE DETECTED!!!"
                    + " buy on " + lowAsk.getExchange().getDefaultExchangeSpecification().getExchangeName()
                    + " for " + lowAsk.getAsk()
                    + " and sell on " + highBid.getExchange().getDefaultExchangeSpecification().getExchangeName()
                    + " for " + highBid.getBid()
                    + " and make a return (before fees) of "
                    + differenceFormated
                    + "%");
            System.out.println();
            System.out.println("=======================================================================================");
            System.out.println("=======================================================================================");

        } else {
            System.out.println("=======================================================================================");
            System.out.println("=======================================================================================");
            System.out.println();
            System.out.println("No arbitrage found");
            System.out.println();
            System.out.println("=======================================================================================");
            System.out.println("=======================================================================================");

        }
    }

}
