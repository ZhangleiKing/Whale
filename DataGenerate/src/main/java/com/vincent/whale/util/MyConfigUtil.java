package com.vincent.whale.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Vincent on 2018/1/23.
 */
@Component
public class MyConfigUtil {

    public static String HBASE_TABLE_NAME;

    public static String HBASE_MASTER_IP;

    public static String ZOOKEEPER_QUORUM_IP;

    public static String ZOOKEEPER_CLIENT_PORT;

    public static String CURRENT_OPERATE_TABLE;


    @Value("${hbase.table.name}")
    private void setHbaseTableName(String tableName) {
        HBASE_TABLE_NAME = tableName;
    }

    @Value("${hbase.master.ip}")
    private void setHbaseMasterIp(String ip) {
        HBASE_MASTER_IP = ip;
    }

    @Value("${zookeeper.quorum}")
    private void setZooKeeperQuorumIp(String ip) {
        ZOOKEEPER_QUORUM_IP = ip;
    }

    @Value("${zookeeper.client.port}")
    private void setZooKeeperClientPort(String port) {
        ZOOKEEPER_CLIENT_PORT = port;
    }

    @Value("${operation.current.table}")
    private void setCurrentOperateTable(String table) {
        CURRENT_OPERATE_TABLE = table;
    }
}
