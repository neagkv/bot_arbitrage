package co.codingnomads.bot.arbitrage.exception;

/**
 * @author Kevin Neag
 */


public class EmailLimitException extends Exception{

    String errorMsg;

    /**
     * An exception so the user can not exceed the daily email limit set by Amazon SES
     * @param message
     */
    public EmailLimitException(String message) {
        super(message);
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "EmailLimitException{" +
                "errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
