package co.codingnomads.bot.arbitrage.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author Kevin Neag
 */
@Mapper
public interface TradeHistoryMapper {

    String INSERT_TradeHistory = "INSERT INTO `bot.arbitrage`.`trade_history` (`TimeOfCall`, `EmailAddress`, `Type`) VALUES " + "(#{arg0}, #{arg1}, #{arg2})";


    @Insert(INSERT_TradeHistory)
    public void insertEmailRecord(String timeEmailSent, String TO, String SUBJECT);


}
