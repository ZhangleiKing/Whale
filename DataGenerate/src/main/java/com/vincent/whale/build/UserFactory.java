package com.vincent.whale.build;

import com.google.gson.Gson;
import com.vincent.whale.entity.User;
import com.vincent.whale.util.MyFileUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Vincent on 2018/1/24.
 */
public class UserFactory {
    private static ArrayList<Integer> ageRangeList;
    private static HashMap<String, ArrayList<String>> provinceCities;

    private static ArrayList<String> userIds = new ArrayList<>();

    static {
        ageRangeList = AgeWeightRandomFactory.getIntegerWeightsDistributionList();
        provinceCities = AddressFactory.getProvinceCities();
    }

    public static User buildUser() {
        User user = new User();
        user.setUserAge(AgeWeightRandomFactory.getWeightedRandom(ageRangeList));
        user.setHomeAddress(AddressFactory.getRandomAddress(provinceCities));
        user.setIdCardNumber(IdNumberFactory.generate());
        user.setUserName(ChineseNameFactory.getChineseName());
        byte sex = (byte)((new Random().nextBoolean()) ? 1 : 0);
        user.setUserSex(sex);
        String id = String.valueOf(10000+userIds.size());
        userIds.add(id);
        user.setUserId(id);
        return user;
    }

    public static void generateUsers(int count) {
        String usersFilePath = "E:/Projects/Git/Whale/DataGenerate/src/main/resources/users.txt";
        try {
            long begin = System.currentTimeMillis();
            System.out.println("*****begin to write users*****");
            System.out.print("producing");
            for(int i=0;i<count;i++) {
                String str = Gson.class.newInstance().toJson(buildUser())+"\n";
                MyFileUtil.writeFile(usersFilePath, str, false);
                if((i+1)%5000==0)
                    System.out.print(".");
            }
            long end = System.currentTimeMillis();
            System.out.println("");
            System.out.println("*****write users end*****");
            System.out.println("total " + count + " records, spend " + (end-begin) + "ms");
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        generateUsers(200000);
    }
}
