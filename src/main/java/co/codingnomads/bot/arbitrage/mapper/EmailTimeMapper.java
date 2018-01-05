package co.codingnomads.bot.arbitrage.mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/**
 * @author Kevin Neag
 **/

@Mapper
public interface EmailTimeMapper {

    String INSERT_EmailRecord = "INSERT INTO `bot.arbitrage`.`emailinfo` (`TimeOfCall`, `EmailAddress`, `Type`) VALUES " + "(#{arg0}, #{arg1}, #{arg2})";

    String GET_Last_CallTime = "SELECT `TimeOfCall` FROM `bot.arbitrage`.`emailinfo` ORDER BY id DESC Limit 1";

    @Insert(INSERT_EmailRecord)
    public void insertEmailRecord(String timeEmailSent, String TO, String SUBJECT);

    @Select(GET_Last_CallTime)
    public String getLastCallTime();

}

