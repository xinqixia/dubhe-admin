package xin.qixia.dubhe.utils;

import java.util.Random;

public class StringUtils {
    public static String getCard(String pre, int time, int type) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(pre);
        stringBuilder.append("T");
        stringBuilder.append(time);
        stringBuilder.append("P");
        stringBuilder.append(type);
        stringBuilder.append("D");
        char ch;
        Random random = new Random();
        int count = stringBuilder.toString().length();
        for (int i = 0; i < 45 - count; i++) {
            ch = (char) (Math.abs(random.nextInt()) % 26 + 65);
            stringBuilder.append(ch);
        }
        return stringBuilder.toString();
    }

    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(52);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}