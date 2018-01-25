package com.vincent.whale.build;

import java.util.Random;

/**
 * Created by Vincent on 2018/1/25.
 */
public class DateRandomFactory {

    public static long getRandomTimestamp(int certainYearsAgo) {
        long now = System.currentTimeMillis();
        long start = now - certainYearsAgo * 365 * 24 * 3600 * 1000;
        long diff = now - start;
        return start + nextLongInRange(diff);
    }

    private static long nextLongInRange(long n) {
        long bits, val;
        do{
            bits = (new Random().nextLong() << 1) >>> 1;
            val = bits % n;
        } while(bits - val + (n-1) < 0L);
        return val;
    }
}
