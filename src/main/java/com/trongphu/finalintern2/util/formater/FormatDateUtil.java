package com.trongphu.finalintern2.util.formater;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Trong Phu on 17/09/2024 23:43
 *
 * @author Trong Phu
 */
public class FormatDateUtil {
    public static Date setEndDate(Date endDate){
        if(endDate != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endDate);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            endDate = calendar.getTime();
            return endDate;
        }
        return null;
    }
}
