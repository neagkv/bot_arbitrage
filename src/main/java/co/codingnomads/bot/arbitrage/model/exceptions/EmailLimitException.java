package co.codingnomads.bot.arbitrage.model.exceptions;

/**
 * @author Kevin Neag
 */
public class EmailLimitException extends Exception{

    String errorMsg;

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
