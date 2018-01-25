package co.codingnomads.bot.arbitrage.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.knowm.xchange.currency.Currency;
import java.math.BigDecimal;

/**
 * @author Kevin Neag
 */
@Mapper
public interface TradeHistoryMapper {


    String INSERT_TradeHistory = "INSERT INTO `bot.arbitrage`.`trade_history` (`base`, `counter`, `oldBaseBuy`, `oldCounterSell`," +
            "`newBaseBuy`,`newCounterBuy`, `newBaseSell`, `newCounterSell`, `oldTotalBase`, `newTotalBase`" +
            "`differenceBaseBuy`, `differenceCounterSell`, `realAsk`, `realBid`, `differenceBidAsk`, `realDifferenceFormated`, `differenceTotalBase`) VALUES " +
            "(#{arg0}, #{arg1}, #{arg2}, #{arg3}, #{arg4}, #{arg5}, #{arg6}, #{arg7}, #{arg8}, #{arg9}, #{arg10}, #{arg11}, #{arg12}, #{arg13}, #{arg14}, #{arg15}, #{arg16})";

    String INSERT_Base = "INSERT INTO `bot.arbitrage`.`new_table` (`oldBaseBuy`) VALUES " + "(#{arg0})";



    @Insert(INSERT_TradeHistory)
    public void insertTradeHistory(Currency base, Currency counter, BigDecimal oldBaseBuy, BigDecimal oldCounterSell,
                                  BigDecimal newBaseBuy, BigDecimal newCounterBuy, BigDecimal newBaseSell,
                                  BigDecimal newCounterSell, BigDecimal oldTotalBase, BigDecimal newTotalBase,
                                  BigDecimal differenceBaseBuy, BigDecimal differenceCounterSell, BigDecimal realAsk,
                                  BigDecimal realBid, BigDecimal differenceBidAsk, BigDecimal realDifferenceFormated,
                                  BigDecimal differenceTotalBase);

    @Insert(INSERT_Base)
    public void insertBase(BigDecimal oldBaseBuy);


//    Currency base, Currency counter, BigDecimal oldBaseBuy, BigDecimal oldCounterSell,
//    BigDecimal newBaseBuy, BigDecimal newCounterBuy, BigDecimal newBaseSell,
//    BigDecimal newCounterSell, BigDecimal oldTotalBase, BigDecimal newTotalBase,
//    BigDecimal differenceBaseBuy, BigDecimal differenceCounterSell, BigDecimal realAsk,
//    BigDecimal realBid, BigDecimal differenceBidAsk, BigDecimal realDifferenceFormated,
//    BigDecimal differenceTotalBase



}
