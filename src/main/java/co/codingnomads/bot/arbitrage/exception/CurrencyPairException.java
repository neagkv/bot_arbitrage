package co.codingnomads.bot.arbitrage.exception;

/**
 * @author Kevin Neag
 */
public class CurrencyPairException extends Exception{


    String errorMsg;

    /**
     * An exception if the currency pair the user selected is not supported by the exchange/s given
     * @param message
     */
    public CurrencyPairException(String message) {
        super(message);
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "CurrencyPairException{" +
                "errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
