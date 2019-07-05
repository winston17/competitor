package com.yzc.bigdata.competitor.util;

import com.yzc.bigdata.competitor.util.baseUtil.JsonUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname TextUtil
 * @Author lizonghuan
 * @Description 文本工具
 * @Date-Time 2019/4/18-17:16
 * @Version 1.0
 */

public class TextUtil {

    private static Pattern numPattern = Pattern.compile("\\d+(?:\\.\\d+)?");

    private static Pattern removedOrgPattern = Pattern.compile("^.{2,7}(?:公司|集团|局|办事处)");

//    private static String jsonFilePath = TextUtil.class.getClassLoader().getResource("json").toURI().getPath();

    public static String removeOrgFromTitle(String title){
        if (title == null){
            return null;
        }
        String removedTitle = title.replace("^.{2,7}(?:公司|集团|局|办事处)", "");
        Matcher matcher = removedOrgPattern.matcher(title);
        if (matcher.find()){
            return title.replaceFirst(matcher.group(0), "");
        }else{
            return title;
        }
    }

    public static Double transDays(String days){
        if (days == null){
            return null;
        }
        Matcher matcher = numPattern.matcher(days);
        if (matcher.find()){
            if (days.contains("年")){
                return Double.parseDouble(matcher.group(0)) * 365;
            }
            if (days.contains("月")){
                return Double.parseDouble(matcher.group(0)) * 31;
            }
            if(days.contains("天")){
                return Double.parseDouble(matcher.group(0));
            }
            if(days.contains("日")){
                return Double.parseDouble(matcher.group(0));
            }
            return Double.parseDouble(matcher.group(0));
        }else{
            return null;
        }
    }

    //判断同步时间，当数据没问题的时候，即程序是一直运行。
    // 程序开始运行的时候产生一个当前时间a（定时任务从该时间开始计时，下一次更新是在该时间的28天之后的b），第一次将数据放入搜索引擎和elasticsearch的时候是把当前时间之前所有的数据塞入
    //程序每28天更新一次，更新时间b减去28天就是上次的截止时间a，sql或者搜索引擎都是查出a之后的数据相应进行更新
    public static String delayDateDays(String dateString, int n){
//        String dateString = "2019-04-25 18:43:57.0";
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date date = sdf.parse(dateString);

            Calendar calendar  =   Calendar.getInstance();
            calendar.setTime(date); //需要将date数据转移到Calender对象中操作
            calendar.add(calendar.DATE, -n);//把日期往后增加n天.正数往后推,负数往前移动, n可以设置为7的倍数
            date = calendar.getTime(); //获取增加后的值
            dateString = sdf.format(date);
            return dateString;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static int writeDateToFile(String date){
        try{
            String jsonFilePath = TextUtil.class.getClassLoader().getResource("json").toURI().getPath();
            InputStream is = TextUtil.class.getClassLoader().getResourceAsStream("json/dateList.json");
            List<String> dateList = null;
            if (is != null){
                BufferedReader br = new BufferedReader(new InputStreamReader(is));// 读取原始json文件
                String temp = null;
                if((temp = br.readLine()) != null) {
                    JSONObject jsonObject = JSONObject.fromObject(temp);
                    dateList = JsonUtil.json2pojo(jsonObject.toString(), List.class);
                }else{
                    dateList = new ArrayList<>();
                }
            }else{
                dateList = new ArrayList<>();
            }
            dateList.add(date);
            JSONArray jsonArray = JSONArray.fromObject(dateList);
            String jsonString = jsonArray.toString();
            boolean success = CreateFileUtil.createJsonFile(jsonString, jsonFilePath, "dataList.json");
            System.out.println("成功与否: " + success);
            return 1;
        }catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getLastUpdateFromFile(){
        try{
            InputStream is = TextUtil.class.getClassLoader().getResourceAsStream("json/dateList.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));// 读取原始json文件
            String temp = null;
            while((temp = br.readLine()) != null) {
                JSONObject jsonObject = JSONObject.fromObject(temp);
                List<String> dateList = JsonUtil.json2pojo(jsonObject.toString(), List.class);
                String lastUpdateTime = dateList.get(dateList.size() - 1);
                return lastUpdateTime;
            }
            return null;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    public static void main(String[] args) throws Exception{
//        System.out.println(writeDateToFile("2019-06-05"));
//        System.out.println(delayDateDays("2019-06-05", 3));
        System.out.println(delayDateDays("2019-04-25 18:43:57", 3));

//        String str1 = "中国建设集团陆军军官学院";
//        String str2 = "安徽省发改委";
//        System.out.println(123);
//        String filePath = TextUtil.class.getClassLoader().getResource("json").toURI().getPath();
//        System.out.println("propertiesPath:" + filePath);
//        List<String> strList = new ArrayList<>();
//        strList.add(str1);
//        strList.add(str2);
//        writeDateToFile(str1);
//        System.out.println(removeOrgFromTitle(str1));
    }
}
