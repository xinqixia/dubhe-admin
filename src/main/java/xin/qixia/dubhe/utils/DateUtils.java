package xin.qixia.dubhe.utils;

import java.awt.dnd.DropTarget;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat formatt = new SimpleDateFormat("yyyy-MM-dd");

    //根据日期获取当前周周一日期
    public static Calendar getWeek(Date date) throws ParseException {
        Date dat=formatt.parse(formatt.format(date));   //去掉时分秒
        Calendar cal = Calendar.getInstance();
        cal.setTime(dat);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal;
    }

    //根据日前获取当前月第一天
    public static String getMonth(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return formatt.format(c.getTime());
    }

    //获取当前午夜日期(不包括时间)
    public static String getNowAfterDate(Date date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        return formatt.format(cal.getTime());
    }

    //获取当前凌晨日期(不包括时间)
    public static String getNowBeforeDate(Date date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return formatt.format(cal.getTime());
    }

    // 日期转字符串
    public static String toString(Date date) {
        if (date == null) date = new Date();
        return format.format(date);
    }

    //字符串转日期
    public static Date toDate(String date) throws ParseException {
        return format.parse(date);
    }

    //日期减小时
    public static String dateHour(Date date,int time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, -time);
        return format.format(calendar.getTime());
    }

    // 获取延后或提前的日期
    public static String getDate(String str, int time) throws ParseException {
        Date date = new Date();
        if (str != null) date = format.parse(str);

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(GregorianCalendar.HOUR, time); //把日期往后增加一天,整数  往后推,负数往前移动
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果

        return format.format(date);
    }

    // 获取两个日期相差多少个小时
    public static int getTime(String m, String r) throws ParseException {
        Date d1 = new Date();
        Date d2 = new Date();
        if (m != null) d1 = format.parse(m);
        if (r != null) d2 = format.parse(r);
        return (int) (Math.abs(d2.getTime() - d1.getTime()) / (60 * 60 * 1000));
    }

}
