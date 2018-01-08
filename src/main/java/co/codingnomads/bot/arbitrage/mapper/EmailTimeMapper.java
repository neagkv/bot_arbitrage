package co.codingnomads.bot.arbitrage.mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/**
 * @author Kevin Neag
 **/

@Mapper
public interface EmailTimeMapper {

    /**
     * Enters TimeEmailSent, the TO address and the SUBJECT into the bot.arbitrage schema in the email_info table
     */
    String INSERT_EmailRecord = "INSERT INTO `bot.arbitrage`.`email_info` (`TimeOfCall`, `EmailAddress`, `Type`) VALUES " + "(#{arg0}, #{arg1}, #{arg2})";

    /**
     * Selects the numbers of emails sent fot today's date fromt the bot.arbitrage schema in the email_info table
     */
    String Get_Total_Calls_Today = "SELECT count(id) FROM `bot.arbitrage`.email_info where TimeOfCall > 'CURDATE()'";

    /**
     * Inserts the results of INSERT_EmailRecord above
     * @param timeEmailSent     The time that email was sent
     * @param TO    The email address that is going to receive email updates
     * @param SUBJECT   The subject of the email
     */
    @Insert(INSERT_EmailRecord)
    public void insertEmailRecord(String timeEmailSent, String TO, String SUBJECT);

    /**
     * Selects the results from Get_Total_Calls_Today method
     * @param day   today's date in yyyy-MM-dd format
     * @return number of emails sent today
     */
    @Select(Get_Total_Calls_Today)
    public int getTotalCallsToday(String day);

}

