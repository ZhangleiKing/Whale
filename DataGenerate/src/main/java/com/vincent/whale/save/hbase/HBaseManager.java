package com.vincent.whale.save.hbase;

import com.vincent.whale.util.MyConfigUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Vincent on 2018/1/23.
 */
public class HBaseManager {

    private Configuration config = null;
    private Table hTable = null;
    private Admin admin = null;
    private Connection connection = null;
    private HTableDescriptor desc = null;

    public HBaseManager() {
        config = HBaseConfiguration.create();
        config.set("hbase.master", MyConfigUtil.HBASE_MASTER_IP + "60000");
        config.set("hbase.zookeeper.property.clientPort", MyConfigUtil.ZOOKEEPER_CLIENT_PORT);
        config.set("hbase.zookeeper.quorum", MyConfigUtil.ZOOKEEPER_QUORUM_IP);
        try {
            connection = ConnectionFactory.createConnection(config);
            admin = connection.getAdmin();
            if(MyConfigUtil.CURRENT_OPERATE_TABLE != null)
                hTable = connection.getTable(TableName.valueOf(MyConfigUtil.CURRENT_OPERATE_TABLE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param tableName
     */
    public void createTable(String tableName, String[] family) {
        System.out.println("*****start create hbase table*****");
        TableName tn = TableName.valueOf(tableName);
        try {
            if(admin.tableExists(tn)) {
                System.out.println("table exist");
                return;
            }
            desc = new HTableDescriptor(tn);
            for (int i = 0; i < family.length; i++) {
                desc.addFamily(new HColumnDescriptor(family[i]));
            }
            //invoke Admin's method
            admin.createTable(desc);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("*****end create hbase table*****");
    }

    /**
     * if table exists and existThenDelete is true, then delete the existed table and create table
     * @param tableName
     * @param existThenDelete
     * @param family
     */
    public void createTable(String tableName, boolean existThenDelete, String[] family) {
        TableName tn = TableName.valueOf(tableName);
        try{
            if(existThenDelete) {
                if(admin.tableExists(tn)) { //if the table exist, delete and then create
                    admin.disableTable(tn);
                    admin.deleteTable(tn);
                    System.out.println("deleted existed table first");
                }
            }else {
                if(admin.tableExists(tn)) {
                    System.out.println("table exist");
                    return;
                }
            }
            createTable(tableName, family);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteTable(String tableName) {
        System.out.println("*****start to delete table*****");
        TableName tb = TableName.valueOf(tableName);
        try {
            if(admin.tableExists(tb)) {
                admin.disableTable(tb);
                admin.deleteTable(tb);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("*****delete table end*****");
    }

    public ArrayList<String> listTables() {
        ArrayList<String> ret = new ArrayList<>();
        try {
            HTableDescriptor[] hTableDescriptors = admin.listTables();
            for(HTableDescriptor des : hTableDescriptors) {
                ret.add(des.getNameAsString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public void insertData(String tableName, String rowKey, HashMap<String, HashMap<String, String>> data) {
        TableName tb = TableName.valueOf(tableName);
        try {
            if(hTable == null || !tableName.equals(MyConfigUtil.CURRENT_OPERATE_TABLE))
                hTable = connection.getTable(tb);
            Put put = new Put(Bytes.toBytes(rowKey));
            Iterator<Map.Entry<String, HashMap<String, String>>> familyIter = data.entrySet().iterator();
            while(familyIter.hasNext()) {
                Map.Entry<String, HashMap<String, String>> entry = familyIter.next();
                String family = entry.getKey();
                HashMap<String, String> qualifierData = entry.getValue();
                Iterator<Map.Entry<String, String>> qualifierIter = qualifierData.entrySet().iterator();
                while(qualifierIter.hasNext()) {
                    Map.Entry<String, String> kv = qualifierIter.next();
                    String qualifier = kv.getKey();
                    String value = kv.getValue();
                    put.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes.toBytes(value));
                }
            }
            hTable.put(put);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteData(String tableName, String rowKey, String columnFamily, String qualifier) {
        TableName tb = TableName.valueOf(tableName);
        try {
            if(!admin.tableExists(tb)) {
                System.out.println("table not exist!");
            }else {
                if(hTable == null || !tableName.equals(MyConfigUtil.CURRENT_OPERATE_TABLE))
                    hTable = connection.getTable(tb);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void getData(String tableName, String rowKey, String columnFamily, String qualifier) {
        TableName tb = TableName.valueOf(tableName);

        try {
            if(hTable == null || !tableName.equals(MyConfigUtil.CURRENT_OPERATE_TABLE))
                hTable = connection.getTable(tb);

            Get get = new Get(Bytes.toBytes(rowKey));
            if(columnFamily != null) {
                get.addFamily(Bytes.toBytes(columnFamily));
            }
            if(columnFamily != null && qualifier != null) {
                get.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(qualifier));
            }
            Result result = hTable.get(get);
            for (Cell cell : result.listCells()) {
                System.out.println("family:" + Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength()));
                System.out.println("qualifier:" + Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength()));
                System.out.println("value:" + Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
                System.out.println("Timestamp:" + cell.getTimestamp());
                System.out.println("-----------------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {


    }

}
