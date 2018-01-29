package co.codingnomads.bot.arbitrage.service.tradehistory;

import co.codingnomads.bot.arbitrage.mapper.TradeHistoryMapper;
import co.codingnomads.bot.arbitrage.model.trading.TradingData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Kevin Neag
 */


/**
 * Trade History Service class for inserting Trading data after successful trade into database
 */
@Service
public class TradeHistoryService {


    @Autowired
    TradeHistoryMapper tradeHistoryMapper;


    /**
     *Insert Trade method, takes trading data object after successful arbitrage trade action and calls the tradeHistoryMapper to insert
     * into database
     * @param tradingData
     */

    public void insertTrades(TradingData tradingData){
        tradeHistoryMapper.insertTrades(tradingData.getBaseName(), tradingData.getCounterName(), tradingData.getBuyExchange(), tradingData.getSellExchange(),
                tradingData.getOldTotalBase(), tradingData.getNewTotalBase(), tradingData.getDifferenceBaseBuy(), tradingData.getDifferenceCounterBuy(),
                tradingData.getDifferenceBaseSell(), tradingData.getDifferenceCounterSell(), tradingData.getRealAsk(), tradingData.getRealBid(),
                tradingData.getRealDifferenceFormated(),tradingData.getExpectedDifference(), tradingData.getEstimatedFee());
    }

}
