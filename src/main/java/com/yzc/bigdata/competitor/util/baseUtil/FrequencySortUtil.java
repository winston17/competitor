package com.yzc.bigdata.competitor.util.baseUtil;

import com.yzc.bigdata.competitor.entity.node.Person;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @Classname FrequencySortUtil
 * @Author lizonghuan
 * @Description 根据列表中元素出现的次数对列表进行排序
 * @Date-Time 2019/6/13-14:16
 * @Version 1.0
 */
public class FrequencySortUtil {

    public static <T> List<T> sortList(List<T> inputList){
        if (inputList == null){
            return null;
        }
        Map<T, Integer> frequencyMap = new HashMap<>();
        for(T element : inputList){
            if (!frequencyMap.containsKey(element)){
                frequencyMap.put(element, 1);
            }else{
                frequencyMap.put(element, frequencyMap.get(element) + 1);
            }
        }
        List<Map.Entry<T, Integer>> frequencyList = new ArrayList<>(frequencyMap.entrySet());
        Collections.sort(frequencyList, new Comparator<Map.Entry<T, Integer>>(){
            @Override
            public int compare(Map.Entry<T, Integer> e1, Map.Entry<T, Integer> e2){
////                按照value值，从小到大排序
//                return e1.getValue() - e2.getValue();

//                按照value值，从大到小排序
                return e2.getValue() - e1.getValue();

//                //按照value值，用compareTo()方法默认是从小到大排序
//                return e1.getValue().compareTo(e2.getValue());
            }
        });
        List<T> sortedList = new ArrayList<>();
        for(Map.Entry<T, Integer> entry : frequencyList){
            sortedList.add(entry.getKey());
        }
        return sortedList;
    }

    public static <T> List<T> sortList(List<T> inputList, String countFieldName){
        Class<?> tClass;
        countFieldName = countFieldName.replaceFirst(countFieldName.substring(0, 1),
                countFieldName.substring(0, 1).toUpperCase());
        String setCountFieldName = "set" + countFieldName;
        if (inputList == null){
            return null;
        }
        Map<T, Integer> frequencyMap = new HashMap<>();
        for(T element : inputList){
            if (!frequencyMap.containsKey(element)){
                frequencyMap.put(element, 1);
            }else{
                frequencyMap.put(element, frequencyMap.get(element) + 1);
            }
        }
        List<Map.Entry<T, Integer>> frequencyList = new ArrayList<>(frequencyMap.entrySet());
        Collections.sort(frequencyList, new Comparator<Map.Entry<T, Integer>>(){
            @Override
            public int compare(Map.Entry<T, Integer> e1, Map.Entry<T, Integer> e2){
////                按照value值，从小到大排序
//                return e1.getValue() - e2.getValue();

//                按照value值，从大到小排序
                return e2.getValue() - e1.getValue();

//                //按照value值，用compareTo()方法默认是从小到大排序
//                return e1.getValue().compareTo(e2.getValue());
            }
        });
        List<T> sortedList = new ArrayList<>();
        for(Map.Entry<T, Integer> entry : frequencyList){
            T t = entry.getKey();
            try{
                tClass = t.getClass();
                Method method = tClass.getMethod(setCountFieldName, int.class);
                method.invoke(t, entry.getValue());
            }catch(Exception e){
                e.printStackTrace();
            }
            sortedList.add(entry.getKey());
        }
        return sortedList;
    }


    public static void main(String[] args) {
        List<Person> testList = new ArrayList<>();
        Person person1 = new Person("高俊", 20, "aa");
        Person person2 = new Person("马武", 22, "aa");
        Person person3 = new Person("李磊", 24, "aa");
        Person person4 = new Person("李磊", 24, "bb");



        testList.add(person1);
        testList.add(person1);
        testList.add(person2);
        testList.add(person3);
        testList.add(person3);
        testList.add(person4);

        System.out.println(testList);
        testList = sortList(testList, "count");
        System.out.println(testList);

    }
}
