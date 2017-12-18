package co.codingnomads.bot.arbitrage.service;

import co.codingnomads.bot.arbitrage.model.BidAsk;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.WalletWrapper;
import org.knowm.xchange.dto.account.Wallet;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * Created by Thomas Leruth on 12/17/17
 */

public class GetWalletWrapperThread implements Callable<WalletWrapper> {

    BidAsk bidAsk;

    @Override
    public WalletWrapper call() {
        try {
            String exchangeName = bidAsk.getExchange().getDefaultExchangeSpecification().getExchangeName();
            Wallet wallet = bidAsk.getExchange().getAccountService().getAccountInfo().getWallet();
            return new WalletWrapper(wallet, exchangeName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public GetWalletWrapperThread(BidAsk bidAsk) {
        this.bidAsk = bidAsk;
    }
}
