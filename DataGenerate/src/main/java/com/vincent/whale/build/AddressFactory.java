package com.vincent.whale.build;

import com.vincent.whale.Main;
import com.vincent.whale.util.MyFileUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Vincent on 2018/1/23.
 */
public class AddressFactory {

    private static HashMap<String, ArrayList<String>> provinceCities;

    static {
        if(provinceCities == null)
            provinceCities = getProvinceCities(MyFileUtil.readFileByStream(Main.class.getResourceAsStream("/provinceCity.json")));
    }

    public static HashMap<String, ArrayList<String>> getProvinceCities(String content) {
        JSONObject jsonObject = JSONObject.fromObject(content);
        JSONArray provinces = jsonObject.getJSONArray("provinces");

        HashMap<String, ArrayList<String>> ret = new HashMap<String, ArrayList<String>>();
        for(Object province : provinces) {
            JSONObject pro = (JSONObject)province;
            String provinceName = pro.getString("provinceName");
            ArrayList<String> list = new ArrayList<String>();
            JSONArray cities = pro.getJSONArray("cities");
            for(Object city : cities) {
                JSONObject cit = (JSONObject)city;
                String cityName = cit.getString("cityName");
                list.add(cityName);
            }
            ret.put(provinceName, list);
        }
        return ret;
    }

    public static HashMap<String, ArrayList<String>> getProvinceCities() {
        return provinceCities;
    }

    public static String getRandomAddress() {
        return getRandomAddress(provinceCities);
    }

    public static String getRandomAddress(HashMap<String, ArrayList<String>> addrs) {
        String ret = "";
        int size = addrs.size();
        int random = new Random().nextInt(size);
        int i = 0;
        for(String province : addrs.keySet()) {
            if(random == i) {
                ret = province;
                break;
            }
            i++;
        }
        size = addrs.get(ret).size();
        random = new Random().nextInt(size);
        i = 0;
        for(String city : addrs.get(ret)) {
            if(random == i) {
                ret =  ret + "," + city;
                break;
            }
            i++;
        }
        return ret;
    }

    public static void main(String[] args) {

    }
}
