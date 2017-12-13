package co.codingnomads.bot.arbitrage.service;

import co.codingnomads.bot.arbitrage.model.BidAsk;

import java.util.ArrayList;

/**
 * Created by Thomas Leruth on 12/12/17
 */

public class DataUtil {

    public BidAsk lowBidFinder(ArrayList<BidAsk> list) {
        int lowIndex = 0;
        if (list.size() > 1) {
            for (int i = 1; i < list.size(); i++) {
                if (list.get(lowIndex).getBid().compareTo(list.get(i).getBid()) > 0) {
                    lowIndex = i;
                }
            }
        }
        return list.get(lowIndex);
    }

    public BidAsk highAskFinder(ArrayList<BidAsk> list) {
        int highIndex = 0;
        if (list.size() > 1) {
            for (int i = 1; i < list.size(); i++) {
                if (list.get(highIndex).getAsk().compareTo(list.get(i).getAsk()) < 0) {
                    highIndex = i;
                }
            }
        }
        return list.get(highIndex);
    }
}
