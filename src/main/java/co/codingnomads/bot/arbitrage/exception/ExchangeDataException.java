package co.codingnomads.bot.arbitrage.exception;

/**
 * @author Kevin Neag
 */
public class ExchangeDataException extends Exception{


    String errorMsg;

    /**
     * An exception if the currency pair the user selected is not supported by the exchange/s given, or if you do not have the funds
     * needed to complete the trade
     * @param message
     */
    public ExchangeDataException(String message) {
        super(message);
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "ExchangeDataException{" +
                "errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
