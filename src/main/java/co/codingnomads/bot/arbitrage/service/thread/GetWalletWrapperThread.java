package co.codingnomads.bot.arbitrage.service.thread;

import co.codingnomads.bot.arbitrage.model.ticker.TickerData;
import co.codingnomads.bot.arbitrage.model.trading.WalletWrapper;
import org.knowm.xchange.dto.account.Wallet;
import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * Created by Thomas Leruth on 12/17/17
 *
 * Callable class to get the wallet wrapper
 */

public class GetWalletWrapperThread implements Callable<WalletWrapper> {

    TickerData tickerData;


    /**
     * call method that gets the exchange name and your wallet and puts it in a walletWrapper object
     * @return walletWrapper with you wallet and exchange name
     */
    @Override
    public WalletWrapper call() {
        try {
            String exchangeName = tickerData.getExchange().getDefaultExchangeSpecification().getExchangeName();
            Wallet wallet = tickerData.getExchange().getAccountService().getAccountInfo().getWallet();
            return new WalletWrapper(wallet, exchangeName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Constructor
     * @param tickerData
     */
    public GetWalletWrapperThread(TickerData tickerData) {
        this.tickerData = tickerData;
    }
}
