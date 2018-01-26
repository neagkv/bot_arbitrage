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


    public void insertTrades(TradingData tradingData){
        tradeHistoryMapper.insertTrades(tradingData.getBaseName(), tradingData.getCounterName(), tradingData.getBuyExchange(), tradingData.getSellExchange(),
                tradingData.getOldTotalBase(), tradingData.getNewTotalBase(), tradingData.getDifferenceBaseBuy(), tradingData.getDifferenceCounterBuy(),
                tradingData.getDifferenceBaseSell(), tradingData.getDifferenceCounterSell(), tradingData.getRealAsk(), tradingData.getRealBid(),
                tradingData.getRealDifferenceFormated(),tradingData.getExpectedDifference(), tradingData.getEstimatedFee());
    }

}
