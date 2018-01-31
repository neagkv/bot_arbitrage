package co.codingnomads.bot.arbitrage.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import java.math.BigDecimal;

/**
 * @author Kevin Neag
 */

/**
 * TradeHistoryMapper to insert trade information into database
 */
@Mapper
public interface TradeHistoryMapper {


    /**
     * Method to insert trade information into the database
     */
    String INSERT_Trades = "INSERT INTO `botarbitrage`. `trades` (`baseName`, `counterName`, `buyExchange`, `sellExchange`, " +
            "`oldTotalBase`, `newTotalBase`, `differenceBaseBuy`, `differenceCounterBuy`, `differenceBaseSell`, `differenceCounterSell`, " +
            "`realAsk`, `realBid`, `realDifference`, `expectedDifferenceFormatted`, `estimatedFee`) VALUES " +
            "(#{arg0}, #{arg1}, #{arg2}, #{arg3}, #{arg4}, #{arg5}, #{arg6}, #{arg7}, #{arg8}, #{arg9}, #{arg10}, #{arg11}, #{arg12}, #{arg13}, #{arg14})";

    /**
     * The variables from the trading data object to be put into the database
     * @param baseName
     * @param counterName
     * @param buyExchange
     * @param sellExchange
     * @param oldTotalBase
     * @param newTotalBase
     * @param differenceBaseBuy
     * @param differenceCounterBuy
     * @param differenceBaseSell
     * @param differenceCounterSell
     * @param realAsk
     * @param realBid
     * @param realDifference
     * @param expectedDifferenceFormatted
     * @param estimatedFee
     */
    @Insert(INSERT_Trades)
    public void insertTrades(String baseName, String counterName, String buyExchange, String sellExchange, BigDecimal oldTotalBase,
                             BigDecimal newTotalBase, BigDecimal differenceBaseBuy, BigDecimal differenceCounterBuy, BigDecimal differenceBaseSell,
                             BigDecimal differenceCounterSell, BigDecimal realAsk, BigDecimal realBid, BigDecimal realDifference, BigDecimal expectedDifferenceFormatted,
                             BigDecimal estimatedFee);


}
