package co.codingnomads.bot.arbitrage.service;

import co.codingnomads.bot.arbitrage.model.BidAsk;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by Thomas Leruth on 12/12/17
 */
@Service
public class DataUtil {

    public BidAsk lowAskFinder(ArrayList<BidAsk> list) {
        int lowIndex = 0;
        if (list.size() > 1) {
            for (int i = 1; i < list.size(); i++) {
                if (list.get(lowIndex).getAsk().compareTo(list.get(i).getAsk()) > 0) {
                    lowIndex = i;
                }
            }
        }
        return list.get(lowIndex);
    }

    public BidAsk highBidFinder(ArrayList<BidAsk> list) {
        int highIndex = 0;
        if (list.size() > 1) {
            for (int i = 1; i < list.size(); i++) {
                if (list.get(highIndex).getBid().compareTo(list.get(i).getBid()) < 0) {
                    highIndex = i;
                }
            }
        }
        return list.get(highIndex);
    }
}
