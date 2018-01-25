package com.vincent.whale.build;

import com.vincent.whale.entity.Travelling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Vincent on 2018/1/25.
 */
public class TravellingFactory {

    private static HashMap<String, ArrayList<String>> provinceCities;

    static {
        provinceCities = AddressFactory.getProvinceCities();
    }

    public static Travelling buildTravelling() {
        Travelling travelling = new Travelling();
        String departure = AddressFactory.getRandomAddress(provinceCities);
        travelling.setDeparture(departure);
        String destination = AddressFactory.getRandomAddress(provinceCities);
        //目的地和出发地应该不一致
        while(destination.equals(departure)) {
            destination = AddressFactory.getRandomAddress(provinceCities);
        }
        travelling.setDestination(destination);
        travelling.setConsumption(new Random().nextDouble()*45000+1000);
        travelling.setCreateTime(DateRandomFactory.getRandomTimestamp(2));
        return travelling;
    }
}
