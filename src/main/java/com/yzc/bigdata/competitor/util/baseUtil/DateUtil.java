package com.yzc.bigdata.competitor.util.baseUtil;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	/**
	 * 计算两个日期的天数差值
	 * @param fromDate
	 * @param toDate
	 * @return
	 * @author douqr 2018-04-17
	 */
	public static int getDifferDays(Date fromDate,Date toDate){
		
		long from = fromDate.getTime();  
		long to = toDate.getTime();  
		int days = (int) ((to - from)/(1000 * 60 * 60 * 24)); 
		return days;
	}
	
	/**
	 * 获取日期的前一天
	 * @param date
	 * @return
	 */
	public static Date getPreDay(Date date) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        
        int day = calendar.get(Calendar.DATE);  
        calendar.set(Calendar.DATE, day-1);  
        
       // calendar.add(Calendar.DAY_OF_MONTH, -1);  //不可用
        return  calendar.getTime(); 
    }  
}
