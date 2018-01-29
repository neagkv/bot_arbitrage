package co.codingnomads.bot.arbitrage.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author Kevin Neag
 */

/**
 * Mapper class to for detection information to be entered into the database
 */

@Mapper
public interface DetectionWrapperMapper {

    /**
     * Method to enter Difference Wrapper to put into database
     */
    String INSERT_DifferenceWrapper = "INSERT INTO `bot.arbitrage`.`Detection` (`timestamp`, `currencyPair`, `Difference`, `lowAsk`, `lowaskExchange`, `highBid`, `highBidExchange`) VALUES " +
            "(#{arg0}, #{arg1}, #{arg2}, #{arg3}, #{arg4}, #{arg5}, #{arg6})";

    /**
     * Variables from the difference wrapper class to be entered into the database by the mapper
     * @param timestamp
     * @param currencyPairReformated
     * @param difference
     * @param lowAsk
     * @param lowAskExchange
     * @param highBid
     * @param highBidExchange
     */
    @Insert(INSERT_DifferenceWrapper)
    public void insert_DifferenceWrapper(Timestamp timestamp, String currencyPairReformated, BigDecimal difference, BigDecimal lowAsk,
    String lowAskExchange, BigDecimal highBid, String highBidExchange);

}

