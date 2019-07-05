package com.yzc.bigdata.competitor.util;

import com.yzc.bigdata.competitor.util.baseUtil.HttpUtil;
import com.yzc.bigdata.competitor.util.baseUtil.HttpUtil;
import com.yzc.bigdata.competitor.util.baseUtil.JsonUtil;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @Classname Similarity
 * @Author lizonghuan
 * @Description 计算文本串相似度
 * @Date-Time 2019/4/16-18:43
 * @Version 1.0
 */
public class Similarity {
    static Map<String, String> effectSegMap;
    static Set<String> remainPosSet = new HashSet<>();
    static Set<String> blackWordSet = new HashSet<>();
    static String lacSegUrl = "http://192.168.18.246/lac_rep_seg_dict";


    static{
        effectSegMap = getEffectSegMap("");

        //lac分词的保留词性
        Collections.addAll(remainPosSet, "n", "nz", "nw", "vn", "an", "v", "j", "vd", "un", "a");
        //lac分词的黑名单词性
        Collections.addAll(blackWordSet, "普通", "优质", "一般", "其它", "其他", "及");


    }

    public static Map<String, String> getEffectSegMap(String path){
        Map<String, String> effectSegMap = new HashMap<>();
        InputStream is = Similarity.class.getClassLoader().getResourceAsStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));// 读取原始json文件
        String temp = null;
        try{
            while((temp = br.readLine()) != null) {
                JSONObject jsonObject = JSONObject.fromObject(temp);
                Map<String, String> dataMap = JsonUtil.json2pojo(jsonObject.toString(), Map.class);
                for(Map.Entry<String, String> infoEntry : dataMap.entrySet()) {
                    effectSegMap.put(infoEntry.getKey(), infoEntry.getValue());
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return effectSegMap;
    }

    //获取goods和catagory的相似度
    public static double getGoodsCatagorySimilarity(String goods, String catagory){
        if (goods == null || catagory == null){
            return 0;
        }
        List<String> sameSubStrs = getAllSubstring(goods, catagory);
        int sameStrLen = 0;
        for(String subStr : sameSubStrs){
            sameStrLen += subStr.length();
        }
        return (double) sameStrLen / catagory.length();
    }


    //获取两个文本的相似度
    public static double getTextSimilarity(String text1, String text2){
        if (text1 == null || text2 == null){
            return 0;
        }
        List<String> sameSubStrs = getAllSubstring(text1, text2);
        int sameStrLen = 0;
        for(String subStr : sameSubStrs){
            sameStrLen += subStr.length();
        }
        return (double) 2 * sameStrLen / (text1.length() + text2.length());
    }

    //获取两个文本的所有相似子串
    public static List<String> getAllSubstring(String str1, String str2){
        if (str1 == null || str2 == null){
            return null;
        }
        String longStr, shortStr;
        if (str1.length() < str2.length()){
            longStr = str2;
            shortStr = str1;
        } else{
            longStr = str1;
            shortStr = str2;
        }
        List<String> result = new ArrayList<>();
        List<String> vacancy = new ArrayList<>();
        vacancy.add("");
        List<String> list1 = splitString(shortStr);
        List<String> list2 = splitString(longStr);
//        result.addAll(list1);
        for(String temp : list1){
            if (list2.contains(temp)){
                result.add(temp);
            }
        }
        if (result.size() < 2){
            return result;
        }
        List<String> commonSubStrs = new ArrayList<>();
        StringBuffer strBuf = new StringBuffer();
        for(int i = 0; i < result.size(); i++){
            strBuf.append(result.get(i));
            if (i == result.size() - 1){
                break;
            }
            if (shortStr.indexOf(strBuf.toString() + result.get(i + 1)) != -1
                && longStr.indexOf(strBuf.toString() + result.get(i + 1)) != -1
                && i < result.size() - 2){
                continue;
            } else{
                commonSubStrs.add(strBuf.toString());
                strBuf.setLength(0);
            }
        }
        String tail = commonSubStrs.get(commonSubStrs.size() - 1) + result.get(result.size() - 1);
        if (shortStr.indexOf(tail) != -1 && longStr.indexOf(tail) != -1) {
            commonSubStrs.set(commonSubStrs.size() - 1, tail);
        } else{
            commonSubStrs.add(result.get(result.size() - 1));
        }
        return commonSubStrs;
    }

    public static List<String> splitString(String str){
        List<String> resultList = new ArrayList<>();
        if (str == null){
            return resultList;
        }
        for(int i = 0; i < str.length(); i++){
            resultList.add(str.substring(i, i + 1));
        }
        return resultList;
    }

    //得到保留词性连接的词
    public static String getEffectWordByLac(String catagoryString){
        if (effectSegMap.containsKey(catagoryString)){
            return effectSegMap.get(catagoryString);
        }else{
            List<Map<String, String>> resultMapList = null;
            try{
                resultMapList =  HttpUtil.postUrlByModel(lacSegUrl, catagoryString, List.class);
            }catch(Exception e){
                e.printStackTrace();
            }
            if (resultMapList != null){
                StringBuffer sbEffectWord = new StringBuffer();
                String pos = null;
                for(Map<String, String> resultMap : resultMapList){
                    if (remainPosSet.contains(resultMap.get("pos")) && !blackWordSet.contains(resultMap.get("word"))){
                        sbEffectWord.append(resultMap.get("word"));
                        sbEffectWord.append(" ");
                    }
                }
                return sbEffectWord.toString();
            }
            return null;
        }
    }

    public static void main(String[] args){
        String str = "qwerty";
        System.out.println(str.substring(5, 5));
        List<String> strList = new ArrayList<>();
        strList.add("12");
        strList.add("sasd");
        System.out.println(strList.contains("sasd"));
        String str1 = "上海市上海港务局但是";
        String str2 = "上海大众但是";
        System.out.println(getAllSubstring(str1, str2));
        System.out.println(getTextSimilarity(str1, str2));
    }
}
