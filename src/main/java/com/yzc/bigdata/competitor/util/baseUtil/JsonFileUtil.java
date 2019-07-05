package com.yzc.bigdata.competitor.util.baseUtil;
//
//import java.io.*;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @Classname JsonFileUtil
// * @Author lizonghuan
// * @Description 保存和读取json文件
// * @Date-Time 2019/6/5-10:30
// * @Version 1.0
// */
//public class JsonFileUtil {
//    /**
//     * @Title: getProfileByClassLoader
//     * @Description: 采用ClassLoader(类加载器)方式进行读取配置信息
//     * @return Map<String,Object> 以Map键值对方式返回配置文件内容
//     * @param fileName 配置文件名称
//     * 优点：可以在非Web应用中读取配置资源信息，可以读取任意的资源文件信息
//     * 缺点：只能加载类classes下面的资源文件
//     */
//    public static Map<String, Object> getProfileByClassLoader(String fileName) {
//        // 通过ClassLoader获取到文件输入流对象
//        InputStream in = JsonFileUtil.class.getClassLoader().getResourceAsStream(fileName);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//        JsonFileUtil props = new JsonFileUtil();
//        Map<String, Object> profileMap = new HashMap<String, Object>();
//        try {
//            props.load(reader);
//            for (Object key : props.keySet()) {
//                profileMap.put(key.toString(), props.getProperty(key.toString()));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return profileMap;
//    }
//
//    /**
//     * 传递键值对的Map，更新properties文件
//     * @param fileName 文件名(放在resource源包目录下)，需要后缀
//     * @param keyValueMap 键值对Map
//     */
//    public static void updateProperties(String fileName, Map<String, String> keyValueMap) throws Exception {
//        //获取文件路径
//        String filePath = Profile.class.getClassLoader().getResource(fileName).toURI().getPath();
//        System.out.println("propertiesPath:" + filePath);
//        Properties props = new Properties();
//        BufferedReader br = null;
//        BufferedWriter bw = null;
//        try {
//            // 从输入流中读取属性列表（键和元素对）
//            br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
//            props.load(br);
//            br.close();
//            // 写入属性文件
//            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)));
//            // 清空旧的文件
//            // props.clear();
//            for (String key : keyValueMap.keySet()) {
//                props.setProperty(key, keyValueMap.get(key));
//            }
//            props.store(bw, "改变数据");
//            bw.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.err.println("Visit " + filePath + " for updating " + "" + " value error");
//        } finally {
//            try {
//                br.close();
//                bw.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//}
