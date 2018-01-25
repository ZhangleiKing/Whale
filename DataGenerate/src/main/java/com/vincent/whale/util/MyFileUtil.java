package com.vincent.whale.util;

import java.io.*;

/**
 * Created by Vincent on 2018/1/23.
 */
public class MyFileUtil {

    public static String readFile(String filePath) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return readFileByStream(inputStream);
    }

    public static String readFileByStream(InputStream inputStream) {
        StringBuilder ret = new StringBuilder();

        BufferedReader reader = null;
        InputStreamReader inputStreamReader = null;
        String tmp = "";

        try {
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            while((tmp = reader.readLine()) != null) {
                ret.append(tmp);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret.toString();
    }

    public static void writeFile(String filePath, String content, boolean overwrite) {
        File file = new File(filePath);

        try {
            if(!file.exists())
                file.createNewFile();

            if(!overwrite) {
                //追加写入
                FileWriter writer = new FileWriter(filePath, true);
                writer.write(content);
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
