package co.codingnomads.bot.arbitrage.mapper;

import co.codingnomads.bot.arbitrage.model.arbitrageAction.email.TimeOfCall;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Kevin Neag
 */

@Mapper
public interface EmailTimeMapper {

    String INSERT_TimeOfCALL = "INSERT INTO `bot.arbitrage`.`new_table` (`id`, `timeofcall`) VALUES " + "(#{id}, #{timeofcall})";


    @Insert(INSERT_TimeOfCALL)
    int insertValue(TimeOfCall timeOfCall);




}

