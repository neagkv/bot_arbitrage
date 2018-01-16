package co.codingnomads.bot.arbitrage.exception;

/**
 * @author Kevin Neag
 */
public class WaitTimeException extends Exception{

    String errorMsg;

    /**
     * An Exception so that the wait time is not small enough to overload the database
     * @param message
     */
    public WaitTimeException(String message) {
        super(message);
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "WaitTimeException{" +
                "errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
