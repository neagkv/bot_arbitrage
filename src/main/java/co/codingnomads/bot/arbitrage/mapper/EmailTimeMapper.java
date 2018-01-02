package co.codingnomads.bot.arbitrage.mapper;
import co.codingnomads.bot.arbitrage.model.arbitrageAction.email.EmailSendTime;
import com.amazonaws.services.dynamodbv2.xspec.S;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.MappedJdbcTypes;

/**
 * @author Kevin Neag
 **/

@Mapper
public interface EmailTimeMapper {

    String INSERT_TimeOfCALL = "INSERT INTO `bot.arbitrage`.`new_table` (`timeofcall`) VALUES " + "(#{timeOfCallInt})";
    String SelectAll ="SELECT * FROM `bot.arbitrage`.`new_table`";

    @Select(SelectAll)
    public EmailSendTime getALL();

    @Insert(INSERT_TimeOfCALL)
    public int insertValue(int timeOfCall);




}

