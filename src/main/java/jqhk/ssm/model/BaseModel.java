package jqhk.ssm.model;

import java.lang.reflect.Field;

public class BaseModel {

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Field[] fields = this.getClass().getDeclaredFields();
        sb.append("(");
        for (Field f: fields) {
            f.setAccessible(true);
            try {
                Object v = f.get(this);
                String s = String.format("%s: %s, ", f.getName(), v);
                sb.append(s);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        sb.append(")");
        return sb.toString();
    }

    public static String formattedTime(Long time) {
        Long mine = 60L;
        Long hour = mine * 60;
        Long day = hour * 24;
        Long month = day * 30;
        Long year = month * 12;
        Long now = System.currentTimeMillis() / 1000L;
        Long gap = now - time;
        String r;

        if (gap < hour) {
            Long count = gap / mine;
            r = String.format("%s分钟前", count);
        } else if (gap < day) {
            Long count = gap / hour;
            r = String.format("%s小时前", count);
        } else if (gap < month) {
            Long count = gap / day;
            r = String.format("%s天前", count);
        } else if (gap < year) {
            Long count = gap / month;
            r = String.format("%s月前", count);
        } else {
            Long count = gap / year;
            r = String.format("%s年前", count);
        }

        return r;
    }
}

