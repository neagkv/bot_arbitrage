package co.codingnomads.bot.arbitrage.mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;


/**
 * @author Kevin Neag
 **/

@Mapper
public interface EmailTimeMapper {

    String INSERT_EmailRecord = "INSERT INTO `bot.arbitrage`.`email_info` (`TimeOfCall`, `EmailAddress`, `Type`) VALUES " + "(#{arg0}, #{arg1}, #{arg2})";

    String GET_Last_CallTime = "SELECT `TimeOfCall` FROM `bot.arbitrage`.`email_info` ORDER BY id DESC Limit 1";

    String Get_Total_Calls_Today = "SELECT count(id) FROM `bot.arbitrage`.email_info where TimeOfCall > '2018-01-05'";

    @Insert(INSERT_EmailRecord)
    public void insertEmailRecord(String timeEmailSent, String TO, String SUBJECT);

    @Select(Get_Total_Calls_Today)
    public int getTotalCallsToday(String day);

}

