package com.vincent.whale.build;

import com.vincent.whale.entity.User;
import com.vincent.whale.entity.UserTravelling;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Vincent on 2018/1/25.
 */
public class UserTravellingFactory {

    private static ArrayList<User> users = new ArrayList<>();

    static {
        users = ParseUsers.parseUser();
    }

    public static UserTravelling buildUserTravelling() {
        UserTravelling ut = new UserTravelling();
        int random = new Random().nextInt(users.size());
        ut.setUser(users.get(random));
        ut.setTravelling(TravellingFactory.buildTravelling());
        return ut;
    }
}
