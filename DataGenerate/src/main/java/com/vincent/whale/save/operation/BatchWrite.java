package com.vincent.whale.save.operation;

import com.vincent.whale.build.UserTravellingFactory;
import com.vincent.whale.entity.Travelling;
import com.vincent.whale.entity.User;
import com.vincent.whale.entity.UserTravelling;
import com.vincent.whale.save.hbase.HBaseManager;

import java.util.HashMap;

/**
 * Created by Vincent on 2018/1/25.
 */
public class BatchWrite {

    private static HBaseManager manager = null;

    static {
        manager = new HBaseManager();
    }

    public static void batchWrite(int count) {
        System.out.println("*****begin to batchWrite*****");
        System.out.print("insert");
        for(int i=0; i<count; i++) {
            UserTravelling userTravelling = UserTravellingFactory.buildUserTravelling();
            User user = userTravelling.getUser();
            Travelling travelling = userTravelling.getTravelling();
            String rowKey = travelling.getCreateTime()+"-"+user.getUserId();
            HashMap<String, HashMap<String, String>> data = new HashMap<>();

            HashMap<String, String> userInfo = new HashMap<>();
            userInfo.put("userId", user.getUserId());
            userInfo.put("userName", user.getUserName());
            userInfo.put("idCardNumber", user.getIdCardNumber());
            userInfo.put("userAge", String.valueOf(user.getUserAge()));
            userInfo.put("userSex", String.valueOf(user.getUserSex()));
            userInfo.put("homeAddress", user.getHomeAddress());
            data.put("userInfo", userInfo);

            HashMap<String, String> travelInfo = new HashMap<>();
            travelInfo.put("departure", travelling.getDeparture());
            travelInfo.put("destination", travelling.getDestination());
            travelInfo.put("consumption", String.valueOf(travelling.getConsumption()));
            travelInfo.put("createTime", String.valueOf(travelling.getCreateTime()));
            data.put("travelInfo", travelInfo);

            manager.insertData("whale", rowKey, data);
            if((i+1)%5000==0)
                System.out.print(".");
        }
        System.out.println("");
        System.out.println("*****batchWrite end*****");
    }
}
