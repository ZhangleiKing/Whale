package com.vincent.whale.build;

import com.alibaba.fastjson.JSON;
import com.vincent.whale.entity.User;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Vincent on 2018/1/25.
 */
public class ParseUsers {

    public static ArrayList<User> parseUser() {
        ArrayList<User> ret = new ArrayList<>();
        String usersFilePath = "E:/Projects/Git/Whale/DataGenerate/src/main/resources/users.txt";
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(usersFilePath), "UTF-8");
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String tmp = "";
            while((tmp = reader.readLine()) != null) {
                User user = JSON.parseObject(tmp, User.class);
                ret.add(user);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static void main(String[] args) {
        ArrayList<User> users = parseUser();
        for(User user : users) {
            System.out.println(user.getUserName() + user.getUserId());
        }
    }
}
