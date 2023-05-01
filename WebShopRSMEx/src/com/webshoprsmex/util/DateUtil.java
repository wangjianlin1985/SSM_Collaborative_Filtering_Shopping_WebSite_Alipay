package com.webshoprsmex.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期时间相关
 */
public abstract class DateUtil {

	public static final String pattern_ymd = "yyyy-MM-dd"; // pattern_ymd
	
	public static final String pattern_ymd_hms = "yyyy-MM-dd HH:mm:ss"; // pattern_ymdtime
	
	public static final String pattern_ymd_hms_sss = "yyyy_MM_dd_HH_mm_ss_sss"; // pattern_ymdtime

	public static String getCurrentDate() {
        Date date = new Date();
        date.setTime(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat(pattern_ymd_hms);
        return sdf.format(date);
    }
	
	public static String getCurrentDate(String pattern) {
        Date date = new Date();
        date.setTime(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

}
