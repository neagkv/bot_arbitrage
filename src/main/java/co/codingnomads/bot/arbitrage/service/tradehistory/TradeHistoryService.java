package co.codingnomads.bot.arbitrage.service.tradehistory;

import co.codingnomads.bot.arbitrage.mapper.TradeHistoryMapper;
import co.codingnomads.bot.arbitrage.model.trading.TradingData;
import org.knowm.xchange.currency.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

/**
 * @author Kevin Neag
 */
@Service
public class TradeHistoryService {


    @Autowired
    TradeHistoryMapper tradeHistoryMapper;

    public void insertTradeHistoryRecords(TradingData tradingData){
        tradeHistoryMapper.insertTradeHistory(tradingData.getBase(),tradingData.getCounter(),tradingData.getOldBaseBuy(),tradingData.getOldCounterSell(),
                tradingData.getNewBaseBuy(),tradingData.getNewCounterBuy(),tradingData.getNewBaseSell(),tradingData.getNewCounterSell(),
                tradingData.getOldTotalBase(),tradingData.getNewTotalBase(),tradingData.getDifferenceBaseBuy(),tradingData.getDifferenceCounterSell(),
                tradingData.getRealAsk(), tradingData.getRealBid(), tradingData.getDifferenceBidAsk(),tradingData.getRealDifferenceFormated(),tradingData.getDifferenceTotalBase());

    }

    public void insertBase(TradingData tradingData){
        tradeHistoryMapper.insertBase(tradingData.getOldBaseBuy());
    }
}
