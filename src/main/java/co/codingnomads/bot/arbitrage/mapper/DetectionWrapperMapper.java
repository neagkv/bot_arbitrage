package co.codingnomads.bot.arbitrage.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.knowm.xchange.currency.CurrencyPair;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author Kevin Neag
 */

@Mapper
public interface DetectionWrapperMapper {

    String INSERT_DifferenceWrapper = "INSERT INTO `bot.arbitrage`.`Detection` (`timestamp`, `currencyPair`, `Difference`, `lowAsk`, `lowaskExchange`, `highBid`, `highBidExchange`) VALUES " +
            "(#{arg0}, #{arg1}, #{arg2}, #{arg3}, #{arg4}, #{arg5}, #{arg6})";

    @Insert(INSERT_DifferenceWrapper)
    public void insert_DifferenceWrapper(Timestamp timestamp, CurrencyPair currencyPair, BigDecimal difference, BigDecimal lowAsk,
    String lowAskExchange, BigDecimal highBid, String highBidExchange);

}

