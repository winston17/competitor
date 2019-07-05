package com.yzc.bigdata.competitor.util.baseUtil;

import java.util.UUID;

public class UuidUtil {

	/**
     * 获取UUID主键
     * 
     * @return String
     */
    public static String randomUUID()
    {
        return UUID.randomUUID().toString();
    }
    
}
