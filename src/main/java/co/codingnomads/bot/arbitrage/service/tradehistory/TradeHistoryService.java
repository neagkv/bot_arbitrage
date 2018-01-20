package co.codingnomads.bot.arbitrage.service.tradehistory;

import co.codingnomads.bot.arbitrage.mapper.TradeHistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Kevin Neag
 */
@Service
public class TradeHistoryService {

    @Autowired
    TradeHistoryMapper tradeHistoryMapper;

    public void insertTradeHistoryRecords(){

    }
}
