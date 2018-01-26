package co.codingnomads.bot.arbitrage.mapper;

import co.codingnomads.bot.arbitrage.service.general.BalanceCalc;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import java.math.BigDecimal;

/**
 * @author Kevin Neag
 */
@Mapper
public interface TradeHistoryMapper {


    String INSERT_EmailRecord = "INSERT INTO `bot.arbitrage`.`email_info` (`TimeOfCall`, `EmailAddress`, `Type`) VALUES " + "(#{arg0}, #{arg1}, #{arg2})";


    String INSERT_DifferenceWrapper = "INSERT INTO `bot.arbitrage`.`Detection` (`timestamp`, `currencyPair`, `Difference`, `lowAsk`, `lowaskExchange`, `highBid`, `highBidExchange`) VALUES " +
            "(#{arg0}, #{arg1}, #{arg2}, #{arg3}, #{arg4}, #{arg5}, #{arg6})";



//    String INSERT_TradeHistory = "INSERT INTO `bot.arbitrage`.`trade_history` (`baseName`,`counterName`,`buyExchange`, `sellExchange`," +
//            "`oldBaseBuy`, `oldCounterBuy`, `oldBaseSell`, `oldCounterSell`, `newBaseSell`, `newCounterSell`, `oldTotalBase`, `newTotalBase`," +
//            "`differenceBaseBuy`, `differenceCounterBuy`, `differenceBaseSell`, `differenceCounterSell`, `realAsk`, `realBid`," +
//            "``realDifference`, `expectedDifferenceFormatted`, `estimatedFee`) VALUES " +
//            "(#{arg0}, #{arg1}, #{arg2}, #{arg3}, #{arg4}, #{arg5}, #{arg6}, #{arg7}, #{arg8}, #{arg9}, #{arg10}, #{arg11}, #{arg12}, #{arg13}, #{arg14}, #{arg15}, #{arg16}, #{arg17}, #{arg18}, " +
//            "(#{arg19}, #{arg20})";

    String INSERT_Trades = "INSERT INTO `bot.arbitrage`. `trades` (`baseName`, `counterName`, `buyExchange`, `sellExchange`, " +
            "`oldTotalBase`, `newTotalBase`, `differenceBaseBuy`, `differenceCounterBuy`, `differenceBaseSell`, `differenceCounterSell`, " +
            "`realAsk`, `realBid`, `realDifference`, `expectedDifferenceFormatted`, `estimatedFee`) VALUES " +
            "(#{arg0}, #{arg1}, #{arg2}, #{arg3}, #{arg4}, #{arg5}, #{arg6}, #{arg7}, #{arg8}, #{arg9}, #{arg10}, #{arg11}, #{arg12}, #{arg13}, #{arg14})";


    @Insert(INSERT_Trades)
    public void insertTrades(String baseName, String counterName, String buyExchange, String sellExchange, BigDecimal oldTotalBase,
                             BigDecimal newTotalBase, BigDecimal differenceBaseBuy, BigDecimal differenceCounterBuy, BigDecimal differenceBaseSell,
                             BigDecimal differenceCounterSell, BigDecimal realAsk, BigDecimal realBid, BigDecimal realDifference, BigDecimal expectedDifferenceFormatted,
                             BigDecimal estimatedFee);


//    String INSERT_Info = "INSERT INTO `bot.arbitrage`. `new_table` (`baseName`, `counterName`, `buyExchange`, `sellExchange`) VALUES " +
//            "(#{arg0}, #{arg1}, #{arg2}, #{arg3})";

//    @Insert(INSERT_TradeHistory)
//    public void insertTadeHistory(String baseName, String counterName, String buyExchange, String sellExchange,
//                                  BigDecimal oldBaseBuy, BigDecimal oldCounterBuy, BigDecimal oldBaseSell,
//                                  BigDecimal oldCounterSell, BigDecimal newBaseSell, BigDecimal newCounterSell,
//                                  BigDecimal oldTotalBase, BigDecimal newTotalBase, BigDecimal differenceBaseBuy,
//                                  BigDecimal differenceCounterBuy, BigDecimal differenceBaseSell, BigDecimal differenceCounterSell,
//                                  BigDecimal realAsk, BigDecimal realBid, BigDecimal realDifference,
//                                  BigDecimal expectedDifferenceFormatted, BigDecimal estimatedFee);
//
//
//    @Insert(INSERT_Info)
//    public void insertInfo(String baseName, String counterName, String buyExchange, String sellExchange);


}
