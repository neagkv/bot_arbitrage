package co.codingnomads.bot.arbitrage.action.arbitrage;

import co.codingnomads.bot.arbitrage.action.arbitrage.selection.ArbitrageActionSelection;
import co.codingnomads.bot.arbitrage.exchange.ExchangeSpecs;
import co.codingnomads.bot.arbitrage.model.ticker.TickerData;
import co.codingnomads.bot.arbitrage.service.general.MarginDiffCompare;
import org.knowm.xchange.ExchangeSpecification;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
/**
 * Created by Thomas Leruth on 12/17/17
 * class for the information to use the print method as acting behavior
 */
@Service
public class ArbitragePrintAction extends ArbitrageActionSelection {

    /**
     * Fully qualified constructor
     * @param arbitrageMargin
     */
    public ArbitragePrintAction(double arbitrageMargin) {
        super(arbitrageMargin);
    }

    /**
     * empty constructor
     */
    public ArbitragePrintAction(){

    }

    ExchangeSpecs exchangeSpecs = new ExchangeSpecs() {
        @Override
        public ExchangeSpecification GetSetupedExchange() {
            return null;
        }
    };

    /**
     * Method to print the arbitrage action to the console
     *
     * @param lowAsk          the lowest ask found (buy)
     * @param highBid         the highest bid found (sell)
     * @param arbitrageMargin the margin difference accepted (not a valid arbitrage if below that value)
     */
    public void print(TickerData lowAsk, TickerData highBid, double arbitrageMargin) {

        MarginDiffCompare marginDiffCompare = new MarginDiffCompare();

        //percentage of returns you will make
        BigDecimal difference = marginDiffCompare.findDiff(lowAsk,highBid);

        //the difference between the arbitrage margin and percentage of returns
        BigDecimal marginSubDiff = marginDiffCompare.diffWithMargin(lowAsk,highBid,arbitrageMargin);

        //if the arbitrage margin - the percent difference between the highestBid and lowAsk is greater than zero arbitrage detected
        if (marginSubDiff.compareTo(BigDecimal.ZERO) > 0) {

            System.out.println("=======================================================================================");
            System.out.println("=======================================================================================");
            System.out.println();
            System.out.println("ARBITRAGE DETECTED!!!"
                    + " buy on " + lowAsk.getExchange().getDefaultExchangeSpecification().getExchangeName()
                    + " for " + lowAsk.getAsk()
                    + " and sell on " + highBid.getExchange().getDefaultExchangeSpecification().getExchangeName()
                    + " for " + highBid.getBid()
                    + " and make a return (before fees) of "
                    + difference
                    + "%");
            System.out.println();
            System.out.println("=======================================================================================");
            System.out.println("=======================================================================================");
            System.out.println();

        } else {
            System.out.println("=======================================================================================");
            System.out.println("=======================================================================================");
            System.out.println();
            System.out.println("No arbitrage found");
            System.out.println();
            System.out.println("=======================================================================================");
            System.out.println("=======================================================================================");
            System.out.println();

        }
    }

}
