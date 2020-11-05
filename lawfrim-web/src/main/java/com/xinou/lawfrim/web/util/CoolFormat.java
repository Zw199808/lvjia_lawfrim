package com.xinou.lawfrim.web.util;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
/**
 * @author Wangxin
 * @since 2020-11-05
 * 数字格式化   1002  --> 1k+  1000 --> 1K
 */
public class CoolFormat {

    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();
    static {
        suffixes.put(1_000L, "k");
        suffixes.put(1_001L, "k+");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_001L, "M+");
        suffixes.put(1_000_000_000L, "G");
        suffixes.put(1_000_000_001L, "G+");
        suffixes.put(1_000_000_000_000L, "T");
        suffixes.put(1_000_000_000_001L, "T+");
        suffixes.put(1_000_000_000_000_000L, "P");
        suffixes.put(1_000_000_000_000_001L, "P+");
        suffixes.put(1_000_000_000_000_000_000L, "E");
        suffixes.put(1_000_000_000_000_000_001L, "E+");
    }

    public static String format(long value) {
        //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
        if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1);
        if (value < 0) return "-" + format(-value);
        if (value < 1000) return Long.toString(value); //deal with easy case

        //
        String s = value+"";
        long a = Long.parseLong("1"+s.substring(1));

        Map.Entry<Long, String> e = suffixes.floorEntry(a);
        Long divideBy = e.getKey();
        String suffix = e.getValue();

        long truncated = value / (divideBy / 10); //the number part of the output times 10
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }

    public static void main(String args[]) {

        long[] numbers = new long[]{3,123,999,1000,1002,5000,5821, 10500, 101800, 2000000, 7800000, 92150000, 123200000, 999999999};

        for(long n : numbers) {

            System.out.println(n + " => " + format(n));

        }

    }

}
