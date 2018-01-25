package com.vincent.whale;

import com.vincent.whale.build.UserTravellingFactory;
import com.vincent.whale.entity.Travelling;
import com.vincent.whale.entity.User;
import com.vincent.whale.entity.UserTravelling;
import com.vincent.whale.save.hbase.HBaseManager;
import com.vincent.whale.save.operation.BatchWrite;
import com.vincent.whale.util.MyConfigUtil;
import com.vincent.whale.util.MyFileUtil;
import com.vincent.whale.build.AddressFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Vincent on 2018/1/23.
 */
public class Main {
    @SuppressWarnings("resource")
    public static void main(String[] args) throws IOException {
        new ClassPathXmlApplicationContext("spring-config.xml");
        BatchWrite.batchWrite(1500000);
    }
}
