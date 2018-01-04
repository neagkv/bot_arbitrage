package co.codingnomads.bot.arbitrage.mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/**
 * @author Kevin Neag
 **/

@Mapper
public interface EmailTimeMapper {

    String INSERT_EmailTime = "INSERT INTO `bot.arbitrage`.`emailinfo` (`TimeOfCall`) VALUES " + "(#{currentTime})";

    String GET_Last_CallTime = "SELECT * FROM `bot.arbitrage`.`emailinfo` where TimeOfCall = #{currentTime}";

    @Insert(INSERT_EmailTime)
    public void insertEmailTime(String currentTime);

    @Select(GET_Last_CallTime)
    public String getLastCallTime(String currentTime);



}

