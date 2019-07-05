package com.yzc.bigdata.competitor.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @Classname JdbcUtil
 * @Author lizonghuan
 * @Description jdbc
 * @Date-Time 2019/5/23-11:16
 * @Version 1.0
 */
public class JdbcUtil {

    //connection
    public static Connection getConectionYouZC_DW()
    {
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn= DriverManager.getConnection("jdbc:sqlserver://192.168.8.107:1433;DatabaseName=YOUZC_DW",
                    "youzhicaisa","qwertyuiop!@#$%^*()0987654321");
            return conn;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.print("----------------连接失败");
        }
        return null;
    }

    //connection
    public static Connection getConectionSpiderTask()
    {
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn= DriverManager.getConnection("jdbc:sqlserver://192.168.8.38:1433;DatabaseName=SpiderTask",
                    "sa","f6BTNG8sicZD");
            return conn;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.print("----------------连接失败");
        }
        return null;
    }

    //关闭请求
    public static int closeConnection(Connection connection){
        try{
            connection.close();
            return 1;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

}
