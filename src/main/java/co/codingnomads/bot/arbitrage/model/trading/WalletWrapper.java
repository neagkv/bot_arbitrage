package co.codingnomads.bot.arbitrage.model.trading;

import org.knowm.xchange.dto.account.Wallet;

/**
 * Created by Thomas Leruth on 12/17/17
 *
 * POJO class to wrap the wallet with an item to recognize the wallet (this class is needed because of the
 * drawback of using Callable<> being that they come back in unpredictable order
 */


public class WalletWrapper {

    private Wallet wallet;
    private String exchangeName;

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public WalletWrapper(Wallet wallet, String exchangeName) {
        this.wallet = wallet;
        this.exchangeName = exchangeName;
    }
}
